package com.betazoo.podcast.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betazoo.podcast.model.Data.Category;
import com.bumptech.glide.Glide;
import com.betazoo.podcast.R;

import java.util.ArrayList;

/**
 * Created by thetaeo on 28/08/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Category> data = new ArrayList<Category>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Category> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        Category item = data.get(position);
        holder.imageTitle.setText(item.getName());

        String imgUrl = item.getThumbnailPath();

        Glide.with(getContext())
                .load(imgUrl)
                .placeholder(R.drawable.defimage)
                .dontAnimate()
                .centerCrop()
                .into(holder.image);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
