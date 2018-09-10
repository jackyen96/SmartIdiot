package com.zhenjie.smartidiot.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhenjie.smartidiot.R;
import com.zhenjie.smartidiot.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 文件名: ResetPassActivity
 * 创建者: Jack Yan
 * 创建日期: 2018/9/2 17:49
 * 邮箱: cn.zhenjie@gmail.com
 * 描述：重置密码的Activity
 */
public class ResetPassActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btForgetPass,btResetPass;
    private EditText etOldPass,etNewPass,etRePass,etEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
    }

    private void initView() {
        btForgetPass = findViewById(R.id.bt_forget_password);
        btForgetPass.setOnClickListener(this);
        btResetPass = findViewById(R.id.bt_change_password);
        btResetPass.setOnClickListener(this);
        etEmail = findViewById(R.id.et_email);
        etNewPass = findViewById(R.id.et_pass_word);
        etOldPass = findViewById(R.id.et_old_password);
        etRePass = findViewById(R.id.et_reenter_pass_word);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_forget_password:
                //获取输入框的邮箱
                final String email = etEmail.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(email)){
                    //发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ResetPassActivity.this, "邮箱已发送至" + email, Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ResetPassActivity.this, "邮箱发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, R.string.edit_text_can_not_be_empty, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_change_password:
                String oldPass = etOldPass.getText().toString();
                String newPass = etNewPass.getText().toString();
                String rePass = etRePass.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(oldPass)&!TextUtils.isEmpty(newPass)&!TextUtils.isEmpty(rePass)){
                    if (newPass.equals(rePass)){
                        MyUser.updateCurrentUserPassword(oldPass, newPass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ResetPassActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ResetPassActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}