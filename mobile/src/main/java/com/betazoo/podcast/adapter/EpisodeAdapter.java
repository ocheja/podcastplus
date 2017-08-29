package com.betazoo.podcast.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betazoo.podcast.R;
import com.betazoo.podcast.model.episode.Episode;
import com.betazoo.podcast.ui.AudioListener;
import com.betazoo.podcast.ui.CategoryActivity;
import com.betazoo.podcast.ui.EpisodeBrowserFragment;
import com.betazoo.podcast.ui.MediaBrowserFragment;
import com.betazoo.podcast.ui.PodcastPlayerActivity;

import java.util.List;


/**
 * Created by tosin on 12/14/2016.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {


    private Context context;
    private List<Episode> episodes;
    private AudioListener listener;
    private EpisodeBrowserFragment.MediaFragmentListener mMediaFragmentListener;




    public EpisodeAdapter(Activity mContext, List<Episode> episodes) {
        this.episodes = episodes;
        this.context = mContext;
        this.listener = (AudioListener) mContext;
        this.mMediaFragmentListener = (EpisodeBrowserFragment.MediaFragmentListener) mContext;


    }



    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_list_item, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        holder.episode = episodes.get(position);
        if (holder.episode != null) {


            holder.titleTextView.setText(holder.episode.getTitle());
            holder.descriptionView.setText(holder.episode.getDescription());
            holder.durationTextView.setText(holder.episode.getPublicationDate());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //listener.openPlayer(holder.episode);
                    MediaBrowserCompat.MediaItem mediaItem = createBrowsableMediaItemFromEpisode(holder.episode);


                    listener.openPlayer(holder.episode);
                    mMediaFragmentListener.onMediaItemSelected(mediaItem);
                }
            });

            holder.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaBrowserCompat.MediaItem mediaItem = createBrowsableMediaItemFromEpisode(holder.episode);


                  //listener.openPlayer(holder.episode);
                    mMediaFragmentListener.onMediaItemSelected(mediaItem);


                }
            });



        }


    }

    private MediaBrowserCompat.MediaItem createBrowsableMediaItemFromEpisode(Episode episode) {
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
        return new MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);
    }

    @Override
    public int getItemCount() {

        return (episodes == null) ? 0 : episodes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void refresh(List<Episode> list) {
        episodes.clear();
        episodes.addAll(list);
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Episode> list) {
        episodes.addAll(list);
        notifyDataSetChanged();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View cardView;
        private TextView titleTextView;
        private TextView durationTextView;
        private TextView descriptionView;
        private Episode episode;
        private ImageView playBtn;




        public ViewHolder(View v) {
            super(v);
            cardView = v;

            playBtn = (ImageView) v.findViewById(R.id.play_eq);
            titleTextView = (TextView) v.findViewById(R.id.title);
            durationTextView = (TextView) v.findViewById(R.id.date);
            descriptionView = (TextView) v.findViewById(R.id.description);


        }
    }


}