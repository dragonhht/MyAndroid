package com.example.dragon.myandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huang on 16-12-29.
 */

public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //首次创建数据库时调用,一般将建数据库，建表的操作写入此
    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    //当数据库版本发生变化时自动执行
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
