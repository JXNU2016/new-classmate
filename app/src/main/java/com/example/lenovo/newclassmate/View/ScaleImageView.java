package com.example.lenovo.newclassmate.View;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.lenovo.newclassmate.Adapter.MyPagerAdapter;
import com.example.lenovo.newclassmate.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2018/12/5.
 */

public class ScaleImageView {

        private static final byte URLS = 0;
        private static final byte FILES = 1;
        private byte status;

        private Activity activity;

        private List<String> urls;
        private List<File> files;
        private List<File> downloadFiles;

        private int selectedPosition;  //表示当前被选中ViewPager的item的位置

        private Dialog dialog;

        private ImageView delete;
        private ImageView download;
        private TextView imageCount;
        private ViewPager viewPager;

        private List<View> views;  //ViewPager适配的数据源
        public MyPagerAdapter adapter;

        private OnDeleteItemListener listener;
        private int startPosition; //打开大图查看器时，想要查看ViewPager的item的位置

        public ScaleImageView(Activity activity) {
            this.activity = activity;
            init();
        }

        public void setUrls(List<String> urls, int startPosition) {
            if (this.urls == null) {  //网络查看模式
                this.urls = new ArrayList<>();
            } else {
                this.urls.clear();
            }
            this.urls.addAll(urls);
            status = URLS;  //给类设置状态
            delete.setVisibility(View.GONE);
            if (downloadFiles == null) {   //清空或者初始化下载的图片
                downloadFiles = new ArrayList<>();
            } else {
                downloadFiles.clear();
            }
            this.startPosition = startPosition++;
            String text = startPosition + "/" + urls.size();
            imageCount.setText(text);
        }

        public void setFiles(List<File> files, int startPosition) {
            if (this.files == null) {
                this.files = new LinkedList<>();
            } else {
                this.files.clear();
            }
            this.files.addAll(files);
            status = FILES;
            download.setVisibility(View.GONE);
            this.startPosition = startPosition++;
            String text = startPosition + "/" + files.size();
            imageCount.setText(text);
        }

        public void setOnDeleteItemListener(OnDeleteItemListener listener) {
            this.listener = listener;
        }

        private void init() {
            RelativeLayout relativeLayout = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.dialog_scale_image, null);
            ImageView close = (ImageView) relativeLayout.findViewById(R.id.scale_image_close);
            delete = (ImageView) relativeLayout.findViewById(R.id.scale_image_delete);
            download = (ImageView) relativeLayout.findViewById(R.id.scale_image_save);
            imageCount = (TextView) relativeLayout.findViewById(R.id.scale_image_count);//显示当前用户浏览第几张图的TextView
            viewPager = (ViewPager) relativeLayout.findViewById(R.id.scale_image_view_pager);
            dialog = new Dialog(activity, R.style.Dialog_Fullscreen);
            dialog.setContentView(relativeLayout);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int size = views.size();
                    files.remove(selectedPosition);
                    if (listener != null) {
                        listener.onDelete(selectedPosition);
                    }
                    viewPager.removeView(views.remove(selectedPosition));
                    if (selectedPosition != size) {
                        int position = selectedPosition + 1;
                        String text = position + "/" + views.size();
                        imageCount.setText(text);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                downloadFiles.get(selectedPosition).getAbsolutePath(),
                                downloadFiles.get(selectedPosition).getName(), null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(viewPager, "图片保存成功", Snackbar.LENGTH_SHORT).show();
                }
            });
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    selectedPosition = position;
                    String text = ++position + "/" + views.size();
                    imageCount.setText(text);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        /*
        加载图片并启动大图查看器
         */
        public void create() {
            dialog.show();
            views = new ArrayList<>();
            adapter = new MyPagerAdapter(views, dialog);
            if (status == URLS) {
                for (String url : urls) {
                    FrameLayout frameLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.view_scale_image, null);
                    SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) frameLayout.findViewById(R.id.scale_image_view);
                    views.add(frameLayout);
                    IOThread.getSingleThread().execute(() -> {
                        File downLoadFile;
                        try {
                            downLoadFile = Glide.with(activity).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                            downloadFiles.add(downLoadFile);
                            activity.runOnUiThread(() -> imageView.setImage(ImageSource.uri(Uri.fromFile(downLoadFile))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                viewPager.setAdapter(adapter);
            } else if (status == FILES) {
                for (File file : files) {
                    FrameLayout frameLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.view_scale_image, null);
                    SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) frameLayout.findViewById(R.id.scale_image_view);
                    views.add(frameLayout);
                    imageView.setImage(ImageSource.uri(Uri.fromFile(file)));
                }
                viewPager.setAdapter(adapter);
            }
            viewPager.setCurrentItem(startPosition);
        }



        public interface OnDeleteItemListener {
            void onDelete(int position);
        }



}
