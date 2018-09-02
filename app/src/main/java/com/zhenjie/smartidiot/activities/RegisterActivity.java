package com.zhenjie.smartidiot.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 文件名: RegisterActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/1 19:35
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：注册Activity
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUsername, etAge, etPassCode, etRePassCode, etDescription, etEmail;
    private RadioGroup rgGender;
    private Button btRegister;
    private boolean gender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
    }

    private void setListener() {
        //判断性别
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male:
                        gender = true;
                        break;
                    case R.id.rb_female:
                        gender = false;
                        break;
                }
            }
        });
        btRegister.setOnClickListener(this);
    }

    private void initView() {
        etUsername = findViewById(R.id.et_user_name);
        etAge = findViewById(R.id.et_user_age);
        etDescription = findViewById(R.id.et_about);
        etPassCode = findViewById(R.id.et_pass_word);
        etRePassCode = findViewById(R.id.reenter_pass_word);
        etEmail = findViewById(R.id.et_email);
        btRegister = findViewById(R.id.btn_register);
        rgGender = findViewById(R.id.rg_gender);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String name = etUsername.getText().toString();
                String age = etAge.getText().toString();
                String description = etDescription.getText().toString();
                String password = etPassCode.getText().toString();
                String rePassword = etRePassCode.getText().toString();
                String email = etEmail.getText().toString();

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(rePassword) & !TextUtils.isEmpty(email)) {

                    //判断两次密码是否一致
                    if (password.equals(rePassword)) {


                        //判断简介是否为空
                        if (!TextUtils.isEmpty(description)) {
                            description = "这个人很懒，什么都没有留下";
                        }

                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setAge(Integer.parseInt(age));
                        user.setGender(gender);
                        user.setDescription(description);
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}