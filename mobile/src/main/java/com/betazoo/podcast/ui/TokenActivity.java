package com.betazoo.podcast.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

public class TokenActivity extends AppCompatActivity {

    public static final String TAG = "TokenActivity";
    private CoordinatorLayout coordinatorLayout;
    private EditText editText;
    private boolean isVerifying;

    private AppCompatButton loginButton;
    private AVLoadingIndicatorView loaderIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        AppCompatImageView logoImage = (AppCompatImageView) findViewById(R.id.logo);
        Glide.with(this)
                .load(R.drawable.logo)
                .into(logoImage);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        TextView promptTextView = (TextView) findViewById(R.id.prompt_token_txt);
        PrefManager mPrefManager = new PrefManager(getApplicationContext());
        String mobile = mPrefManager.getUserPhoneNumber();
        String promptMessage = getResources().getString(R.string.prompt_token, mobile);
        promptTextView.setText(promptMessage);

        loginButton = (AppCompatButton) findViewById(R.id.verify_button);
        loaderIndicator = (AVLoadingIndicatorView) findViewById(R.id.loader_indicator);

        editText = (EditText) findViewById(R.id.token_txt);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String token = editable.toString();
                if(Utility.isValidToken(token))
                    loginButton.setEnabled(true);

            }
        });

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    View view = TokenActivity.this.getCurrentFocus();
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
        isVerifying = false;
        loginButton.setEnabled(true);
        loaderIndicator.hide();
        PrefManager mPrefManager = new PrefManager(getApplicationContext());
        mPrefManager.setIsLogin(false);
        mPrefManager.setAuthenticationToken("");

    }

    public void onClick(View view)
    {
        loaderIndicator.show();
        loginButton.setEnabled(false);
        if(isVerifying)
            return;
        isVerifying = true;

        View v = TokenActivity.this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(!Utility.hasNetwork(this)) {
            resetLogginStatus();
            makeSnackBar(coordinatorLayout, "No Internet Connection!");
            return;
        }

        String token = editText.getText().toString();

        if (isInvalidToken(token)) return;

        doVerifyToken(token);



    }


    private boolean isInvalidToken(String token) {
        if(TextUtils.isEmpty(token)) {
            makeSnackBar(coordinatorLayout, "Empty Token! Enter your token");
            resetLogginStatus();
            return true;

        }
        if(!Utility.isValidToken(token)) {
            makeSnackBar(coordinatorLayout, "Invalid Token! Enter a Valid Token");
            resetLogginStatus();
            return true;
        }
        return false;
    }

    private void doVerifyToken(String token) {
        final PrefManager mPrefManager = new PrefManager(getApplicationContext());
        String mobile = mPrefManager.getUserPhoneNumber();

        PodcastAPIService mPodcastAPIService = Injector.providePodcastAPIService(this);
        final Call<AuthResult> authRequest = mPodcastAPIService.verifytoken(mobile, token);
        authRequest.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                try
                {
                    if(!response.isSuccessful()) {
                        Log.d(TAG, "Error Token Verification Failed");
                        throw new NullPointerException("Error Token Verification Failed");
                    }
                    //Ok

                    isVerifying = false;
                    mPrefManager.setIsLogin(true);
                    mPrefManager.setAuthenticationToken(response.body().getData().getToken());
                    startActivity(new Intent(TokenActivity.this, MusicPlayerActivity.class));
                    finish();
                }
                catch(NullPointerException e)
                {
                    resetLogginStatus();
                    makeSnackBar(coordinatorLayout, "Error Token Verification Failed");
                }
                catch(Exception e)
                {
                    resetLogginStatus();
                    makeSnackBar(coordinatorLayout, "Error Token Verification Failed");
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                try {
                    Log.d(TAG, t.getMessage());
                    resetLogginStatus();
                    makeSnackBar(coordinatorLayout, "Error Token Verification Failed");

                }
                catch(NullPointerException e)
                {    Log.d(TAG, "Something went wrong trying to find out about failure");
                    resetLogginStatus();
                    makeSnackBar(coordinatorLayout, "Error Token Verification Failed");

                }
            }
        });
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
}
