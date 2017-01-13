package dragon.hht.com.mina;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import dragon.hht.com.mina.DataBase.MyDatabase;
import dragon.hht.com.mina.DataBase.Mydata;
import dragon.hht.com.mina.Utils.BaseActivity;

/**
 * Created by 游戏2 on 2017/1/5.
 */

public class Regist_Activity extends BaseActivity {

    EditText registname,pwdregist,repwdregist,registmessage;
    Mydata mydata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);



        registname= (EditText) findViewById(R.id.registname);
        pwdregist= (EditText) findViewById(R.id.pwdregist);
        repwdregist= (EditText) findViewById(R.id.repwdregist);
        registmessage= (EditText) findViewById(R.id.registmessage);

        mydata=new Mydata(Regist_Activity.this);



    }



    public void registClick(View v){
        switch (v.getId()){
            case R.id.registBtn:

                String name= String.valueOf(registname.getText());
                String pwd= String.valueOf(pwdregist.getText());
                String repwd= String.valueOf(repwdregist.getText());
                String message= String.valueOf(registmessage.getText());
                if (pwd.equals(repwd)){
                    if (message!=null&&name!=null&&pwd!=null&&message!=""&&pwd!=""&&repwd!=""&message!=""){


                        boolean ok=mydata.isExists("users","name=?",new String[]{name});
                        if (ok){
                            Toast.makeText(Regist_Activity.this,"该用户已存在",Toast.LENGTH_SHORT).show();
                        }else {
                            ContentValues values=new ContentValues();
                            values.put("name",name);
                            values.put("pwd",pwd);
                            values.put("message",message);
                            mydata.insert("users",values);
                            Toast.makeText(Regist_Activity.this,"用户注册成功，请登录",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Regist_Activity.this,MainActivity.class);
                            startActivity(intent);
                        }

                    }else {
                        Toast.makeText(Regist_Activity.this,"某些信息不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Regist_Activity.this,"两次密码不匹配",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.registReset:

                if (registname.getText()!=null){
                    registname.setText("");
                }
                if (pwdregist.getText()!=null){
                    pwdregist.setText("");
                }
                if (repwdregist.getText()!=null){
                    repwdregist.setText("");
                }
                if (registmessage.getText()!=null){
                    registmessage.setText("");
                }

                break;
        }
    }


}
