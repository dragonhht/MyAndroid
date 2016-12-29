package com.example.dragon.myandroid;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by huang on 16-12-29.
 *
 * 查询联系人姓名
 *
 */

public class Tel_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tel);



        //获取ContentResolver对象
        ContentResolver contentResolver=getContentResolver();

        //查询联系人
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,new  String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
        if (cursor!=null){
            while(cursor.moveToNext()){
                int id= cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("这里","__________id:"+id);
                Log.i("这里","__________name:"+name);
                //查询电话号码
                Cursor cursor1=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);
                if (cursor1!=null){
                    while (cursor1.moveToNext()){
                        int phone_type=cursor1.getInt(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        if (phone_type== ContactsContract.CommonDataKinds.Phone.TYPE_HOME){
                            Log.i("这里","__________家庭电话:"+cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }else{
                            Log.i("这里","__________手机:"+cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                    }
                }
                cursor1.close();
                Log.i("这里","___________________________________________________");

            }
        }
        cursor.close();




    }
}
