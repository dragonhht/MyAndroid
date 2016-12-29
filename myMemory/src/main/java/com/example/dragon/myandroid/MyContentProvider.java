package com.example.dragon.myandroid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by huang on 16-12-29.
 */

public class MyContentProvider extends ContentProvider{
    @Override
    public boolean onCreate() {
        return false;
    }

    //根据Uri查询selection指定的条件所匹配到的全部记录
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    //返回当前Uri的MINE类型，如果该URi对应的数据有多条记录，那么MINE类型字符串就是
    //月以vnd.android.dir/开头，否则就是以vnd.android.cursor.item/开头
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    //根据Uri插入values对应的数据
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }


    //根据Uri删除selection指定的条件所匹配到的全部记录
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }


    //根据Uri修改selection指定的条件所匹配到的全部记录
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
