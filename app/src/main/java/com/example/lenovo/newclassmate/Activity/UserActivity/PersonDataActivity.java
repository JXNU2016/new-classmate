package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.Fragment.UserFragment;
import com.example.lenovo.newclassmate.R;
import com.example.pickerview.PickerView;
import com.example.pickerview.entity.PickerData;
import com.example.pickerview.listener.OnPickerClickListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.example.lenovo.newclassmate.Activity.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 个人资料设置
 */

public class PersonDataActivity extends BaseActivity implements View.OnClickListener {
    public final static String TAG = PersonDataActivity.class.getSimpleName();
    public static final String NAME = "name";
    public static final String SIGNATURE = "signature";
    public static final int REQUEST_CODE_FIRST = 1111;
    public static final int REQUEST_CODE_SECOND = 2222;
    public static final int REQUEST_CODE_THIRD=3333;
    private View person_name_;
    private View personSignature;
    private TextView person_name_text; //昵称
    private TextView personSignature_text;
    private SharedPreferences preferences;
    private String studentId;
    private String sex;
    private String year;
    private String month;
    private String day;
    private String province;
    private String county;
    private String city;
    private String userName;  //昵称
    private ImageView roundImageView;
    private TextView id_text;
    private TextView sex_textView;
    private TextView birthday_text;
    private TextView city_text;
    PickerView mPickerView;
    private View layout_sex;
    private View layout_birthday;
    private View layout_city;
    private TextView man;
    private TextView woman;
    private Dialog dialog;
    private View inflater;
    private View layout_label;
    private static ArrayList<String> lable_arrayList; //存放标签的集合
    private TextView lable_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        AllActivity.getInstance().addActivity(this);
        preferences = getSharedPreferences("land", Context.MODE_PRIVATE);
        studentId = preferences.getString("studentId", null);
        sex = preferences.getString("sex", null);
        year = preferences.getString("year", null);
        month = preferences.getString("month", null);
        day = preferences.getString("day", null);
        city = preferences.getString("city", null);
        userName = preferences.getString("userName", null);

        person_name_ = findViewById(R.id.person_name_layout);
        person_name_text = findViewById(R.id.person_name_textview); //昵称
        personSignature = findViewById(R.id.personSignature_layout);
        personSignature_text = findViewById(R.id.personSignature_textview);//个签
        roundImageView = findViewById(R.id.roundImageView);//头像
        id_text = findViewById(R.id.ID); //学号
        sex_textView = findViewById(R.id.sex_textView); //性别
        birthday_text = findViewById(R.id.birthday); //生日
        city_text = findViewById(R.id.city); //城市
        layout_sex = findViewById(R.id.persondata_sex);
        layout_birthday = findViewById(R.id.persondata_birthday);
        layout_city = findViewById(R.id.persondata_city);
        //个人标签
        layout_label = findViewById(R.id.persondata_music);
        lable_text = findViewById(R.id.music_text); //展示个人标签


        person_name_.setOnClickListener(this);
        personSignature.setOnClickListener(this);
        layout_label.setOnClickListener(this);
        layout_sex.setOnClickListener(this);
        if (UserFragment.bit == null) {
            if (sex.equals("男")) {
                roundImageView.setImageResource(R.mipmap.boy);
            } else {
                roundImageView.setImageResource(R.mipmap.girl);
            }
        }

        person_name_text.setText(userName);
        id_text.setText(studentId);
        sex_textView.setText(sex);
        birthday_text.setText(year + "-" + month + "-" + day);
        city_text.setText(city);
        initProvinceDatas();

