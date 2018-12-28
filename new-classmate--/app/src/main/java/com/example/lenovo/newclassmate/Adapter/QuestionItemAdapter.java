package com.example.lenovo.newclassmate.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.newclassmate.Fragment.Test.CourseTestFragment;
import com.example.lenovo.newclassmate.Fragment.Test.DormitoryTestFragment;
import com.example.lenovo.newclassmate.Fragment.Test.FriendTestFragment;
import com.example.lenovo.newclassmate.Fragment.Test.OrganizationTestFragment;
import com.example.lenovo.newclassmate.Fragment.Test.StartQuestionFragment;
import com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment.CourseTestResultFragment;
import com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment.DormitoryTestResultFragment;
import com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment.FriendTestResultFragment;
import com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment.OrganizationTestResultFragment;
import com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment.StartTestResultFragment;

/**
 * @author Eskii
 * 碎片适配器
 */

public class QuestionItemAdapter extends FragmentStatePagerAdapter {

    private int length = 0; //题目数组的长度
    private String tag; //测试的类型 开始测试 还是 分寝测试 还是好友推荐测试

    public QuestionItemAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public Fragment getItem(int position) { //获得碎片当前所在的页数，就是进行到第几题了

        if (tag.equals("start")) {

            if (position == length) {    //如果长度等于题目长度也就是说当前的fragment是最后一个问题，则返回一个测试结果页面，
                return new StartTestResultFragment();
            } else {                      //如果长度小于题目长度则返回下一题测试界面
                return new StartQuestionFragment(position);
            }

        } else if (tag.equals("dormitory")) {

            if (position == length) {
                return new DormitoryTestResultFragment();
            } else {
                return new DormitoryTestFragment(position);
            }

        } else if (tag.equals("friend")) {
            if (position == length) {
                return new FriendTestResultFragment();
            } else {
                return new FriendTestFragment(position);
            }
        } else if (tag.equals("organization")) {
            if (position == length) {
                return new OrganizationTestResultFragment();
            } else {
                return new OrganizationTestFragment(position);
            }
        } else {
            if (position == length) {
                return new CourseTestResultFragment();
            } else {
                return new CourseTestFragment(position);
            }
        }
    }

    @Override
    public int getCount() {
        return length + 1;
    }
}
