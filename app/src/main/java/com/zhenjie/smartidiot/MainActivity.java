package com.zhenjie.smartidiot;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhenjie.smartidiot.activities.SettingActivity;
import com.zhenjie.smartidiot.fragment.BeautyFragment;
import com.zhenjie.smartidiot.fragment.IdiotFragment;
import com.zhenjie.smartidiot.fragment.UserFragment;
import com.zhenjie.smartidiot.fragment.WeChatFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> mTitle;
    private List<android.support.v4.app.Fragment> fragments;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.idiot_feature));
        mTitle.add(getString(R.string.we_chat_service));
        mTitle.add(getString(R.string.beauty_pic));
        mTitle.add(getString(R.string.user_features));
        fragments = new ArrayList<>();
        fragments.add(new IdiotFragment());
        fragments.add(new WeChatFragment());
        fragments.add(new BeautyFragment());
        fragments.add(new UserFragment());
    }


    private void initView() {
        tabLayout = findViewById(R.id.mTabLayout);
        viewPager = findViewById(R.id.mViewPager);
        floatingActionButton = findViewById(R.id.fb_setting);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setVisibility(View.GONE);
        //预加载
        viewPager.setOffscreenPageLimit(fragments.size());
        //viewpager滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    floatingActionButton.setVisibility(View.GONE);
                }else{
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
