package dragon.hht.com.mina;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import dragon.hht.com.mina.DataBase.MyDatabase;
import dragon.hht.com.mina.DataBase.Mydata;


public class MainActivity extends AppCompatActivity {

    private EditText userText,pwdText;
    private Button loginBtn,resetBtn;
    private CheckBox checkBox;
    private SharedPreferences.Editor editor;
    //记录输错次数
    private int num=0;
    private Intent intent;
    Mydata mydata;
    private int nowUser=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userText= (EditText) findViewById(R.id.username);
        pwdText= (EditText) findViewById(R.id.pwd);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        loginBtn= (Button) findViewById(R.id.loginBtn);
        resetBtn= (Button) findViewById(R.id.resetBtn);


        SharedPreferences sharedPreferences=getSharedPreferences("MyPassword",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String user=sharedPreferences.getString("username","");
        String pwds=sharedPreferences.getString("pwd","");

        if (user!=null&&user!=""){
            userText.setText(user);
        }
        if (pwds!=null&&user!=""){
            pwdText.setText(pwds);
        }

        intent=new Intent(MainActivity.this,Pwd_Activity.class);

        mydata=new Mydata(MainActivity.this);



    }

    //点击事件
    public void doClick(View v){
        switch (v.getId()){
            case R.id.loginBtn:  //登录

                Log.i("info","点击登录");
                String name= String.valueOf(userText.getText());
                Log.i("info","用户名"+name);
                String userpwd= String.valueOf(pwdText.getText());

                /*Cursor cursor=null;
                cursor=db.rawQuery("select * from users where name=? and pwd=?",new String[]{name,userpwd});*/
                boolean ok=mydata.isExists("users","name=? and pwd=?",new String[]{name,userpwd});

                if (ok){
                    nowUser=mydata.getId();
                    login();
                }else {
                    if (num<3){
                        num++;
                        Toast.makeText(this,"密码输错，您还有 "+(3-num)+"次机会",Toast.LENGTH_SHORT).show();
                    }else {
                        finish();
                    }
                }
                break;
            case R.id.resetBtn:  //重置

                if (userText.getText()!=null) {
                    userText.setText("");
                }
                if (pwdText.getText()!=null) {
                    pwdText.setText("");
                }

                break;
            case R.id.button:
                Intent intent1=new Intent(MainActivity.this,Regist_Activity.class);

                startActivity(intent1);
                break;
        }
    }

    //登录
    public void login(){

        if (checkBox.isChecked()){
            editor.putString("username", String.valueOf(userText.getText()));
            editor.putString("pwd", String.valueOf(pwdText.getText()));
            editor.commit();
            Bundle bundle=new Bundle();
            bundle.putInt("nowUser",nowUser);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }else {
            Bundle bundle=new Bundle();
            bundle.putInt("nowUser",nowUser);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }



}
