/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.betazoo.podcast.ui.tv;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.betazoo.podcast.R;
import com.betazoo.podcast.adapter.CategoryAdapter;
import com.betazoo.podcast.adapter.ChannelAdapter;
import com.betazoo.podcast.api.PodcastAPIService;
import com.betazoo.podcast.model.Channel.Channel;
import com.betazoo.podcast.model.Channel.ChannelResult;
import com.betazoo.podcast.model.Data.Category;
import com.betazoo.podcast.model.Data.CategoryResult;
import com.betazoo.podcast.service.MusicService;
import com.betazoo.podcast.ui.MediaItemViewHolder;
import com.betazoo.podcast.utils.Injector;
import com.betazoo.podcast.utils.LogHelper;
import com.betazoo.podcast.utils.NetworkHelper;
import com.betazoo.podcast.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Fragment that lists all the various browsable queues available
 * from a {@link android.service.media.MediaBrowserService}.
 * <p/>
 * It uses a {@link MediaBrowserCompat} to connect to the {@link MusicService}.
 * Once connected, the fragment subscribes to get all the children.
 * All {@link MediaBrowserCompat.MediaItem}'s that can be browsed are shown in a ListView.
 */
public class ChannelBrowserFragment extends Fragment {

    private static final String TAG = LogHelper.makeLogTag(ChannelBrowserFragment.class);

    private static final String ARG_CAT_ID = "category_id";

    private int mCategoryId;
    private BrowserFragmentListener mBrowserFragmentListener;
    private View mErrorView;
    private TextView mErrorMessage;
    List <Channel> itemList;
    RecyclerView recyclerView;
    private final BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        private boolean oldOnline = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            // We don't care about network changes while this fragment is not associated
            // with a media ID (for example, while it is being initialized)

                boolean isOnline = NetworkHelper.isOnline(context);
                if (isOnline != oldOnline) {
                    oldOnline = isOnline;
                    checkForUserVisibleErrors(false);

            }
        }
    };




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // If used on an activity that doesn't implement BrowserFragmentListener, it
        // will throw an exception as expected:
        mBrowserFragmentListener = (BrowserFragmentListener) activity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogHelper.d(TAG, "fragment.onCreateView");
        View rootView = inflater.inflate(R.layout.browser_list, container, false);

        mErrorView = rootView.findViewById(R.id.playback_error);
        mErrorMessage = (TextView) mErrorView.findViewById(R.id.error_message);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        itemList = new ArrayList();



        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadItem();


        return rootView;
    }

    private  void loadItem()
    {
        PrefManager mPrefManager = new PrefManager(this.getActivity().getApplicationContext());
        String token = mPrefManager.getAuthenticationToken();

        PodcastAPIService mPodcastAPIService = Injector.providePodcastAPIService(this.getActivity().getApplicationContext());
        final Call<ChannelResult> categoryResultCall = mPodcastAPIService.getChannel(token, getCategoryId());
        categoryResultCall.enqueue(new Callback<ChannelResult>() {
            @Override
            public void onResponse(Call<ChannelResult> call, Response<ChannelResult> response) {
                try
                {
                    if(!response.isSuccessful()) {
                        Log.d(TAG, "Response is not successful");
                        throw new NullPointerException("Response failed");
                    }
                    itemList.addAll(response.body().getData().getData());
                    ChannelAdapter recylerAdapter = new ChannelAdapter(ChannelBrowserFragment.this.getActivity(), itemList);
                    recyclerView.setAdapter(recylerAdapter);
                    recylerAdapter.notifyDataSetChanged();


                }
                catch(NullPointerException e)
                {
                    System.err.println(TAG  + e.getMessage());
                    Toast.makeText(ChannelBrowserFragment.this.getActivity(), "Login Failed! Please Try Again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChannelResult> call, Throwable t) {
                try {
                    Log.d(TAG, t.getMessage());
                    Toast.makeText(ChannelBrowserFragment.this.getActivity(), "Login Failed! Please Try Again", Toast.LENGTH_LONG).show();
                }
                catch(NullPointerException e)
                {
                    Log.d(TAG, "Something went wrong trying to find out about failure");
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        LogHelper.d(TAG, "fragment.onStart");


        // Registers BroadcastReceiver to track network connection changes.
        this.getActivity().registerReceiver(mConnectivityChangeReceiver,
            new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();

        this.getActivity().unregisterReceiver(mConnectivityChangeReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBrowserFragmentListener = null;
    }

    public int getCategoryId() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getInt(ARG_CAT_ID);
        }
        return -1;
    }

    public void setCategoryId(int categoryId) {
        Bundle args = new Bundle(1);
        args.putInt(ChannelBrowserFragment.ARG_CAT_ID, categoryId);
        setArguments(args);
    }



    private void checkForUserVisibleErrors(boolean forceError) {
        boolean showError = forceError;
        // If offline, message is about the lack of connectivity:
        if (!NetworkHelper.isOnline(getActivity())) {
            mErrorMessage.setText(R.string.error_no_connection);
            showError = true;
        } else {
            if (forceError) {
                // Finally, if the caller requested to show error, show a generic message:
                mErrorMessage.setText(R.string.error_loading_media);
                showError = true;
            }
        }
        mErrorView.setVisibility(showError ? View.VISIBLE : View.GONE);
        LogHelper.d(TAG, "checkForUserVisibleErrors. forceError=", forceError,
            " showError=", showError,
            " isOnline=", NetworkHelper.isOnline(getActivity()));
    }

    private void updateTitle() {

           // mBrowserFragmentListener.setToolbarTitle(null);
            //return;



    }

    public interface BrowserFragmentListener {
        void onMediaItemSelected(String mediaID);
        void setToolbarTitle(CharSequence title);
    }

}
