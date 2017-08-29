package com.betazoo.podcast.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.betazoo.podcast.R;
import com.betazoo.podcast.utils.XmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class FavoriteActivity extends ActionBarCastActivity{


    private String stationListFileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Bundle extras = getIntent().getExtras();
        if(extras != null){
        }
        stationListFileName = "favourites";
        new fetcFavhStationList().execute(stationListFileName);
    }

    private class fetcFavhStationList extends AsyncTask<String, Void, List<XmlParser.Item> > {

        protected List<XmlParser.Item> doInBackground(String... urls) {

            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (Exception e){
                e.printStackTrace();


                return null;

            }
        }

        protected void onPostExecute(final List<XmlParser.Item> stList){

            try {


                final ListView stationList = (ListView) findViewById(R.id.stationlistView);
                FavouritesAdapter adapter = new FavouritesAdapter(getApplicationContext(), stList);
                stationList.setAdapter(adapter);

                stationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(FavoriteActivity.this, PodcastPlayerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



                        intent.putExtra("radiourl", stList.get(position).url);
                        intent.putExtra("image_file", stList.get(position).image);
                        intent.putExtra("id", stList.get(position).rid);
                        intent.putExtra("radioName", stList.get(position).title);
                        intent.putExtra("activity","favorite");


                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        finish();
                    }
                });
            } catch (NullPointerException e){
                Toast.makeText(getApplicationContext(), "No File", Toast.LENGTH_LONG).show();
            }
        }
    };

    private List<XmlParser.Item> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {

        InputStream stream = null;
        FileInputStream fis;
        XmlParser stationListXmlParser = new XmlParser();
        List<XmlParser.Item> Items = null;

        try {
            //**********TEST AREA**********
            fis = getApplicationContext().openFileInput("favourites");

            stream = fis;
            Items = stationListXmlParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
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



}
