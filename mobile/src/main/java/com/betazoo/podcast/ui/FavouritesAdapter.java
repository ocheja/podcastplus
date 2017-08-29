package com.betazoo.podcast.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betazoo.podcast.R;
import com.betazoo.podcast.utils.FavouritesUpdater;
import com.betazoo.podcast.utils.XmlParser;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by designer on 11.09.15.
 */
public class FavouritesAdapter extends ArrayAdapter {
    private final List<XmlParser.Item> stations;
    private final Context context;

    public FavouritesAdapter(Context context, List<XmlParser.Item> stations) {
        super(context, R.layout.favourites_item, stations);
        this.stations = stations;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.favourites_item, parent, false);
        TextView categoryName = (TextView) item.findViewById(R.id.stationNameText);
        categoryName.setText(stations.get(position).title);
        ImageView categoryImage = (ImageView) item.findViewById(R.id.stationImage);
        ImageView removeFavBtn = (ImageView) item.findViewById(R.id.removeFavBtn);
        removeFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouritesUpdater favUpd = new FavouritesUpdater(context);
                favUpd.removeFav(position);
                remove(stations.get(position));
                notifyDataSetChanged();
            }
        });





        Glide.with(getContext())
                .load(stations.get(position).image)
                .placeholder(R.drawable.defimage)
                .dontAnimate()
                .centerCrop()
                .into(categoryImage);
        return item;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }
}
