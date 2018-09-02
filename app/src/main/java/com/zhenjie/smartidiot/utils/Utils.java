package com.zhenjie.smartidiot.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 文件名: Utils
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 20:25
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：工具类
 */
public class Utils {
    public static void setFont(Context mContext, TextView tv){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        tv.setTypeface(fontType);
    }
}