package dragon.hht.com.noname.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 游戏2 on 2017/1/13.
 *
 * 数据库的基本操作
 */

public class MyData {
    Context context;
    private int id=0;

    public MyData(Context context){
        this.context=context;
    }

    //添加
    public void insert(String tableName,ContentValues values){
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        db.insert(tableName, null, values);
        db.close();

    }

    //删除
    public void delete(String tableName,String whereClause,String[] whereArgs){
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        db.delete(tableName,whereClause,whereArgs);
        db.close();
    }

    //修改
    public void update(String tableName,ContentValues values,String whereClause,String[] whereArgs){
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        db.update(tableName,values,whereClause,whereArgs);
        db.close();
    }

    //查询是否存在
    public boolean isExists(String tableName,String whereClause,String[] whereArgs){
        boolean ok=false;
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+tableName+" where "+whereClause,whereArgs);
        if (cursor.moveToFirst()){
            ok=true;
            //获取id
            id=cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        db.close();
        return ok;
    }

    //查询需要的值
    public List<Map<String,String>> getValues(String tableName, String[] selections, String whereClause, String[] whereArgs){
        List<Map<String,String>> values=new ArrayList<>();
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        String sql="select ";
        if (selections!=null){
            for (String selection:selections){
                sql=sql+" "+selection;
            }
        }else{
            sql=sql+" *";
        }
        sql=sql+" from "+tableName;
        if (whereClause!=null){
            sql=sql+" where "+whereClause;
        }
        Cursor cursor=db.rawQuery(sql,whereArgs);
        int columnCount=cursor.getColumnCount();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                values.add(map);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return values;
    }
    //获取Id
    public int getId() {
        return id;
    }

    //查询最小Id
    public int getMinId(String tableName){
        int minId=-1;
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+tableName+" order by _id ",null);
        if (cursor.moveToFirst()){
            //获取id
            minId=cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        db.close();
        return minId;
    }

    //获取最大Id
    public int getMaxId(String tableName){
        int minId=-1;
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+tableName+" order by _id desc",null);
        if (cursor.moveToFirst()){
            //获取id
            minId=cursor.getInt(cursor.getColumnIndex("_id"));
        }
        cursor.close();
        db.close();
        return minId;
    }

    //删除表
    public void delTable(String tableName){
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        db.execSQL("drop table "+tableName);
        db.close();
    }

    //创建表
    public void createTeble(){
        MyDataBase myDatabase=new MyDataBase(context,"mydata.db",null,1);
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        //事情表
        db.execSQL("create table if not exists thing(_id integer primary key autoincrement,name text not null)");
        //评价表
        db.execSQL("create table if not exists evaluate(_id integer primary key autoincrement,thing_id integer not null,grade text,comment text,date text)");
        db.close();
    }

}
