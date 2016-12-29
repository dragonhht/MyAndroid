package com.example.dragon.myandroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

/**
 * Created by huang on 16-12-29.
 *
 * SQLite的使用
 *
 */

public class Sqlite_Activity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_one);
        Log.i("这里","已进入");

        //每个程序都有一个自己的数据库，默认情况下互不干扰

        //创建一个数据库并且打开
        //openOrCreateDatabase参数分别为：数据库名，权限
        db=openOrCreateDatabase("user.db",MODE_PRIVATE,null);

        //执行sql语句
        db.execSQL("create table if not exists usertb (_id integer primary key autoincrement,name text not null)");
        db.execSQL("insert into usertb(name) values('张三')");
        Log.i("这里","创建成功");
        //查询
        //该查询方法返回游标对象
        Cursor c=db.rawQuery("select * from usertb",null);

        //显示数据
        if (c!=null){
            //将游标移至第一条记录
            c.moveToFirst();
            while(c.moveToNext()){
                Log.i("这里", "id为：：：："+c.getInt(c.getColumnIndex("_id")));
                Log.i("这里", "name为：：：："+c.getString(c.getColumnIndex("name")));
            }

            //释放游标
            c.close();
        }
        //释放数据库对象
        //db.close();




        /*
         * 使用SQLiteOpenHelper
         */
//        DBOpenHelper helper=new DBOpenHelper(Sqlite_Activity.this,"user.db",null,1);
//        //SQLiteDatabase db2=helper.getReadableDatabase();//获取一个只读的数据库对象
//        SQLiteDatabase db3=helper.getWritableDatabase();

    }

    public void doClick(View v){

        switch (v.getId()){
            case R.id.addbtn:  //添加

                ContentValues values=new ContentValues();
                values.put("name","李四");
                db.insert("usertb",null,values);
                //清除ContentValues的值，清除后可继续存值
                values.clear();

                break;
            case R.id.delbtn:  //删除

                db.delete("usertb","_id=?",new String[]{"3"});

                break;

            case R.id.updatebtn:  //修改

                ContentValues values1=new ContentValues();
                values1.put("name","王五");
                db.update("usertb",values1,"name='张三'",null);

                break;

            case R.id.selbtn:  //查询

                Cursor cursor=db.query("usertb",null,null,null,null,null,null,null);
                //遍历查询结果
                if (cursor.moveToFirst()){
                    while (!cursor.isAfterLast()){
                        Log.i("这里", "id为：：：："+cursor.getInt(cursor.getColumnIndex("_id")));
                        Log.i("这里", "name为：：：："+cursor.getString(cursor.getColumnIndex("name")));
                        Log.i("这里","_____________________________________________________________________");

                        //移动至下一条
                        cursor.moveToNext();
                    }
                }

                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放数据库对象
        if (db!=null){
            db.close();
        }

    }
}
