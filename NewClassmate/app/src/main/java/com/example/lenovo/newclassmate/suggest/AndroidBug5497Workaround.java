package com.example.lenovo.newclassmate.suggest;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

//public class AndroidBug5497Workaround {
//
//    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
//    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.
//
//    public static void assistActivity (Activity activity) {
//        new AndroidBug5497Workaround(activity);
//    }
//
//    private View mChildOfContent;
//    private int usableHeightPrevious;
//    private FrameLayout.LayoutParams frameLayoutParams;
//
//    private AndroidBug5497Workaround(Activity activity) {
//        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
//        mChildOfContent = content.getChildAt(0);
//        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                possiblyResizeChildOfContent();
//            }
//        });
//        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
//    }
//
//    private void possiblyResizeChildOfContent() {
//        int usableHeightNow = computeUsableHeight();
//        if (usableHeightNow != usableHeightPrevious) {
//            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
//            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
//            if (heightDifference > (usableHeightSansKeyboard/4)) {
//                // keyboard probably just became visible
//                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
//            } else {
//                // keyboard probably just became hidden
//                frameLayoutParams.height = usableHeightSansKeyboard;
//            }
//            mChildOfContent.requestLayout();
//            usableHeightPrevious = usableHeightNow;
//        }
//    }
//
//    private int computeUsableHeight() {
//        Rect r = new Rect();
//        mChildOfContent.getWindowVisibleDisplayFrame(r);
//        return (r.bottom - r.top);
//    }
//
//}
public class AndroidBug5497Workaround {
    public static void assistActivity(View content) {
        new AndroidBug5497Workaround(content);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private ViewGroup.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(View content) {
        if (content != null) {
            mChildOfContent = content;
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent();
                }
            });
            frameLayoutParams = mChildOfContent.getLayoutParams();
        }
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        //计算视图可视高度
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }
}

