package com.betazoo.podcast.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.betazoo.podcast.R;
import com.betazoo.podcast.adapter.GridViewAdapter;
import com.betazoo.podcast.api.PodcastAPIService;
import com.betazoo.podcast.model.Data.Category;
import com.betazoo.podcast.model.Data.CategoryResult;
import com.betazoo.podcast.utils.Injector;
import com.betazoo.podcast.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CategoryChooser extends BaseActivity {

    private GridView gridView;
    private List<Category> categories ;

    private GridViewAdapter gridAdapter;

    public static final String TAG = "CategoryChooser";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_chooser);

        initializeToolbar();

        /*gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, );
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                *//*ImageItem item = (ImageItem) parent.getItemAtPosition(position);


                Intent intent = new Intent(MainActivity.this, RadioListActivity.class);
                intent.putExtra("id", item.getId());


                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);*//*
            }
        });*/

    }

    public void createList(){
/*
        // pDialog = new ProgressDialog(this);
        pDialog  = ProgressDialog.show(this, "Loading...", null, false, false);
        // pDialog.setMessage("Loading...");
        // pDialog.setCancelable(false);


        PrefManager mPrefManager = new PrefManager(getApplicationContext());
        String token = mPrefManager.getAuthenticationToken();


        PodcastAPIService mPodcastAPIService = Injector.providePodcastAPIService(getApplicationContext());
        final Call<CategoryResult> categoryResultCall = mPodcastAPIService.getLatestCategories(token);
        categoryResultCall.enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, retrofit2.Response<CategoryResult> response) {
                try
                {
                    if(!response.isSuccessful()) {
                        Log.d(TAG, "Response is not successful");
                        throw new NullPointerException("Response failed");
                    }
                    categories = response.body().getData().getData();


                    pDialog.hide();
                    makeDraw();


                }
                catch(NullPointerException e)
                {
                    System.err.println(TAG  + e.getMessage());
                    Toast.makeText(MainActivity.this, "Something went wrong while fetching category", Toast.LENGTH_LONG).show();
                    pDialog.hide();
                }
                pDialog.hide();
            }

            @Override
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                try {
                    Log.d(TAG, t.getMessage());
                    Toast.makeText(MainActivity.this, "Category wasnt fetched", Toast.LENGTH_LONG).show();
                }
                catch(NullPointerException e)
                {
                    Log.d(TAG, "Something went wrong trying to find out about failure");
                    pDialog.hide();
                }
                pDialog.hide();
            }
        });*/

    }

    public void makeDraw(){


        gridView = (GridView) findViewById(R.id.gridView);
       // gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, imageItems);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
             /*   Category item = (Category) parent.getItemAtPosition(position);


                Intent intent = new Intent(MainActivity.this, RadioListActivity.class);
                intent.putExtra("id", item.getId());


                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);*/
            }
        });

    }


}
