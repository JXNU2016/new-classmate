package com.example.lenovo.newclassmate.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lenovo.newclassmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/11/29.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContetx;
    private LayoutInflater mLayoutInflater;
    private List<Bitmap> images=new ArrayList<>();
    private ImageView image;

    public ImageAdapter(Context content,ArrayList<Bitmap> images) {
        mContetx=content;
        this.images=images;
        mLayoutInflater=(LayoutInflater) mContetx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= mLayoutInflater.inflate(R.layout.user_grid_view_item,null);
        image = convertView.findViewById(R.id.imaeg_user_grid_view);
        image.setImageBitmap(images.get(position));
        return convertView;
    }
}
