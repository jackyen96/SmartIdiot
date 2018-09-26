package com.zhenjie.smartidiot.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.adapter.ChatListAdapter;
import com.zhenjie.smartidiot.application.BaseApplication;
import com.zhenjie.smartidiot.entity.ChatListData;
import com.zhenjie.smartidiot.utils.L;
import com.zhenjie.smartidiot.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名: IdiotFragment
 * 创建者: Jack Yan
 * 创建日期: 2018/8/31 20:57
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：主页fragment
 */
public class IdiotFragment extends Fragment implements View.OnClickListener {
    private Button btSend;
    private EditText editText;
    private ListView mListView;
    private List<ChatListData> mList = new ArrayList<>();
    private String userName;
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_idiot, null);
        findView(v);
        btSend.setOnClickListener(this);
        adapter = new ChatListAdapter(getActivity(),mList);
        mListView.setAdapter(adapter);
        BaseApplication application = (BaseApplication) getActivity().getApplication();
        userName = application.getUserName();
        return v;
    }

    private void findView(View v) {
        btSend = v.findViewById(R.id.btn_send);
        editText = v.findViewById(R.id.et_text);
        mListView = v.findViewById(R.id.mChatListView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String str = editText.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    ChatListData mMsg = new ChatListData();
                    mMsg.setText(str);
                    mMsg.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
                    mList.add(mMsg);
                    adapter.notifyDataSetChanged();
                    mListView.smoothScrollToPosition(mList.size()-1);
                    editText.setText("");
                    String url = "http://openapi.tuling123.com/openapi/api/v2";
                    HttpParams params = new HttpParams();
                    params.putJsonParams("{\n" +
                            "\t\"reqType\":0,\n" +
                            "    \"perception\": {\n" +
                            "        \"inputText\": {\n" +
                            "            \"text\": \""+str+"\"\n" +
                            "        }\n" +
                            "    },\n" +
                            "    \"userInfo\": {\n" +
                            "        \"apiKey\": \""+StaticClass.CHAT_ROBOT_KEY+"\",\n" +
                            "        \"userId\": \""+userName+"\"\n" +
                            "    }\n" +
                            "}");
                    RxVolley.jsonPost(url, params, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            String bacStr = "";
                            try {
                                JSONObject jsonObject = new JSONObject(t);
                                JSONArray result = jsonObject.getJSONArray("results");
                                JSONObject resultObject = result.getJSONObject(0);
                                JSONObject values = resultObject.getJSONObject("values");
                                bacStr = values.getString("text");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ChatListData bMsg = new ChatListData();
                            bMsg.setText(bacStr);
                            bMsg.setType(ChatListAdapter.VALUE_LEFT_TEXT);
                            mList.add(bMsg);
                            adapter.notifyDataSetChanged();
                            mListView.smoothScrollToPosition(mList.size()-1);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), getString(R.string.edit_text_can_not_be_empty), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}