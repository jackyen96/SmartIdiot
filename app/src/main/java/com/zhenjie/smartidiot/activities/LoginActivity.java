package com.zhenjie.smartidiot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.entity.MyUser;
import com.zhenjie.smartidiot.utils.ShareUtils;
import com.zhenjie.smartidiot.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 文件名: LoginActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/1 19:16
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：登录Activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister, btnLogin;
    private EditText etName, etPassword;
    private CheckBox cbRememberPassword;
    private TextView tvForget;
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tvForget = findViewById(R.id.tv_forget_pass);
        tvForget.setOnClickListener(this);
        cbRememberPassword = findViewById(R.id.cb_remember_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        etName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_pass_word);


        dialog = new CustomDialog(this,R.layout.dialog_loading, R.style.Theme_dialog);
        dialog.setCancelable(false);

        boolean isCheck = ShareUtils.getBoolean(this, "rememberPassword", false);
        cbRememberPassword.setChecked(isCheck);
        if (isCheck) {
            etPassword.setText(ShareUtils.getString(this, "password", ""));
            etName.setText(ShareUtils.getString(this, "name", ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_pass:
                startActivity(new Intent(this, ResetPassActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的值
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    //开始登陆，显示dialog
                    dialog.show();
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            if (e == null) {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                //保存是否记住密码
                                ShareUtils.putBoolean(LoginActivity.this, "rememberPassword", cbRememberPassword.isChecked());
                                //记住用户名和密码
                                if (cbRememberPassword.isChecked()) {
                                    ShareUtils.putString(LoginActivity.this, "name", etName.getText().toString());
                                    ShareUtils.putString(LoginActivity.this, "password", etPassword.getText().toString());
                                } else {
                                    ShareUtils.deleteShare(LoginActivity.this, "name");
                                    ShareUtils.deleteShare(LoginActivity.this, "password");
                                }
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败" + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}