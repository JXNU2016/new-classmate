package com.example.lenovo.newclassmate.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.newclassmate.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends BaseAdapter {

    public static class Item {
        public String title;
        public String text;
}
    private List<Item> list;

    public NoticeAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Item getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = getItem(position);
        holder.title.setText(item.title);
        holder.text.setText(item.text);
        return convertView;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView text;

        public ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.title);
            this.text = (TextView) view.findViewById(R.id.text);
        }
    }

}

