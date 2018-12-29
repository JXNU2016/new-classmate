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

public  class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<List<String>> list;
    private List<Group> groups;
    Context context;

    public  MyExpandableListViewAdapter(Context context , List<List<String>> list,List<Group> groups){
        this.context = context;
        this.list = list;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    // i 表示的是分组的位置i =groupPosition ，i1表示的是子列表的位置i1 = childPosition
    public Object getChild(int i, int i1) {
        return list.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    class  GroupViewHolder{
        TextView textView;
        ImageView imageView1;
        ImageView imageView2;

    }
    class  ChildViewHolder{
        TextView textView;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;

        if (view == null){
            view= LayoutInflater.from(context).inflate(R.layout.club_group,viewGroup,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.textView= (TextView)view.findViewById(R.id.group_item);
            groupViewHolder.imageView1= (ImageView)view.findViewById(R.id.stexpand_img1);
            groupViewHolder.imageView2= (ImageView)view.findViewById(R.id.stexpand_img2);
            view.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.textView.setText(groups.get(i).getText());
        groupViewHolder.imageView1.setImageResource(groups.get(i).getImg());
        groupViewHolder.imageView2.setImageResource(groups.get(i).getIamg());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.club_child,viewGroup,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.textView =(TextView)view.findViewById(R.id.club_childname);
            view.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.textView.setText(list.get(i).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}








