package dragon.hht.com.mina;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import dragon.hht.com.mina.DataBase.Mydata;
import dragon.hht.com.mina.Utils.BaseActivity;

/**
 * Created by 游戏2 on 2017/1/9.
 */

public class ExportData_Activity extends BaseActivity {

    private EditText fileText;
    Bundle bundle;
    String directoryPath;
    private boolean flag=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportdata);

        fileText= (EditText) findViewById(R.id.fileText);

        bundle=getIntent().getExtras();


    }

    //点击事件
    public void selectFile(View v){
        switch (v.getId()){
            case R.id.selectBtn:

                flag=false;
                getGrant(Manifest.permission.WRITE_EXTERNAL_STORAGE);


                break;
            case R.id.export:
                flag=true;
                getGrant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void startRun() {
        super.startRun();
        for (Map<String,Object> map:getPermissionList()){
            if (map.get("permission")==Manifest.permission.WRITE_EXTERNAL_STORAGE){
                if (!flag) {
                    Intent intent_4 = new Intent(ExportData_Activity.this, SelectDirectory_Activity.class);
                    intent_4.putExtras(bundle);
                    startActivity(intent_4);
                    break;
                }else {
                    if (exportDate(directoryPath)){
                        Toast.makeText(ExportData_Activity.this,"导出成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ExportData_Activity.this,"导出失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        directoryPath=intent.getExtras().getString("path");
        Log.i("info","nowUser"+bundle.getInt("nowUser"));
        if (directoryPath!=null){
            fileText.setText(directoryPath);
        }
    }

    //导出数据
    public boolean exportDate(String directoryName){
        int userId=bundle.getInt("nowUser");
        Log.i("info","用户"+userId);
        Mydata mydata=new Mydata(ExportData_Activity.this);
        List<Map<String,String>> values=mydata.getValues("pwds",null,"user_id=?",new String[]{String.valueOf(userId)});

        Log.i("info","大小"+values.size());
        String context="<table>" +
                "<tr><td>别名</td><td>账号</td><td>密码</td></tr>";
        for (Map<String,String> map:values){
            Log.i("info","由数据");
            context=context+"<tr><td>"+map.get("othername")+"</td><td>"+map.get("name")+"</td><td>"+map.get("pwd")+"</td></tr>";
        }
        context=context+"</table>";
        FileOutputStream outputStream=null;


       // directoryName=directoryName.substring(1,directoryName.length());
        try {
//            outputStream=openFileOutput(directoryName+"/我的密码.html",MODE_NO_LOCALIZED_COLLATORS);
            outputStream=new FileOutputStream(new File(directoryName+"/我的密码.html"));
            OutputStreamWriter write = new OutputStreamWriter(outputStream,Charset.forName("gbk"));
            BufferedWriter writer=new BufferedWriter(write);
           /* outputStream.write(context.getBytes());
            outputStream.flush();*/
            writer.write(context);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
