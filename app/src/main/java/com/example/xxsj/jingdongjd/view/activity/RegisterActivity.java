package com.example.xxsj.jingdongjd.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.view.dao.MyZhuceDao;

/**
 * Created by Administrator on 2017/9/13.
 */

public class RegisterActivity extends Activity{

    private EditText username;
    private EditText password;
    private Button register;
    private MyZhuceDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        dao = new MyZhuceDao(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this,"输入的用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    dao.addInfo(name,pwd);
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void initView() {
        username = (EditText) findViewById(R.id.register_et_usename);
        password = (EditText) findViewById(R.id.register_et_password);
        register = (Button) findViewById(R.id.btn_register);
    }
}
