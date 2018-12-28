package com.example.lenovo.newclassmate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    } //获取选项列表的大小，即有几个选项

    @Override
    public Object getItem(int i) {
        return qusetionOptionBeanList.get(i);
    }  //获取第几个选项

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

        viewHolder.textView.setText(qusetionOptionBeanList.get(i).getDetails());//将选项列表的第几个选项get(i)内容赋给Textview

        return view;

    }

    class ViewHolder {
        private TextView textView;
    }
}
