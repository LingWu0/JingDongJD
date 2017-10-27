package com.example.xxsj.jingdongjd.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxsj.jingdongjd.R;
import com.example.xxsj.jingdongjd.bean.UserInfoBean;
import com.example.xxsj.jingdongjd.view.dao.MyZhuceDao;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView finish;
    private EditText etUsename;
    private ImageView cleanUserName;
    private EditText etPassword;
    private ImageView cleanPassword;
    private LinearLayout viewById;
    private Button btnLogin;
    private CheckBox checkShowOrHide;
    private ImageView qq_login;
    private SharedPreferences.Editor edit;
    private int color=0XffD3D3D3;
    private TextView register;
    private MyZhuceDao dao;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp = getSharedPreferences("qqdata", MODE_PRIVATE);
        dao = new MyZhuceDao(this);
        edit = sp.edit();
        initView();
        finish.setOnClickListener(this);
        cleanPassword.setOnClickListener(this);
        cleanUserName.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initView() {
        finish = (ImageView) findViewById(R.id.login_iv_finish);
        etUsename = (EditText) findViewById(R.id.login_et_usename);
        etPassword = (EditText) findViewById(R.id.login_et_password);
        cleanUserName = (ImageView) findViewById(R.id.clean_user_name);
        cleanPassword = (ImageView) findViewById(R.id.clean_password);
        checkShowOrHide = (CheckBox) findViewById(R.id.check_showorhide);
        btnLogin = (Button) findViewById(R.id.btn_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        register = (TextView) findViewById(R.id.register);
        etUsename.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etUsename != null) {
                        String nameText = etUsename.getText().toString().trim();
                        if (TextUtils.isEmpty(nameText)) {
                            cleanUserName.setVisibility(View.INVISIBLE);

                        } else{
                            cleanUserName.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    cleanUserName.setVisibility(View.INVISIBLE);

                }
            }
        });

        etUsename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cleanUserName.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etPassword != null) {
                        String nameText = etPassword.getText().toString().trim();
                        if (TextUtils.isEmpty(nameText)) {
                            cleanPassword.setVisibility(View.INVISIBLE);
                        } else {
                            cleanPassword.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    cleanPassword.setVisibility(View.INVISIBLE);

                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cleanPassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkShowOrHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editable pwText = etPassword.getText();
                String pwTextString;
                if (null == pwText) {
                    pwTextString = "";
                } else {
                    pwTextString = pwText.toString();
                }
                if (checkShowOrHide.isChecked()) {
                    // 设置EditText的密码为可见的
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 设置密码为隐藏的
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (!TextUtils.isEmpty(pwTextString)) {
                    etPassword.setSelection(pwTextString.length());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_iv_finish:
                finish();
                break;
            case R.id.clean_user_name:
                if (!TextUtils.isEmpty(etUsename.getText().toString())){
                    etUsename.setText("");
                    cleanUserName.setVisibility(View.INVISIBLE);
                    btnLogin.setBackgroundColor(color);
                }
                break;
            case R.id.clean_password:
                if (!TextUtils.isEmpty(etPassword.getText().toString())){
                    etPassword.setText("");
                    cleanPassword.setVisibility(View.INVISIBLE);
                    btnLogin.setBackgroundColor(color);
                }
                break;
            case R.id.btn_login:
                ArrayList<UserInfoBean> userInfoBeen = dao.selectInfo();
                for (UserInfoBean info: userInfoBeen) {
                    username = info.getUsername();
                    password = info.getPassword();
                }
                if (TextUtils.isEmpty(etUsename.getText().toString())||TextUtils.isEmpty(etPassword.getText().toString())){
                    Toast.makeText(this,"账号或密码不能为空，请重新输入",Toast.LENGTH_SHORT).show();
                }else if(!etUsename.getText().toString().equals(username)||!etPassword.getText().toString().equals(password)){
                    Toast.makeText(this,"账号或密码输入不正确，请重新输入",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.qq_login:
                UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String name = data.get("name");
            String iconurl = data.get("iconurl");
            //将获取到的用户头像与姓名，存储到SharedPreferences中
            edit.putString("name",name);
            edit.putString("iconurl",iconurl);
            edit.commit();
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
