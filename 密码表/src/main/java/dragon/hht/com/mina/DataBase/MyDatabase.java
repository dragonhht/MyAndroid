package dragon.hht.com.mina.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 游戏2 on 2017/1/5.
 */

public class MyDatabase extends SQLiteOpenHelper {


    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists users(_id integer primary key autoincrement,name text not null,pwd text not null,message text not null)");
        db.execSQL("create table if not exists pwds(_id integer primary key autoincrement,othername text not null,name text,pwd text not null,color integer,user_id integer)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
