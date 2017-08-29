package com.betazoo.podcast.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.betazoo.podcast.App;
import com.betazoo.podcast.R;
import com.betazoo.podcast.api.PodcastAPIService;
import com.betazoo.podcast.model.PodcastItem;
import com.betazoo.podcast.model.episode.Episode;
import com.betazoo.podcast.model.episode.EpisodeResult;
import com.betazoo.podcast.utils.Injector;
import com.betazoo.podcast.utils.PrefManager;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;

import static com.betazoo.podcast.Constants.SDCARD_FOLDER_W;

public class PodcastPlayerActivity extends AppCompatActivity {



    private Episode episode;
    final ArrayList<PodcastItem> podcastItems = new ArrayList<>();
    private ProgressDialog pDialog;
    public static final String ARG_EPISODE = "com.betazoo.podcast.Episode";

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Context context;
    ProgressBar progressBar;
    ProgressDialog dialogz;
    String files;
    String names;
    String urls;

    int position = 0;

    public final static String TAG = "PodActivity";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final App mApp = ((App)getApplicationContext());

        if (mApp.getRADIO_IS_PLAY() == null){





        } else {

            if (mApp.getRADIO_IS_PLAY()){

                System.out.println("PLAYIS BACKKK &&&&&&&&&&&&&&&&&&&&&&&&  "  + mApp.getRADIO_IS_PLAY() );
                FullScreenPlayerActivity activity = new FullScreenPlayerActivity();
                //activity.stopPlaying();



            }
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_podcast_player);
        episode = getIntent().getExtras().getParcelable(ARG_EPISODE);

        position = getIntent().getExtras().getInt("position");

