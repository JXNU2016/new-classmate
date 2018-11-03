package com.example.lenovo.newclassmate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Bean.QusetionOptionBean;
import com.example.lenovo.newclassmate.R;

import java.util.List;

/**
 * @author Eskii
 *         选项GridView的适配器
 */

public class OptionsGridViewAdapter extends BaseAdapter {

    private List<QusetionOptionBean> qusetionOptionBeanList;
    private Context context;

    public OptionsGridViewAdapter(List<QusetionOptionBean> qusetionOptionBeanList, Context context) {
        this.qusetionOptionBeanList = qusetionOptionBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return qusetionOptionBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return qusetionOptionBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.option_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.optionButton);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(qusetionOptionBeanList.get(i).getDetails());

        return view;

    }

    private class ViewHolder {
        private TextView textView;
    }
}
