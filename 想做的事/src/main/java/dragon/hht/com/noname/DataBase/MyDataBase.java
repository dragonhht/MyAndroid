package dragon.hht.com.noname.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 游戏2 on 2017/1/13.
 */

public class MyDataBase extends SQLiteOpenHelper {
    public MyDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //事情表
        db.execSQL("create table if not exists thing(_id integer primary key autoincrement,name text not null)");
        //评价表
        db.execSQL("create table if not exists evaluate(_id integer primary key autoincrement,thing_id integer not null,grade text,comment text,date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