        generateLisPodcast();


        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + SDCARD_FOLDER_W);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }



        names  = getIntent().getExtras().getString("track_name");
        files = episode.getLink();



        Log.d("PPP", files);




        createPlayer(files,names);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    mediaPlayer.pause();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                   // mediaPlayer.start();

                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }



    }



    private  void  createPlayer(String url, String trackname){




        urls = url.replaceAll(" ", "%20");

        // create a media player
        mediaPlayer = new MediaPlayer();




        // try to load data and play
        try {

            // give data to mediaPlayer
            mediaPlayer.setDataSource(urls);
            // media player asynchronous preparation
            mediaPlayer.prepareAsync();

            //   progressBar = (new ProgressBar(R.id.progressBar));
//
            // create a progress dialog (waiting media player preparation)
            final ProgressDialog dialog = new ProgressDialog(PodcastPlayerActivity.this);

            // set message of the dialog
            dialog.setMessage("Prepare to play. Please wait.");

            // prevent dialog to be canceled by back button press
            dialog.setCancelable(false);

            // show dialog at the bottom
            dialog.getWindow().setGravity(Gravity.CENTER);

            // show dialog
            dialog.show();


            // inflate layout
          //  setContentView(R.layout.activity_player_cast);

            // display title
            ((TextView)findViewById(R.id.now_playing_text)).setText(trackname);



            // execute this code at the end of asynchronous media player preparation
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(final MediaPlayer mp) {


                    //start media player
                    mp.start();

                    // link seekbar to bar view
                    seekBar = (SeekBar) findViewById(R.id.seekBar);

                    //update seekbar
                    mRunnable.run();

                    //dismiss dialog
                    dialog.dismiss();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            continiousPlay();
                        }
                    });
                }
            });







        } catch (IOException e) {
            Activity a = this;
            a.finish();
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
        }



    }


    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if(mediaPlayer != null) {

                //set max value
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);

                //update total time text view
                TextView totalTime = (TextView) findViewById(R.id.totalTime);
                totalTime.setText(getTimeString(mDuration));

                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);

                //update current time text view
                TextView currentTime = (TextView) findViewById(R.id.currentTime);
                currentTime.setText(getTimeString(mCurrentPosition));

                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(mediaPlayer != null && fromUser){
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });


            }

            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void play(View view){

        mediaPlayer.start();
    }


    public void pause(View view){

        mediaPlayer.pause();

    }

    public void stop(View view){

        mediaPlayer.seekTo(0);
        mediaPlayer.pause();

    }


    public void seekForward(View view){

        //set seek time
        int seekForwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if(currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
            // forward song
            mediaPlayer.seekTo(currentPosition + seekForwardTime);
        }else{
            // forward to end position
            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }

    }

    public void seekBackward(View view){

        //set seek time
        int seekBackwardTime = 5000;

        // get current song position
        int currentPosition = mediaPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if(currentPosition - seekBackwardTime >= 0){
            // forward song
            mediaPlayer.seekTo(currentPosition - seekBackwardTime);
        }else{
            // backward to starting position
            mediaPlayer.seekTo(0);
        }

    }


    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }




    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000*60*60);
        long minutes = ( millis % (1000*60*60) ) / (1000*60);
        long seconds = ( ( millis % (1000*60*60) ) % (1000*60) ) / 1000;

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    public void downloadMix(View view){

        dialogz = new ProgressDialog(PodcastPlayerActivity.this);
        dialogz.setMessage("Track: " +episode.getTitle()+" download...");
        dialogz.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
       // dialogz.setIndeterminate(true);
        dialogz.show();

        Ion.with(PodcastPlayerActivity.this)
                .load(urls)
// have a ProgressBar get updated automatically with the percent
                .progressBar(progressBar)
// and a ProgressDialog
                .progressDialog(dialogz)
// can also use a custom callback
                .progress(new ProgressCallback() {

                    @Override
                    public void onProgress(long downloaded, long total) {



                    }

                })
                .write(new File(Environment.getExternalStorageDirectory() +
                        File.separator + "Netpod/" + names + ".mp3"))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {

                        dialogz.dismiss();
                    }
                });



    }

    public void generateLisPodcast(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        PrefManager mPrefManager = new PrefManager(getApplicationContext());
        String token = mPrefManager.getAuthenticationToken();

        PodcastAPIService mPodcastAPIService = Injector.providePodcastAPIService(this);
        final Call<EpisodeResult> episodeResultCall = mPodcastAPIService.getLatestEpisode(token);
        episodeResultCall.enqueue(new Callback<EpisodeResult>() {
            @Override
            public void onResponse(Call<EpisodeResult> call, retrofit2.Response<EpisodeResult> response) {
                try
                {
                    if(!response.isSuccessful()) {
                        Log.d(TAG, "Response is not successful");
                        throw new NullPointerException("Response failed");
                    }

                    List<Episode> episodeList = response.body().getData().getData();
                    int listSize  = episodeList.size();


                    for (int i = 0; i < listSize; i++) {


                        Episode person = episodeList.get(i);


                        String track_name = person.getTitle();
                        String track_file = person.getLink();
                        String file = person.getLink();

                        podcastItems.add(new PodcastItem(track_name,track_file,file));

                    }

                    pDialog.hide();


                }
                catch(NullPointerException e)
                {
                    pDialog.hide();
                    System.err.println(TAG  + e.getMessage());
                    Toast.makeText(PodcastPlayerActivity.this, "Podcast fetching failed", Toast.LENGTH_LONG).show();
                }
                pDialog.hide();
            }

            @Override
            public void onFailure(Call<EpisodeResult> call, Throwable t) {
                try {
                    Log.d(TAG, t.getMessage());
                    Toast.makeText(PodcastPlayerActivity.this, "Login Failed! Please Try Again", Toast.LENGTH_LONG).show();
                }
                catch(NullPointerException e)
                {
                    pDialog.hide();
                    Log.d(TAG, "Something went wrong trying to find out about failure");
                }
                pDialog.hide();
            }
        });



    }

    public void nextTrack(View view){


        if (position == podcastItems.size() - 1){

            position = 0;
        } else {

            position = position + 1;
        }

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }



        names = podcastItems.get(position).track_name;
        files = podcastItems.get(position).track_file;

        createPlayer(files,names);


    }

    public void forward (View view){



        if (position == 0){

            position = podcastItems.size() - 1;
        } else {

            position = position - 1;
        }



        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }



        names = podcastItems.get(position).track_name;
        files = podcastItems.get(position).track_file;

        createPlayer(files,names);

    }

    public void continiousPlay(){

        if (position == podcastItems.size() - 1){

            position = 0;
        } else {

            position = position + 1;
        }

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }



        names = podcastItems.get(position).track_name;
        files = podcastItems.get(position).track_file;

        createPlayer(files,names);
    }




}
