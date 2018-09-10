package com.zhenjie.smartidiot.view;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zhenjie.smartidiot.R;

/**
 * 文件名: CustomDialog
 * 创建者: Jack Yan
 * 创建日期: 2018/9/5 23:30
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：自定义Dialog
 */
public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, layout, style, Gravity.CENTER);
    }

    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context, style);

        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity) {
        this(context, width, height, layout, style, gravity, R.style.pop_anim_style);
    }
}