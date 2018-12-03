package com.example.lenovo.newclassmate.CardStackView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.StartActivity;

public class TouristSpotCardAdapter extends ArrayAdapter<TouristSpot> {

    public TouristSpotCardAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.cardadapter_adapter, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        TouristSpot spot = getItem(position);

        holder.typeName.setText(spot.typeName);
        holder.userName.setText(spot.userName);
        Glide.with(getContext()).load(spot.url).into(holder.image);

        return contentView;
    }

    private static class ViewHolder {
        public TextView typeName;
        public TextView userName;
        public ImageView image;

        public ViewHolder(View view) {
            this.typeName = (TextView) view.findViewById(R.id.item_tourist_spot_card_typeName);
            this.userName = (TextView) view.findViewById(R.id.item_tourist_spot_card_userName);
            this.image = (ImageView) view.findViewById(R.id.item_tourist_spot_card_image);
        }
    }

}