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
import com.betazoo.podcast.model.Data.Category;
import com.betazoo.podcast.ui.BrowserFragmentListener;
import com.betazoo.podcast.ui.CategoryBrowserFragment;
import com.betazoo.podcast.ui.EpisodeBrowserFragment;
import com.betazoo.podcast.ui.MediaBrowserFragment;
import com.betazoo.podcast.utils.AppGlideSettings;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by tosin on 12/14/2016.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private Context context;
    private List<Category> categorys;
    private BrowserFragmentListener mBrowserFragmentListener;

    public CategoryAdapter(Activity mContext, List<Category> categorys) {
        this.categorys = categorys;
        this.context = mContext;
        this.mBrowserFragmentListener = (BrowserFragmentListener) mContext;
     
    }



    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_list, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //dataCursor.moveToPosition(position);

        holder.category = categorys.get(position);
        if (holder.category != null) {
            // holder.featuredImageView.setMaxHeight(dp);
            //holder.featuredImageView.setMaxWidth(dp);
            if (holder.category.getThumbnailPath() != null) {


                Glide.with(context)
                        .load(holder.category.getThumbnailPath())
                        .diskCacheStrategy(AppGlideSettings.AP_DISK_CACHE_STRATEGY)
                        .dontAnimate()
                        .fitCenter()
                        .centerCrop()
                        .into(holder.featuredImageView);


            }


                holder.titleTextView.setText(holder.category.getName());


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ChannelFragment fragment = ChannelFragment.newInstance(holder.category);
                    //HomeActivity activity = (HomeActivity) context;
                    //activity.loadChildFragment(fragment);
                    mBrowserFragmentListener.onMediaItemSelected(String.valueOf(holder.category.getId()), 1, null);

                }
            });


        }


    }

    @Override
    public int getItemCount() {

        return (categorys == null) ? 0 : categorys.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void refresh(List<Category> list) {
        categorys.clear();
        categorys.addAll(list);
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Category> list) {
        categorys.addAll(list);
        notifyDataSetChanged();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View cardView;
        private ImageView btnView;
        private ImageView featuredImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private Category category;

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