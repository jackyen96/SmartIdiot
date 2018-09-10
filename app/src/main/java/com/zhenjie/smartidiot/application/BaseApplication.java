package com.zhenjie.smartidiot.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhenjie.smartidiot.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * 文件名: BaseApplication
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 19:05
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：Application 的基类
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APPLICATION_ID);
    }
}