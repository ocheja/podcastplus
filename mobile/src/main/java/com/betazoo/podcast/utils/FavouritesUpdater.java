package com.betazoo.podcast.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.betazoo.podcast.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by designer on 11.09.15.
 */
public class FavouritesUpdater {

    private XmlParser.Item favItem;
    private Context context;
    private String stationListFileName;
    private int position;

    public FavouritesUpdater(Context context) {
        this.context = context;
        stationListFileName = context.getResources().getString(R.string.favourites_file_name);
    }

    public void addFav(XmlParser.Item favItem){
        this.favItem = favItem;
        try{
            new addFavToStationList().execute(stationListFileName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addFavClean(XmlParser.Item favItem){
        this.favItem = favItem;
        try{
            new addFavToStationListClean().execute(stationListFileName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeFav(int position){
        this.position = position;
        try{
            new removeFavFromStationList().execute(stationListFileName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private class addFavToStationList extends AsyncTask<String, Void, List<XmlParser.Item> > {

        protected List<XmlParser.Item> doInBackground(String... urls) {

            try {
                return loadXmlFromFile(urls[0]);
            } catch (Exception e){
                e.printStackTrace();
                Log.d("xml parse error", "loadxml didn't load");
                return null;
            }
        }

        protected void onPostExecute(List<XmlParser.Item> stList){
            Boolean favExists = false;

            if(stList == null){
                stList = new ArrayList<XmlParser.Item>();
            }

            for (XmlParser.Item item : stList) {
                if (item.title.equals(favItem.title) && item.url.equals(favItem.url) && item.image.equals(favItem.image) && item.rid.equals(favItem.rid)) {
                    favExists = true;
                }
            }

            if(!favExists){
                stList.add(favItem);
                XmlWriter xmlWriter = new XmlWriter(stList, context);
                xmlWriter.writeXmlList();
                Toast.makeText(context,"Station added in favorite", Toast.LENGTH_LONG).show();

        } else {
                Toast.makeText(context,"Already in fav's", Toast.LENGTH_LONG).show();
            }
        }
    };

    private class removeFavFromStationList extends AsyncTask<String, Void, List<XmlParser.Item> > {

        protected List<XmlParser.Item> doInBackground(String... urls) {

            try {
                return loadXmlFromFile(urls[0]);
            } catch (Exception e){
                e.printStackTrace();
                Log.d("xml parse error", "loadxml didn't load");
                return null;
            }
        }

        protected void onPostExecute(final List<XmlParser.Item> stList){

            stList.remove(position);
            XmlWriter xmlWriter = new XmlWriter(stList, context);
            xmlWriter.writeXmlList();
            Toast.makeText(context,"Fav removed", Toast.LENGTH_LONG).show();
        }
    };

    private List<XmlParser.Item> loadXmlFromFile(String urlString) throws XmlPullParserException, IOException {

        InputStream stream = null;
        FileInputStream fis;
        XmlParser stationListXmlParser = new XmlParser();
        List<XmlParser.Item> Items = null;

        try {
            fis = context.openFileInput(urlString);
            stream = fis;
            Items = stationListXmlParser.parse(stream);

        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return Items;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(30000);
        conn.setConnectTimeout(45000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

    private class addFavToStationListClean extends AsyncTask<String, Void, List<XmlParser.Item> > {

        protected List<XmlParser.Item> doInBackground(String... urls) {

            try {
                return loadXmlFromFile(urls[0]);
            } catch (Exception e){
                e.printStackTrace();
                Log.d("xml parse error", "loadxml didn't load");
                return null;
            }
        }

        protected void onPostExecute(List<XmlParser.Item> stList){
            Boolean favExists = false;

            if(stList == null){
                stList = new ArrayList<XmlParser.Item>();
            }

            for (XmlParser.Item item : stList) {
                if (item.title.equals(favItem.title) && item.url.equals(favItem.url) && item.image.equals(favItem.image) && item.rid.equals(favItem.rid)) {
                    favExists = true;
                }
            }

            if(!favExists){
                stList.add(favItem);
                XmlWriter xmlWriter = new XmlWriter(stList, context);
                xmlWriter.writeXmlListClean();
               // Toast.makeText(context,"Fav added 2",Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(context,"Already in fav's",Toast.LENGTH_LONG).show();
            }
        }
    };
}
