package com.example.xxsj.jingdongjd.view.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xxsj.jingdongjd.bean.UserInfoBean;
import com.example.xxsj.jingdongjd.view.sqlite.MyZhuceSqilte;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/13.
 */

public class MyZhuceDao {

    private  SQLiteDatabase db;

    public MyZhuceDao(Context context){
        MyZhuceSqilte myZhuceSqilte = new MyZhuceSqilte(context);
        db = myZhuceSqilte.getWritableDatabase();
    }
    public void addInfo(String username,String password){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        db.insert("zhuce",null,values);
    }
    public ArrayList<UserInfoBean> selectInfo(){
        Cursor cursor = db.query(false, "zhuce", null, null, null, null, null, null, null);
        ArrayList<UserInfoBean> userInfoBeens = new ArrayList<>();
        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setPassword(password);
            userInfoBean.setUsername(username);
            userInfoBeens.add(userInfoBean);
        }
        return userInfoBeens;
    }

}
