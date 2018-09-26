package com.zhenjie.smartidiot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.adapter.CourierAdapter;
import com.zhenjie.smartidiot.entity.ExpressData;
import com.zhenjie.smartidiot.utils.L;
import com.zhenjie.smartidiot.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.exceptions.OnErrorThrowable;

public class CourierActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_num;
    private Button btn_look_up;
    private ListView mListView;
    private List<ExpressData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }

    private void initView() {
        et_name = findViewById(R.id.et_company_name);
        et_num = findViewById(R.id.et_serial_num);
        btn_look_up = findViewById(R.id.bt_look_up);
        mListView = findViewById(R.id.mListView);
        mListView.setDivider(null);
        btn_look_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_look_up:
                //TODO 查询订单信息
                String name = et_name.getText().toString();
                String num = et_num.getText().toString();
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.EXPRESS_API_KEY + "&com=" + name + "&no=" + num;
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num)) {
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.i("json:" + t);
                            parsingJson(t);
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {
                            Toast.makeText(CourierActivity.this, getString(R.string.request_failer)+strMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, getString(R.string.edit_text_can_not_be_empty), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //解析数据
    private void parsingJson(String t) {
        mList.clear();
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            if (jsonResult==null){
                Toast.makeText(this, R.string.input_error, Toast.LENGTH_SHORT).show();
            }
            JSONArray list = jsonResult.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject jo = (JSONObject) list.get(i);
                ExpressData data = new ExpressData();
                data.setRemark(jo.getString("remark").equals("")?"null":jo.getString("remark"));
                data.setZone(jo.getString("zone").equals("")?"null":jo.getString("zone"));
                data.setDateTime(jo.getString("datetime").equals("")?"null":jo.getString("datetime"));
                mList.add(data);
            }
            //反转数据列表
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
