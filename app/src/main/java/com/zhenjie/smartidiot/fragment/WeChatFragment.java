package com.zhenjie.smartidiot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.activities.WebViewActivity;
import com.zhenjie.smartidiot.adapter.WeChatAdapter;
import com.zhenjie.smartidiot.entity.WeChatData;
import com.zhenjie.smartidiot.utils.L;
import com.zhenjie.smartidiot.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名: WeChatFragment
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 20:59
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：TODO
 */
public class WeChatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    private WeChatAdapter adapter;
    //标题
    private List<String> mTitleList = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wechat, null);
        initView(v);
        return v;
    }

    //初始化View
    private void initView(View v) {
        mListView = v.findViewById(R.id.mListView);
        mListView.setAdapter(adapter);
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsJSONInfo(t);
            }
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("title",mTitleList.get(position));
            intent.putExtra("url",mListUrl.get(position));
            startActivity(intent);
        });
    }

    private void parsJSONInfo(String t) {
        try {

            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                String title = jsonObject1.getString("title");
                data.setTitle(title);
                String source = jsonObject1.getString("source");
                data.setSource(source);
                String firstImg = jsonObject1.getString("firstImg");
                data.setImgUrl(firstImg);
                mList.add(data);
                mTitleList.add(title);
                String url = jsonObject1.getString("url");
                mListUrl.add(url);
            }
            adapter = new WeChatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
