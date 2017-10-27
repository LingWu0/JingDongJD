package com.example.xxsj.jingdongjd.view.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/9/13.
 */

public class MyZhuceSqilte extends SQLiteOpenHelper{
    public MyZhuceSqilte(Context context) {
        super(context,"zhuce.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table zhuce(_id Integer primary key autoincrement,username varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