        //设置标题
        ((CommonTitleBar) findViewById(R.id.person_data_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                    finish();
                }
            }
        });


        //设置选择地址模块
        PickerData data = new PickerData();       //选择器数据实体类封装 ,设置pickerview
        data.setFirstDatas(mProvinceDatas);     //设置数据，有多少层级自己确定
        data.setSecondDatas(mCitisDatasMap);
        data.setThirdDatas(mDistrictDatasMap);
        data.setFourthDatas(new HashMap<String, String[]>());
        mPickerView = new PickerView(this, data);       //初始化选择器
        layout_city.setOnClickListener(new View.OnClickListener() { //设置选择地址监听事件
            @Override
            public void onClick(View v) {
                mPickerView.show(city_text);          //显示选择器
            }
        });
        mPickerView.setOnPickerClickListener(new OnPickerClickListener() {       //选择器完成三级选择后点击回调
            @Override
            public void OnPickerClick(PickerData pickerData) {              //选择列表时触发的事件
                city_text.setText(pickerData.getSelectText());           //想获取单个选择项 PickerData内也有方法（弹出框手动关闭）
                province = pickerData.getFirstText();
                city = pickerData.getSecondText();
                county = pickerData.getThirdText();
                mPickerView.dismiss();//关闭选择器

            }

            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {       //点击确定按钮触发的事件（自动关闭）
                city_text.setText(pickerData.getSelectText());
                province = pickerData.getFirstText();
                city = pickerData.getSecondText();
                county = pickerData.getThirdText();
            }
        });

        //设置选择出生年月日模块
        layout_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(DateType.TYPE_YMD);
            }
        });

    }

    //触发出生日期时间选择函数
    private void showDatePickDialog(DateType type) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                String mat = "yyyy-MM-dd";
                String[] Data = new SimpleDateFormat(mat).format(date).split("-");
                year = Data[0];
                month = Data[1];
                day = Data[2];
                birthday_text.setText(new SimpleDateFormat(mat).format(date));
            }
        });
        dialog.show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(UserFragment.bit!=null){
            roundImageView.setImageBitmap(UserFragment.bit);
        }else  if(User_Activity_children.Person_signature!=null){
            personSignature_text.setText(User_Activity_children.Person_signature);
        }
       preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
       userName=preferences.getString("userName",null);
        person_name_text.setText(userName);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.person_name_layout:
                Intent intent_one =new Intent(PersonDataActivity.this,change_name_Activity.class);
                startActivityForResult(intent_one,REQUEST_CODE_FIRST);
                break;
            case R.id.personSignature_layout:
                Intent intent_two = new Intent(PersonDataActivity.this,Person_signature_Activity.class);
                startActivityForResult(intent_two,REQUEST_CODE_SECOND);
                break;
            case R.id.persondata_sex:
                show_dialog(v);
                break;
            case R.id.man:
                sex_textView.setText("男");
                dialog.dismiss();
                break;
            case R.id.woman:
                sex_textView.setText("女");
                dialog.dismiss();
                break;
            case R.id.persondata_music:
             Intent intent_three=new Intent(PersonDataActivity.this,person_label_Activity.class);
             startActivityForResult(intent_three, REQUEST_CODE_THIRD);
        }
    }

    private void show_dialog(View view){
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        inflater = LayoutInflater.from(this).inflate(R.layout.sex_dialog,null);
        man = inflater.findViewById(R.id.man);
        woman = inflater.findViewById(R.id.woman);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
        dialog.setContentView(inflater);  //将布局设置给dialog
        Window dialogwindow= dialog.getWindow();  //获取当前Activity所在的窗体
        dialogwindow.setGravity(Gravity.CENTER);  //设置dialog从中间弹出
//        WindowManager.LayoutParams lp=dialogwindow.getAttributes();   //获得窗体的属性
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"requestCode"+requestCode+",resultCode"+resultCode);
        if(requestCode== REQUEST_CODE_FIRST &&resultCode==change_name_Activity.RESULT_CODE){
            if(data!=null){
                String title=data.getStringExtra(NAME);
                person_name_text.setText(title);
            }
        }else if(requestCode== REQUEST_CODE_SECOND &&resultCode==Person_signature_Activity.RESULT_CODE){
            if(data!=null){
                String signature=data.getStringExtra(SIGNATURE);
                personSignature_text.setText(signature);
            }
        }else  if(requestCode==REQUEST_CODE_THIRD&&resultCode==person_label_Activity.RESULT_CODE){
            if(data!=null){
                String s="";
                lable_arrayList = data.getStringArrayListExtra("list");  //获取选中的标签集合
                Iterator<String> iterator=lable_arrayList.iterator();
                 while (iterator.hasNext()){
                    s=s+iterator.next()+"、";
                 }
                lable_text.setText(s.substring(0,s.length()-1));
            }
        }
    }
}
