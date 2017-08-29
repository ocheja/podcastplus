package com.betazoo.podcast.utils;

/**
 * Created by tosin on 6/10/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefManager {

    public static final String TAG = "prefMan";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "betazoo-podcast";

    private static final String IS_FIRST_TIME_LAUNCH = "betazoo-podcast-IsFirstTimeLaunch";

    private static  final String PREF_AUTH_TOKEN = "betazoo-podcast-token";

    private static  final String PREF_AUTH_ID = "betazoo-podcast-UserId";

    private static  final String PREF_AUTH_MSISDN = "betazoo-podcast-User-MSISDN";

    private  static final String PREF_AUTH_IS_LOGIN = "betazoo-podcast-islogin";

    private static final String PREF_IS_FIRST_RUN = "betazoo-podcast-IsFirstRun";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public Boolean isFirstRun()
    {
        return pref.getBoolean(PREF_IS_FIRST_RUN, true);
    }

    public void setIsFirstRun(Boolean isFirstRun)
    {
        editor.putBoolean(PREF_IS_FIRST_RUN, isFirstRun);
        editor.commit();
    }

    public void setAuthenticationToken(String token)
    {
        editor.putString(PREF_AUTH_TOKEN, token);
        editor.commit();
    }

    public String getAuthenticationToken()
    {
        return pref.getString(PREF_AUTH_TOKEN, "");
    }

    public void setUserID(int userID)
    {
        editor.putInt(PREF_AUTH_ID, userID);
        editor.commit();
    }

    public boolean isLogin()
    {
        return pref.getBoolean(PREF_AUTH_IS_LOGIN, false);
    }

    public void setIsLogin(boolean status)
    {
        Log.d(TAG, "setting login to "+ String.valueOf(status));
        editor.putBoolean(PREF_AUTH_IS_LOGIN, status);
        editor.commit();
    }

    public int getUserID()
    {
        return pref.getInt(PREF_AUTH_ID, 0);
    }

    public void setUserPhoneNumber(String phoneNumber)
    {

        editor.putString(PREF_AUTH_MSISDN, phoneNumber);
        editor.commit();
    }

    public String getUserPhoneNumber()
    {
        return pref.getString(PREF_AUTH_MSISDN, "");
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void reset(){
        editor.clear().apply();
    }

}