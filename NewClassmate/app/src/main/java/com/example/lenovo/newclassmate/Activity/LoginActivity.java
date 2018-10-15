package com.example.lenovo.newclassmate.Activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;
import com.example.lenovo.newclassmate.suggest.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_number) EditText mInput_number;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.spiner_textview)Spinner mSpiner_textView;
    @BindView(R.id.logo) ImageView mLogo;
    @BindView(R.id.scrollView_login) ScrollView mScrollView;
    @BindView(R.id.content) View mContent;

    private int screenHeight = 0;//屏幕高度
//    private float scale = 0.6f; //logo缩放比例
//    private int keyHeight = 0; //软件盘弹起后所占高度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
//        if(isFullScreen(this)){
//            AndroidBug5497Workaround.assistActivity(this);
//        }
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
//        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,VerifyActivity.str);
        mSpiner_textView.setAdapter(adapter);
        mSpiner_textView.setDropDownVerticalOffset(100);
       getSupportActionBar().hide();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        //设置标题
        ((CommonTitleBar) findViewById(R.id.titlebar_login)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
       // ((CommonTitleBar) findViewById(R.id.titlebar_login)).showCenterProgress();

    }

    private void initListener() {
       // iv_clean_phone.setOnClickListener(this);
        mInput_number.setOnClickListener(this);
       _passwordText.setOnClickListener(this);
       // clean_password.setOnClickListener(this);
//        iv_show_pwd.setOnClickListener(this);



//        mInput_number.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
//                    iv_clean_phone.setVisibility(View.VISIBLE);
//                } else if (TextUtils.isEmpty(s)) {
//                    iv_clean_phone.setVisibility(View.GONE);
//                }
//            }
//        });
//        _passwordText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
////                    clean_password.setVisibility(View.VISIBLE);
////                } else if (TextUtils.isEmpty(s)) {
////                    clean_password.setVisibility(View.GONE);
////                }
//                if (s.toString().isEmpty())
//                    return;
//                if (!s.toString().matches("[A-Za-z0-9]+")) {
//                    String temp = s.toString();
//                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
//                    s.delete(temp.length() - 1, temp.length());
//                    _passwordText.setSelection(s.length());
//                }
//            }
//        });
//        /**
//         * 禁止键盘弹起的时候可以滚动
//         */
//        mScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
//              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
//                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
//                    Log.e("wenzhihao", "up------>"+(oldBottom - bottom));
//                    int dist = mContent.getBottom() - bottom;
//                    if (dist>0){
//                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
//                        mAnimatorTranslateY.setDuration(300);
//                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
//                        mAnimatorTranslateY.start();
//                        zoomIn(mLogo, dist);
//                    }
//                   _signupLink.setVisibility(View.INVISIBLE);
//
//                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
//                    Log.e("wenzhihao", "down------>"+(bottom - oldBottom));
//                    if ((mContent.getBottom() - oldBottom)>0){
//                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
//                        mAnimatorTranslateY.setDuration(300);
//                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
//                        mAnimatorTranslateY.start();
//                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
//                        zoomOut(mLogo);
//                    }
//                    _signupLink.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }


//    public boolean isFullScreen(Activity activity) {
//        return (activity.getWindow().getAttributes().flags &
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)==WindowManager.LayoutParams.FLAG_FULLSCREEN;
//
   }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


//    public void onBackPressed() {
//        // Disable going back to the MainActivity
//        moveTaskToBack(true);
//    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String number = mInput_number.getText().toString();
        String password = _passwordText.getText().toString();

        if (number.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(number).matches()) {
            mInput_number.setError("输入有效的学号");
            valid = false;
        } else {
            mInput_number.setError(null);
            if (password.isEmpty() || password.length() < 4 ) {
                _passwordText.setError("密码错误");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

        }
        return valid;
    }

//    /**
//     * 缩小
//     * @param view
//     */
//    public void zoomIn(final View view, float dist) {
//        view.setPivotY(view.getHeight());
//        view.setPivotX(view.getWidth() / 2);
//        AnimatorSet mAnimatorSet = new AnimatorSet();
//        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
//        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
//        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);
//
//        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
//        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
//        mAnimatorSet.setDuration(300);
//        mAnimatorSet.start();
//    }
//
//    /**
//     * f放大
//     * @param view
//     */
//    public void zoomOut(final View view) {
//        view.setPivotY(view.getHeight());
//        view.setPivotX(view.getWidth() / 2);
//        AnimatorSet mAnimatorSet = new AnimatorSet();
//
//        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
//        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
//        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);
//
//        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
//        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
//        mAnimatorSet.setDuration(300);
//        mAnimatorSet.start();
//    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
//            case R.id.iv_clean_phone:
//                mInput_number.setText("");
//                break;
            case R.id.input_name:
                getKeyboardHeight();
                 break;
            case R.id.input_mobile:
                getKeyboardHeight();
                break;
//            case R.id.iv_show_pwd:
//                if (_passwordText.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//                    _passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
//                } else {
//                    _passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
//                }
//                String pwd = _passwordText.getText().toString();
//                if (!TextUtils.isEmpty(pwd))
//                    _passwordText.setSelection(pwd.length());
//                break;
      }
  }

    private void getKeyboardHeight(){
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                int heightDifference = screenHeight - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > screenHeight / 3;
                if (isKeyboardShowing) {
                    changeScrollView();
                    //移除布局变化监听
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
    }
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //将ScrollView滚动到底
                mScrollView.fullScroll(View.FOCUS_DOWN);
            }
        }, 100);
    }

//    private void controlKeyboardLayout(final ScrollView root, final Activity context) {
//           root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                public void onGlobalLayout() {
//
//
//             Rect rect = new Rect();
//             root.getWindowVisibleDisplayFrame(rect);
//             int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
//             //若不可视区域高度大于100，则键盘显示
//             if (rootInvisibleHeight > 100) {
//                 int[] location = new int[2];
//                 View focus = context.getCurrentFocus();
//                 if (focus != null) {
//                      focus.getLocationInWindow(location);
//                      int scrollHeight = (location[1] + focus.getHeight()) - rect.bottom;
//                      if (rect.bottom < location[1] + focus.getHeight()) {
//                          root.scrollTo(0, scrollHeight);
//                      }
//                 }
//             } else {
//                //键盘隐藏
//                root.scrollTo(0, 0);
//                           }
//                       }
//         });
//    }


}
