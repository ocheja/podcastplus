package com.betazoo.podcast.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.betazoo.podcast.App;
import com.betazoo.podcast.R;
import com.betazoo.podcast.model.episode.Episode;
import com.betazoo.podcast.utils.LogHelper;

public class CategoryActivity extends BaseActivity
implements  BrowserFragmentListener, AudioListener, PodcastBrowserFragment.MediaFragmentListener,
        EpisodeBrowserFragment.MediaFragmentListener

        {

            private static final String FRAGMENT_TAG = "uamp_list_container";
            private static final String TAG = LogHelper.makeLogTag(CategoryActivity.class);
            private static final String SAVED_MEDIA_ID="com.betazoo.podcast.MEDIA_ID";
            public static final String EXTRA_START_FULLSCREEN =
                    "com.betazoo.podcast.EXTRA_START_FULLSCREEN";

            /**
             * Optionally used with {@link #EXTRA_START_FULLSCREEN} to carry a MediaDescription to
             * the {@link FullScreenPlayerActivity}, speeding up the screen rendering
             * while the {@link android.support.v4.media.session.MediaControllerCompat} is connecting.
             */
            public static final String EXTRA_CURRENT_MEDIA_DESCRIPTION =
                    "com.betazoo.podcast.CURRENT_MEDIA_DESCRIPTION";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initializeToolbar();
        initializeFromParams(savedInstanceState, getIntent());



    }

            @Override
            public void onMediaItemSelected(String mediaID, int browsingIndex, Bundle bundle) {
                navigateToBrowser(mediaID, browsingIndex, bundle);
            }

            @Override
            public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
                LogHelper.d(TAG, "onMediaItemSelected, mediaId=" + item.getMediaId());
                if (item.isPlayable()) {
                    getSupportMediaController().getTransportControls()
                            .playFromMediaId(item.getMediaId(), null);
                } else if (item.isBrowsable()) {
                   // navigateToBrowser(item.getMediaId());
                } else {
                    LogHelper.w(TAG, "Ignoring MediaItem that is neither browsable nor playable: ",
                            "mediaId=", item.getMediaId());
                }
            }



            @Override
            public void setToolbarTitle(CharSequence title) {

            }

            protected void initializeFromParams(Bundle savedInstanceState, Intent intent) {
                String mediaId = null;

                if (savedInstanceState != null) {
                        // If there is a saved media ID, use it
                    mediaId = savedInstanceState.getString(SAVED_MEDIA_ID);

                }
                navigateToBrowser(mediaId, 0, null);
            }


            private void navigateToBrowser(String mediaId, int browsingIndex, Bundle bundle) {
                LogHelper.d(TAG, "navigateToBrowser, mediaId=");
                Fragment fragment = getCategoryFragment();

                switch (browsingIndex) {
                    case 0:
                        if (fragment == null) {
                            fragment = new CategoryBrowserFragment();
                            CategoryBrowserFragment categoryBrowserFragment = (CategoryBrowserFragment) fragment ;
                            categoryBrowserFragment.setMediaId(mediaId);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(
                                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
                            transaction.replace(R.id.container, categoryBrowserFragment, FRAGMENT_TAG);
                            // If this is not the top level media (root), we add it to the fragment back stack,
                            // so that actionbar toggle and Back will work appropriately:
                            if (mediaId != null) {
                                transaction.addToBackStack(null);
                            }

                            transaction.commit();
                        }
                        break;
                    case 1:
                        Log.e(TAG,"Clicking on cat");
                            fragment = new ChannelBrowserFragment();
                            ChannelBrowserFragment channelBrowserFragment = (ChannelBrowserFragment) fragment;
                            channelBrowserFragment.setMediaId(mediaId);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(
                                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
                            transaction.replace(R.id.container, channelBrowserFragment, FRAGMENT_TAG);
                            // If this is not the top level media (root), we add it to the fragment back stack,
                            // so that actionbar toggle and Back will work appropriately:
                            transaction.addToBackStack(null);
                            transaction.commit();
                        break;
                    case 2:

                            fragment = new EpisodeBrowserFragment();
                           EpisodeBrowserFragment episodeBrowserFragment = (EpisodeBrowserFragment) fragment ;
                            episodeBrowserFragment.setMediaId(mediaId);
                            //Bundle dataBundle = bundle;
                            //episodeBrowserFragment.setBundle(dataBundle);
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            transaction1.setCustomAnimations(
                                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
                            transaction1.replace(R.id.container, episodeBrowserFragment, FRAGMENT_TAG);
                            // If this is not the top level media (root), we add it to the fragment back stack,
                            // so that actionbar toggle and Back will work appropriately:
                            transaction1.addToBackStack(null);
                            transaction1.commit();

                        break;
                }


            }



            private Fragment getCategoryFragment() {
                return getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            }

            @Override
            public void openPlayer(Episode episode) {

                MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder()
                        .setMediaId("FeedId:" + Long.toString(episode.getId()))
                        .setTitle(episode.getTitle())
                        .setDescription(episode.getDescription())
                        .setSubtitle(episode.getTitle());
       /* if(episode.getChannel().getThumbnailUrl() != null) {
          builder.setIconUri(Uri.parse(episode.getChannel().getThumbnailUrl()));
        }*/
                if(episode.getLink() != null) {
                    builder.setMediaUri(Uri.parse(episode.getLink()));
                }
                MediaDescriptionCompat description = builder.build();


                Intent fullScreenIntent = new Intent(this, FullScreenPlayerActivity.class)
                        .putExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION, description);


               /* Intent intent = new Intent(CategoryActivity.this, PodcastPlayerActivity.class);
                intent.putExtra(PodcastPlayerActivity.ARG_EPISODE, episode);
                intent.putExtra("id", String.valueOf(episode.getChannelId()));
                intent.putExtra("radioName", episode.getTitle());
                intent.putExtra("radiourl", episode.getLink());
                //intent.putExtra("image",  channelAlbumArt);
                //intent.putExtra("image_file",channelAlbumArt);
                intent.putExtra("activity", "list");

                final App mApp = ((App) this.getApplicationContext());

                if (mApp.getRADIO_IS_PLAY() == null) {


                } else {

                    if (mApp.getRADIO_IS_PLAY()) {

                        //RadioActivity activity = new RadioActivity();
                        //activity.stopPlaying();

                    }
                }

*/
                startActivity(fullScreenIntent);
                // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


            }


        }
