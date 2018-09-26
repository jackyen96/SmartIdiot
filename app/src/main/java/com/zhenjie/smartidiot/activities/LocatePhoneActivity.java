package com.zhenjie.smartidiot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.utils.L;
import com.zhenjie.smartidiot.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

public class LocatePhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPhone;
    private ImageView ivCompany;
    private TextView tvResult;
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt0,btDel,btLookUp;
    //标记位,用于在查询成功后，再按下数字键实现清空输入框的功能
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_phone);
        initView();
    }

    private void initView() {
        etPhone = findViewById(R.id.et_phone_num);
        ivCompany = findViewById(R.id.iv_company);
        tvResult = findViewById(R.id.tv_result);
        bt1 = findViewById(R.id.bt_1);
        bt1.setOnClickListener(this);
        bt2 = findViewById(R.id.bt_2);
        bt2.setOnClickListener(this);
        bt3 = findViewById(R.id.bt_3);
        bt3.setOnClickListener(this);
        bt4 = findViewById(R.id.bt_4);
        bt4.setOnClickListener(this);
        bt5 = findViewById(R.id.bt_5);
        bt5.setOnClickListener(this);
        bt6 = findViewById(R.id.bt_6);
        bt6.setOnClickListener(this);
        bt7 = findViewById(R.id.bt_7);
        bt7.setOnClickListener(this);
        bt8 = findViewById(R.id.bt_8);
        bt8.setOnClickListener(this);
        bt9 = findViewById(R.id.bt_9);
        bt9.setOnClickListener(this);
        bt0 = findViewById(R.id.bt_0);
        bt0.setOnClickListener(this);
        btDel = findViewById(R.id.bt_del);
        btDel.setOnClickListener(this);
        btDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etPhone.setText("");
                return false;
            }
        });
        btLookUp = findViewById(R.id.bt_look_up);
        btLookUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = etPhone.getText().toString();
        switch (v.getId()){
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
            case R.id.bt_3:
            case R.id.bt_4:
            case R.id.bt_5:
            case R.id.bt_6:
            case R.id.bt_7:
            case R.id.bt_8:
            case R.id.bt_9:
                if (flag == true){
                    flag = false;
                    str = "";
                    etPhone.setText("");
                }
                etPhone.setText(str+((Button)v).getText());
                etPhone.setSelection(str.length()+1);
                break;
            case R.id.bt_del:
                if (etPhone.getText().toString().length()==0){
                    return;
                }
                etPhone.setText(str.substring(0,str.length()-1));
                etPhone.setSelection(str.length()-1);
                break;
            case R.id.bt_look_up:
                tvResult.setText("");
                ivCompany.setBackground(null);
                if (TextUtils.isEmpty(str)){
                    break;
                }
                getPhoneLocate(str);
                break;
        }
    }

    private void getPhoneLocate(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone="+str+"&key="+StaticClass.PHONE_LOCATE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsJson(t);
            }
        });
    }

    private void parsJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            tvResult.append("归属地："+province+city+"\n");
            tvResult.append("区号："+areacode+"\n");
            tvResult.append("邮编："+zip+"\n");
            tvResult.append("运营商："+company+"\n");
            tvResult.append("类型："+card+"\n");
            switch (company){
                case "移动":
                    ivCompany.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    ivCompany.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    ivCompany.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }

            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
