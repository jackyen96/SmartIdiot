package com.zhenjie.smartidiot.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhenjie.smartidiot.R;

/**
 * 文件名: IdiotFragment
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 20:57
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：主页fragment
 */
public class IdiotFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_idiot,null);
        return v;
    }
}