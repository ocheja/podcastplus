package com.betazoo.podcast.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.betazoo.podcast.Constants;
import com.betazoo.podcast.R;
import com.betazoo.podcast.model.Auth.AuthResult;
import com.betazoo.podcast.api.PodcastAPIService;
import com.betazoo.podcast.utils.Injector;
import com.betazoo.podcast.utils.PrefManager;
import com.betazoo.podcast.utils.Utility;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private  EditText editText;
    private CoordinatorLayout coordinatorLayout;
    private AppCompatButton tokenButton;
    private AVLoadingIndicatorView loaderIndicator;



    private boolean isLoggin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        AppCompatImageView logoImage = (AppCompatImageView) findViewById(R.id.logo);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoImage);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        tokenButton = (AppCompatButton) findViewById(R.id.token_button);
        loaderIndicator = (AVLoadingIndicatorView) findViewById(R.id.loader_indicator);




        editText = (EditText) findViewById(R.id.phone_number_txt);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    String number = editable.toString();
                    if(Utility.isValidMobile(number))
                        tokenButton.setEnabled(true);

            }
        });


        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    View view = LoginActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    onClick(v);
                    return true;
                }
                return false;
            }
        });


    }

    private void resetLogginStatus()
    {
        isLoggin = false;
        tokenButton.setEnabled(true);
        loaderIndicator.hide();

    }

    public void onClick(View view)
    {
        loaderIndicator.show();
        tokenButton.setEnabled(false);

        if(isLoggin)
            return;
        isLoggin = true;

        View v = LoginActivity.this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(!Utility.hasNetwork(this)) {
            resetLogginStatus();
            makeSnackBar(coordinatorLayout, "No Internet Connection!");
            return;
        }
        String mobile = editText.getText().toString();
        if (isInvalidMobilePhoneNumber(mobile)) return;
        mobile = Utility.formatInternationalNumber(mobile);

        doLogin(mobile);


    }

    private boolean isInvalidMobilePhoneNumber(String mobile) {
        if(TextUtils.isEmpty(mobile)) {
            makeSnackBar(coordinatorLayout, "Empty Number! Enter your number");
            resetLogginStatus();
            return true;

        }
        if(!Utility.isValidMobile(mobile)) {
            makeSnackBar(coordinatorLayout, "Invalid Number! Enter a Valid Number");
            resetLogginStatus();
            return true;
        }
        return false;
    }

    private void makeSnackBar(CoordinatorLayout coordinatorLayout, String text) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);
        snackbar.show();


    }

    public void doLogin(String mobile)
    {
        PrefManager mPrefManager = new PrefManager(getApplicationContext());
        mPrefManager.setUserPhoneNumber(mobile);

        PodcastAPIService mPodcastAPIService = Injector.providePodcastAPIService(this);

        final Call<AuthResult> authRequest = mPodcastAPIService.login(mobile);
        authRequest.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                try
                {
                    if(!response.isSuccessful() || !response.body().getStatus().equals(Constants.SUCCESS_KEYWORD)) {
                        Log.d(TAG, "Login FAILED, Try Again");
                        throw new NullPointerException("Login failed");
                    }
                    resetLogginStatus();
                    startActivity(new Intent(LoginActivity.this, TokenActivity.class));
                    finish();

                }
                catch(NullPointerException e)
                {
                    resetLogginStatus();
                    String message = "Null Exception, Login Failed: ";
                    if(e.getMessage() != null)
                        message += e.getMessage();
                    Log.d(TAG, message);
                    makeSnackBar(coordinatorLayout, "Error Login FAILED!");
                }
                catch(Exception e)
                {
                    resetLogginStatus();
                    String message = "Null Exception, Login Failed: ";
                    if(e.getMessage() != null)
                        message += e.getMessage();
                    Log.d(TAG, message);
                    makeSnackBar(coordinatorLayout, "Error Login FAILED!");
                }

            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {


                try {
                    resetLogginStatus();
                    String message = "Login Request Failed: ";
                    if(t.getMessage() != null)
                        message += t.getMessage();
                    Log.d(TAG, message);
                    makeSnackBar(coordinatorLayout, "Error Login FAILED!");
                }
                catch (NullPointerException e)
                {
                    resetLogginStatus();
                    String message = "Null Exception, Login Failed: ";
                    if(e.getMessage() != null)
                        message += e.getMessage();
                    Log.d(TAG, message);
                    makeSnackBar(coordinatorLayout, "Error Login FAILED!");
                }
            }
        });
    }




}
