package com.betazoo.podcast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.betazoo.podcast.utils.PrefManager;


public class SplashActivity extends AppCompatActivity {

    private PrefManager mPrefManager;
    private final static String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "App launched");

        mPrefManager = new PrefManager(getApplicationContext());
        boolean hasLogin = mPrefManager.isLogin();
        Log.d(TAG, "Login status: "+String.valueOf(hasLogin));
        if (hasLogin) {
            Log.e(TAG, "Open Home");
            startActivity(new Intent(this, MusicPlayerActivity.class));
            finish();
        }
        else {

            Log.e(TAG, "Open Login");
                startActivity(new Intent(this, LoginActivity.class));
                finish();

        }
    }
}
