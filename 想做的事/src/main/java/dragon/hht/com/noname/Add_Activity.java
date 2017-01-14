package dragon.hht.com.noname;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import dragon.hht.com.noname.DataBase.MyData;
import dragon.hht.com.noname.Utils.BaseActivity;
import dragon.hht.com.noname.Utils.SelectFile;

/**
 * Created by 游戏2 on 2017/1/13.
 */

public class Add_Activity extends BaseActivity {

    private Bundle bundle;
    private EditText filePath;
    private String getFilepath;
    private MyData myData;
    private boolean flag=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        filePath= (EditText) findViewById(R.id.filePath);

        myData=new MyData(Add_Activity.this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bundle=intent.getExtras();
        getFilepath=bundle.getString("filepath");
        filePath.setText(getFilepath);
    }

    //按钮点击事件
    public void addOnclick(View v){
        switch (v.getId()){
            case R.id.selectFile: //文件按钮

                flag=true;
                getGrant(Manifest.permission.READ_EXTERNAL_STORAGE);

                break;
            case R.id.toAddData:  //确定添加数据

                if (getFilepath!=null){
                    Toast.makeText(Add_Activity.this,"请等待》》》》",Toast.LENGTH_SHORT).show();
                    flag=false;
                    getGrant(Manifest.permission.READ_EXTERNAL_STORAGE);

                }else {
                    Toast.makeText(Add_Activity.this,"没有文件",Toast.LENGTH_SHORT).show();
                }


                break;

        }
    }

    @Override
    public void startRun() {
        super.startRun();
        for (Map<String,Object> map:getPermissionList()){
            if (map.get("permission")==Manifest.permission.READ_EXTERNAL_STORAGE){
                if (flag){

                    Intent intent=new Intent(Add_Activity.this, SelectFile.class);
                    startActivity(intent);

                }else {
                    getData();
                    Intent intent1=new Intent(Add_Activity.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                }

            }
        }
    }

    //读取文件，并存入数据库
    public void getData(){

        FileInputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader reader=null;
        String context="";

        try {

            inputStream=new FileInputStream(getFilepath);
            inputStreamReader=new InputStreamReader(inputStream,"gbk");
            reader=new BufferedReader(inputStreamReader);

            int line;
            char[] text=new char[1024];
            while ((line=reader.read(text))!=-1){
                String context_1=new String(text,0,line);
                context=context+context_1;
            }
            boolean subItem=true;
            while(subItem){

                try {
                    String thing = context.substring(0, context.indexOf("·"));
                    ContentValues values = new ContentValues();
                    values.put("name", thing);
                    myData.insert("thing", values);
                    context = context.substring(context.indexOf("·")+1);
                }catch (Exception e){
                    subItem=false;
                }
            }

            Toast.makeText(Add_Activity.this,"添加成功",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader!=null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }
}
