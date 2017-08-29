package com.betazoo.podcast.utils;

/**
 * Created by tosin on 12/15/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.betazoo.podcast.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public Utility() {

    }

    public static String QueryComposer(String color, String type, String fabric, String occasion, String query) {
        return String.format("color=%s&cloth_type=%s&fabric=%s&occasion=%s&query=%s", color, type, fabric, occasion, query);
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidToken(String token) {
        return token.length() == Constants.TOKEN_LENGTH;
    }

    public static boolean isValidEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static int RandomIndex(int max) {
        Random random = new Random();
        return random.nextInt(max);

    }

    public static boolean vaLidatePhoneString(String NumberToMatch) {
        String pattern = "^[0-9]{6,11}+$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(NumberToMatch);
        return matcher.find();
    }


    public static boolean hasNetwork(Context context)
    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }



    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return (permission == PackageManager.PERMISSION_GRANTED) ;

    }


    public static void requestExternalStorage(Activity activity)
    {
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
        );
    }

    public static final String country_code = "234";

    public static final String TAG = "Utility";

    public static String formatInternationalNumber(String number)
    {
        Log.d(TAG, "original number: "+number);
        if(number == null || TextUtils.isEmpty(number)) {
            Log.d(TAG, "original number is NULL");
            return "";
        }
        number = number.trim();
        Log.d(TAG, "Number after trim: "+number);
        number = number.replaceAll("/\\s+/", "");
        Log.d(TAG, "Number after regex: "+number);


        if(number.charAt(0) == '+'){
            Log.d(TAG, "Number started with +: "+number);
            number = number.substring(1);
            if(number.charAt(0) != '0' && number.length() <= 10)
                number = country_code + number;
        }

        if(number.startsWith(country_code)) {
            Log.d(TAG, "Number started with country code +: "+number);
            if(number.startsWith(country_code+"0")) {
                Log.d(TAG, "Number started with country code and a zero(0) +: "+number);
                return country_code + number.substring(4);
            }else{
                return number;
            }
        }


        if(number.charAt(0)== '0') {
            Log.d(TAG, "Number started with zero(0) : "+number);
            number = country_code + number.substring(1);
            Log.d(TAG, "Country Code added to number : "+number);
        }
        else {
            Log.d(TAG, "Country Code added to number : "+number);
            number = country_code + number;
        }

        return number;

    }
}


