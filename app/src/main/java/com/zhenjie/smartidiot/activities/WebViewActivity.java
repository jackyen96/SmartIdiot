package com.zhenjie.smartidiot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.utils.L;

/**
 * 文件名: WebViewActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/26 8:33 PM
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：展示微信精选详情的Activity
 */
public class WebViewActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    private void initView() {
        mProgressBar = findViewById(R.id.progress_bar);
        mWebView = findViewById(R.id.mWebView);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        L.d(url);
        getSupportActionBar().setTitle(title);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebChromeClient(new WebViewClient());
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){

        });
        mWebView.loadUrl(url);
    }

    public class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
