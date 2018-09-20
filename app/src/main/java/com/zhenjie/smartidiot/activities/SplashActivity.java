package com.zhenjie.smartidiot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.utils.ShareUtils;
import com.zhenjie.smartidiot.utils.StaticClass;
import com.zhenjie.smartidiot.utils.Utils;

import java.lang.ref.WeakReference;

/**
 * 文件名: SplashActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/1 10:42
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：闪屏页
 */
public class SplashActivity extends AppCompatActivity {

    private TextView tv_splash;
    private Handler handler = new MHandler(this);

    static class MHandler extends Handler {
        private WeakReference<SplashActivity> splashActivityWeakReference;
        public MHandler(SplashActivity splashActivity){
            this.splashActivityWeakReference = new WeakReference<SplashActivity>(splashActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (splashActivityWeakReference.get().ifFirst()) {
                        splashActivityWeakReference.get().startActivity(new Intent(splashActivityWeakReference.get(), GuideActivity.class));
                        splashActivityWeakReference.get().finish();
                    } else {
//                        调试环境
                        splashActivityWeakReference.get().startActivity(new Intent(splashActivityWeakReference.get(), LoginActivity.class));
//                        正式环境
//                        splashActivityWeakReference.get().startActivity(new Intent(splashActivityWeakReference.get(), MainActivity.class));
                        splashActivityWeakReference.get().finish();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        tv_splash = findViewById(R.id.tv_splash);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        //设置字体
        Utils.setFont(this,tv_splash);
    }

    private boolean ifFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    //block the back button
    @Override
    public void onBackPressed() {

    }
}