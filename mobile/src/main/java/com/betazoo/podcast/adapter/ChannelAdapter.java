package com.betazoo.podcast.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betazoo.podcast.R;
import com.betazoo.podcast.model.Channel.Channel;
import com.betazoo.podcast.model.Data.Category;
import com.betazoo.podcast.ui.BrowserFragmentListener;
import com.betazoo.podcast.ui.EpisodeBrowserFragment;
import com.betazoo.podcast.utils.AppGlideSettings;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by tosin on 12/14/2016.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {


    private Context context;
    private List<Channel> channels;

    private BrowserFragmentListener mBrowserFragmentListener;


    public ChannelAdapter(Activity mContext, List<Channel> channels) {
        this.channels = channels;
        this.context = mContext;
        this.mBrowserFragmentListener = (BrowserFragmentListener) mContext;


    }



    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_list, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //dataCursor.moveToPosition(position);

        holder.channel = channels.get(position);
        if (holder.channel != null) {
            // holder.featuredImageView.setMaxHeight(dp);
            //holder.featuredImageView.setMaxWidth(dp);
            if (holder.channel.getThumbnailUrl() != null) {


                Glide.with(context)
                        .load(holder.channel.getThumbnailUrl())
                        .diskCacheStrategy(AppGlideSettings.AP_DISK_CACHE_STRATEGY)
                        .dontAnimate()
                        .into(holder.featuredImageView);


            }


                holder.titleTextView.setText(holder.channel.getTitle());


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //EpisodeFragment fragment = EpisodeFragment.newInstance(holder.channel);
                    //HomeActivity activity = (HomeActivity) context;
                    //activity.loadChildFragment(fragment);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EpisodeBrowserFragment.ARG_POD, holder.channel);

                    mBrowserFragmentListener.onMediaItemSelected(String.valueOf(holder.channel.getId()), 2, bundle);
                }
            });


        }


    }

    @Override
    public int getItemCount() {

        return (channels == null) ? 0 : channels.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void refresh(List<Channel> list) {
        channels.clear();
        channels.addAll(list);
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Channel> list) {
        channels.addAll(list);
        notifyDataSetChanged();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {


        private Channel channel;


        private final View cardView;
        private ImageView btnView;
        private ImageView featuredImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;

        public ViewHolder(View v) {
            super(v);
            cardView = v;
            btnView = (ImageView) v.findViewById(R.id.play_eq) ;
            featuredImageView = (ImageView) v.findViewById(R.id.imgFeatured);
            titleTextView = (TextView) v.findViewById(R.id.txtvTitle);
            descriptionTextView = (TextView) v.findViewById(R.id.description);


        }



    }


}