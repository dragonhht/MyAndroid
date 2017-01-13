package dragon.hht.com.mina;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dragon.hht.com.mina.DataBase.MyDatabase;
import dragon.hht.com.mina.DataBase.Mydata;

/**
 * Created by 游戏2 on 2017/1/5.
 */

public class Add_Activity extends AppCompatActivity {

    EditText add_orthername,add_name1,add_Pwd;
    Mydata mydata;
    private boolean flag=false;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpwd);

        add_orthername= (EditText) findViewById(R.id.add_orthername);
        add_name1= (EditText) findViewById(R.id.add_name1);
        add_Pwd= (EditText) findViewById(R.id.editText3);

        mydata=new Mydata(Add_Activity.this);
        bundle=getIntent().getExtras();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
    }

    //点击事件
    public void addclick(View v){
        switch (v.getId()){
            case R.id.addBtn:

                String otherName= String.valueOf(add_orthername.getText());
                Log.i("info","otherName"+otherName+";");
                String account= String.valueOf(add_name1.getText());
                Log.i("info","account"+account);
                String pwd= String.valueOf(add_Pwd.getText());
                Log.i("info","pwd"+pwd);
                Log.i("info","处理点击事件");
                if (otherName!=null&&!otherName.equals("")&&account!=null&&!account.equals("")&&pwd!=null&&!pwd.equals("")){


                    int nowUser=bundle.getInt("nowUser");

                   /* Cursor cursor=db.rawQuery("select * from pwds where othername=? and user_id=?",new String[]{otherName, String.valueOf(nowUser)});*/

                    boolean ok=mydata.isExists("pwds","othername=? and user_id=?",new String[]{otherName, String.valueOf(nowUser)});
                    if (ok){
                        Toast.makeText(Add_Activity.this,"该别名已存在",Toast.LENGTH_SHORT).show();
                    }else {
                        ContentValues values = new ContentValues();
                        values.put("othername", otherName);
                        values.put("name", account);
                        values.put("pwd", pwd);
                        values.put("user_id", nowUser);
//                        db.insert("pwds", null, values);
                        mydata.insert("pwds",values);
                        flag = true;
                        Toast.makeText(Add_Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(Add_Activity.this,"**(自动屏蔽)，你有信息未填",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.addBack:

                if (flag){
                    Intent intent=new Intent(Add_Activity.this,Pwd_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {
                    finish();
                }


                break;
        }
    }
}
