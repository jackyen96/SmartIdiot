package com.zhenjie.smartidiot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhenjie.smartidiot.MainActivity;
import com.zhenjie.smartidiot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名: GuideActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/1 11:40
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View v1, v2, v3;
    //小圆点
    private ImageView point1, point2, point3, ivSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        ivSkip = findViewById(R.id.iv_skip);
        ivSkip.setOnClickListener(this);
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        setPointImg(true, false, false);
        viewPager = findViewById(R.id.mViewPager);
        v1 = View.inflate(this, R.layout.pager_item1, null);
        v2 = View.inflate(this, R.layout.pager_item2, null);
        v3 = View.inflate(this, R.layout.pager_item3, null);
        v3.findViewById(R.id.bt_enter_main).setOnClickListener(this);
        mList.add(v1);
        mList.add(v2);
        mList.add(v3);
        viewPager.setAdapter(new GuideAdapter());
        //监听ViewPager的滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false, false);
                        ivSkip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false, true, false);
                        ivSkip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false, false, true);
                        ivSkip.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_enter_main:
            case R.id.iv_skip:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView(mList.get(position));
        }
    }

    //设置小圆点的显示图片
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING HERE
    }
}