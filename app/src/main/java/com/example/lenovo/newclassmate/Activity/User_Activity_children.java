package com.example.lenovo.newclassmate.Activity;


/*
用户个人资料展示界面
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.ValueCallback;
import android.widget.*;
import com.donkingliang.labels.LabelsView;
import com.example.lenovo.newclassmate.Activity.UserActivity.PersonDataActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.person_label_Activity;
import com.example.lenovo.newclassmate.Adapter.ImageAdapter;
import com.example.lenovo.newclassmate.Adapter.MyPagerAdapter;
import com.example.lenovo.newclassmate.Fragment.UserFragment;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.View.ActionSheetDialog;
import com.example.lenovo.newclassmate.View.HeadZoomScrollView;
import com.example.lenovo.newclassmate.View.ScaleImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class User_Activity_children extends Activity {

    private static final int TAKE_PHOTO_REQUEST_ONE= 11;
    public static final int TAKE_PHOTO_REQUEST_TWO = 22;
    private static final int REQUEST_CODE= 33;
    public static ValueCallback mFilePathCallback;
    View mFLayout;
    TextView mTextView;
    private TextView textView;
    GridView gridView;
    private ImageView add_image;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView uesr_head;
    private HeadZoomScrollView scrollView;
    private int imageHeight;
    private ImageView roundImage;
    private TextView back_view;
    private int id; //定义头像和添加相册的id
    private View viewdataLayout;
    public static String Person_signature=null;  //个性签名
    private SharedPreferences preferences;
    String sex;
    String userName;
    private TextView child_name;
    private TextView label;
    private LabelsView labelsView;
    private static ArrayList<String> arrayList; //存放标签的集合
    private static ArrayList<Bitmap> bitmap_images; //存放拍照和相册的集合
    private ImageAdapter imageAdapter;
    private int columnWidth;  //获取屏幕的宽度
    private ArrayList<File> image_list; //ScaleImageView显示的图片


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //读取本地缓存的用户数据
        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        userName=preferences.getString("userName",null);
        sex=preferences.getString("sex",null);
        Person_signature=preferences.getString("signature",null);


        setContentView(R.layout.activity_user_children);
        image_list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 21) { //5.0及以上系统才支持
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //满屏 将状态栏隐藏  SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和SYSTEM_UI_FLAG_LAYOUT_STABLE，注意两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将状态栏设置成透明色就可
        }


        mFLayout =  findViewById(R.id.fl_layout);
        textView = findViewById(R.id.textView);
        mTextView =  findViewById(R.id.child_person_signature);
        gridView =findViewById(R.id.gridView);
        add_image = findViewById(R.id.add_image);
        toolbar = findViewById(R.id.toolbar);
        uesr_head = findViewById(R.id.user_child_head_image);
        scrollView = findViewById(R.id.n_scroll_view);
        roundImage = findViewById(R.id.round_image);
        back_view = findViewById(R.id.back_textView);
        viewdataLayout = findViewById(R.id.data_text_layout);
        child_name = findViewById(R.id.child_name);
        //编辑标签
        label = findViewById(R.id.label_text);
        //自定义标签
        labelsView = findViewById(R.id.user_child_labels);
        child_name.setText(userName);

        if(UserFragment.bit==null) {
            if (sex.equals("男")) {
                roundImage.setImageResource(R.mipmap.boy);
            } else {
                roundImage.setImageResource(R.mipmap.girl);
            }
        }
        bitmap_images=new ArrayList<>();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //由一屏幕显示的项数决定
        columnWidth = dm.widthPixels;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                bitmap_images.size() * columnWidth /3+bitmap_images.size(), LinearLayout.LayoutParams.WRAP_CONTENT);//
        gridView.setLayoutParams(params1);//设置宽和高
        gridView.setColumnWidth(columnWidth /3);//根据你一屏显示的项数决定
        gridView.setHorizontalSpacing(1); //水平距离
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(bitmap_images.size());//设置一行显示的总列数
        imageAdapter = new ImageAdapter(this,bitmap_images);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScaleImageView scaleImageView=new ScaleImageView(User_Activity_children.this);
                scaleImageView.setFiles(image_list,position);
                scaleImageView.setOnDeleteItemListener(position1 ->{
                    bitmap_images.remove(position1);
                    gridView.setLayoutParams(new LinearLayout.LayoutParams(
                            bitmap_images.size() * columnWidth/3+bitmap_images.size(), LinearLayout.LayoutParams.WRAP_CONTENT));//设置宽和高
                    gridView.setNumColumns(bitmap_images.size());//设置一行显示的总列数
                    imageAdapter = new ImageAdapter(User_Activity_children.this,bitmap_images);
                    gridView.setAdapter(imageAdapter);
                    imageAdapter.notifyDataSetChanged();
                    image_list.remove(position1); //删除ScaleImageView对应的图片
                });
                scaleImageView.create();
            }
        });

        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_Activity_children.this,person_label_Activity.class);
                startActivityForResult(intent,REQUEST_CODE);
                finish();
            }
        });
        viewdataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_Activity_children.this, PersonDataActivity.class);
                startActivity(intent);
                finish();
            }
        });
        uesr_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=v.getId();
                showDialog();
            }
        });
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=v.getId();
                showDialog();
            }
        });

        roundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=v.getId();
                showDialog();
            }
        });
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textView.setTextColor(Color.argb((int) 0, 13, 0, 0)); //开始设置标题文字透明
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
//
//                //第一种
//                int toolbarHeight = appBarLayout.getTotalScrollRange();
//
//                int dy = Math.abs(verticalOffset);  //返回绝对值
//
//
//                if (dy <= toolbarHeight) {
//                    float scale = (float) dy / toolbarHeight;
//                    float alpha = scale * 255;
//                    mFLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//                    textView.setTextColor(Color.argb((int) alpha, 13, 0, 0));
//                   // mTextView.setText("setBackgroundColor(Color.argb((int) "+(int) alpha+", 255, 255, 255))\n"+"mFLayout.setAlpha("+percent+")");
//                }
//            }
//        });
        // scrollView.setOnTouchListener(this);
        initListeners();
        changStatusIconCollor(true);  //将状态栏图标设置黑色
    }


    public void changStatusIconCollor(boolean setDark) {  //改变状态栏图标颜色
        //true:黑色
        // false：白色
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(setDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    private void initListeners() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = uesr_head.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                uesr_head.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = uesr_head.getHeight();
                scrollView.setOnScrollListener(new HeadZoomScrollView.OnScrollListener() {
                    @Override
                    public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY <= 0) {
//                          设置文字背景颜色，白色 透明
                            toolbar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
//                          设置文字颜色，白色 透明
                            textView.setTextColor(Color.argb((int) 0, 255, 255, 255));
                            Log.e("111","y <= 0");
                        } else if (scrollY > 0 && scrollY <= imageHeight) {
                            float scale = (float) scrollY / imageHeight;
                            float alpha = (255 * scale);
                            // 只是layout背景透明白色透明
                            toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                            //                          设置文字颜色，黑色，加透明度
                            textView.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                            Log.e("111","y > 0 && y <= imageHeight");
                        } else {
//							白色不透明
                            toolbar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                            //                          设置文字颜色
                            //黑色
                            textView.setTextColor(Color.argb((int) 255, 0, 0, 0));
                            Log.e("111","else");
                        }
                    }
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserFragment.bit!=null){
            roundImage.setImageBitmap(UserFragment.bit);
        }else  if(Person_signature!=null){
            mTextView.setText(Person_signature);
        }
        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        userName=preferences.getString("userName",null);
        child_name.setText(userName);


        arrayList=new ArrayList<>();
        arrayList.clear();
        String allLabel=preferences.getString("label",null);
        String Label[]=allLabel.split(",");
        if(Label.length>0)
        {
            for(String label : Label)
            {
                arrayList.add(label);
            }
            labelsView.setLabels(arrayList);
        }
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(User_Activity_children.this).builder()
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        takeForPhoto();
                    }
                }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        takeForPicture();
                    }
                }).setCancelable(false).setCanceledOnTouchOutside(true);

        dialog.show();
        //设置点击“取消”按钮监听，目的取消mFilePathCallback回调，可以重复调起弹窗
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFilePathCallback();
            }
        });
    }


    /*
    拍照片
     */
    private void  takeForPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_ONE);
    }


    /*
    取相册
     */
    private void takeForPicture()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_TWO);
    }


    /*
    取消
     */
    private void cancelFilePathCallback(){
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO_REQUEST_ONE:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(User_Activity_children.this, "取消了拍照", Toast.LENGTH_LONG).show();
                    return;
                }
                Uri a = data.getData();
                Bitmap photo = data.getParcelableExtra("data");
                if(id==R.id.add_image){  //添加个人图片
                    image_list.add(uri2File(a));
                    add_image.setImageBitmap(photo);
                } else  if(id==R.id.round_image){  //头像
                    UserFragment.bit=photo;
                    roundImage.setImageBitmap(photo);
                }  else if(id==R.id.user_child_head_image){  //主页图片
                    uesr_head.setImageBitmap(photo);
                }
                break;
            case TAKE_PHOTO_REQUEST_TWO:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(User_Activity_children.this, "点击取消从相册选择", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Bitmap photoBmp=null;
                    if(data!=null&&data.getData()!=null) {
                        Uri imageUri = data.getData();
                        photoBmp = getBitmapFormUri(this, imageUri);
                        if (id == R.id.add_image) {  //添加个人图片
                            image_list.add(uri2File(imageUri));
                            bitmap_images.add(photoBmp);
                            gridView.setLayoutParams(new LinearLayout.LayoutParams(
                                    bitmap_images.size() * columnWidth/3+bitmap_images.size(), LinearLayout.LayoutParams.WRAP_CONTENT));//设置宽和高
                            gridView.setNumColumns(bitmap_images.size());//设置一行显示的总列数
                            imageAdapter = new ImageAdapter(this,bitmap_images);
                            gridView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            Toast.makeText(User_Activity_children.this, "添加了一张图片", Toast.LENGTH_LONG).show();
                        } else if (id == R.id.round_image) {   //头像
                            UserFragment.bit=photoBmp;
                            roundImage.setImageBitmap(photoBmp);
                        } else if (id == R.id.user_child_head_image) {   //主页图片
                            uesr_head.setImageBitmap(photoBmp);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case REQUEST_CODE:
//                if(resultCode==person_label_Activity.RESULT_CODE){
//                    String allLabel=preferences.getString("label",null);
//                    String Label[]=allLabel.split(",");
//                    if(Label.length>0)
//                    {
//                        for(String label : Label)
//                        {
//                            arrayList.add(label);
//                        }
//                        labelsView.setLabels(arrayList);
//                    }
//                }
        }
    }
    /**
     * 通过uri获取图片并进行压缩
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /*
    将Uri转换成File
     */
    private File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }


}
