package com.example.dragon.myandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
/*
 *  SharedPreferencesch存取数据
 */
public class MainActivity extends AppCompatActivity {

    EditText username,pwd;
    CheckBox checkBox;
    Button login;
    SharedPreferences pef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建一个SharedPreferences对象
//      SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences preferences=getSharedPreferences("myPref",MODE_PRIVATE);
        //获取编辑器对象
        SharedPreferences.Editor editor=preferences.edit();
        //存数据
        editor.putString("username","张三");
        editor.putBoolean("status",true);
        editor.putInt("age",21);
        //提交数据
        editor.commit();


        //获取数据
        Log.i("这里","username="+preferences.getString("username",""));
        Log.i("这里","status="+preferences.getBoolean("status",false));
        Log.i("这里","age="+preferences.getInt("age",0));

        username=(EditText)findViewById(R.id.usertext);
        pwd=(EditText)findViewById(R.id.pwdtext);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        login=(Button)findViewById(R.id.login);


        pef=getSharedPreferences("userMessage",MODE_PRIVATE);

        if (username.getText()!=null){
            username.setText(pef.getString("username",""));
        }
        if (pwd.getText()!=null){
            pwd.setText(pef.getString("pwd",""));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){

                    SharedPreferences.Editor editor1=pef.edit();
                    editor1.putString("username",username.getText().toString());
                    editor1.putString("pwd",pwd.getText().toString());
                    editor1.commit();
                }
            }
        });

        Button sqlbtn=(Button)findViewById(R.id.sqlbtn);
        sqlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Sqlite_Activity.class);
                startActivity(intent);
            }
        });

        Button cp=(Button)findViewById(R.id.conpro);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Tel_Activity.class);
                startActivity(intent);
            }
        });

    }
}
