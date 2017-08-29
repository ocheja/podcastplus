package com.betazoo.podcast;

import android.app.Application;

import com.betazoo.podcast.utils.FavouritesUpdater;
import com.betazoo.podcast.utils.PrefManager;
import com.betazoo.podcast.utils.XmlParser;
import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * Created by tosin on 1/14/2017.
 */

public class App extends Application {


    private Boolean RADIO_IS_PLAY;


    @Override
    public void onCreate() {
        //reinitialize variable
        super.onCreate();

        Stetho.initializeWithDefaults(this);

        PrefManager prefs = new PrefManager(this.getApplicationContext());


        if (prefs.isFirstRun()) {
            prefs.setIsFirstRun(false);

            createFileFav();

        }
    }

    public final  Boolean getRADIO_IS_PLAY() {
        return RADIO_IS_PLAY;
    }

    public  final void setRADIO_IS_PLAY(Boolean str) {
        RADIO_IS_PLAY = str;
    }

    public void createFileFav() {



        XmlParser.Item newFav = new XmlParser.Item("", "", "", "");

        FavouritesUpdater favUpd = new FavouritesUpdater(getApplicationContext());
        favUpd.addFavClean(newFav);

        try {
            FileInputStream fis = getApplicationContext().openFileInput("favourites");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}


