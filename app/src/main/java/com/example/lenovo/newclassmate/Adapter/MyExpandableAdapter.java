package com.example.lenovo.newclassmate.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Bean.Group;
import com.example.lenovo.newclassmate.R;

import java.util.List;


public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private  List<Group>groups;
    private  List<List<String>> list;
    Context context;
    //构造函数将List传进
    public MyExpandableAdapter(Context context,List<List<String>> list,List<Group>groups) {
        this.list = list;
        this.context = context;
        this.groups= groups;
    }

    //        获取分组的个数
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int i) {
        return list.get(i).size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }
    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).get(i1);
    }
    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int i) {
        return i;
    }
    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //        获取显示指定分组的视图
    @Override
    //       所有方法都是继承BaseExpandableListAdapter所必须实现的方法
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Groupitem groupitem;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.course_group,viewGroup,false);
            groupitem=new Groupitem();
            groupitem.textView=(TextView)view.findViewById(R.id.group_item);
            groupitem.imageView_1 = (ImageView)view.findViewById(R.id.expand_img1);
            groupitem.imageView_2 = (ImageView)view.findViewById(R.id.expand_img2);
            view.setTag(groupitem);
        }else {
            groupitem=(Groupitem) view.getTag();
        }
        groupitem.textView.setText(groups.get(i).getText());
        groupitem.imageView_1.setImageResource(groups.get(i).getImg());
        groupitem.imageView_2.setImageResource(groups.get(i).getIamg());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        chilItem chilItem;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.course_child,viewGroup,false);
            chilItem=new chilItem();
            chilItem.textView=(TextView)view.findViewById(R.id.chil_item);
            view.setTag(chilItem);
        }else {
            chilItem=(chilItem)view.getTag();
        }
        chilItem.textView.setText(list.get(i).get(i1));
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class Groupitem{
        TextView textView;
        ImageView imageView_1;
        ImageView imageView_2;
    }
    class chilItem{
        TextView textView;
    }
}



