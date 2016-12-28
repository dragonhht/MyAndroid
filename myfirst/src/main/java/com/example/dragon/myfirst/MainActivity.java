package com.example.dragon.myfirst;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean bound=false;
    MyService myService;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder=(MyService.MyBinder) service;
            myService=binder.getService();  //获取Service实例
            bound=true;  //标记已经绑定
        }

        //当启动源与Service意外丢失时调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

            bound=false;  //标记未绑定
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(MainActivity.this,MyService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);  //绑定Service
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound){
            unbindService(connection);  //取消绑定的Service
            bound=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //点击方法
    public void doClick(View v){
        Log.i("这里","调用了点击方法");

        Intent intentT=new Intent(MainActivity.this,MyService.class);

        switch (v.getId()){

            case R.id.sendbtn1:  //发送一条普通广播

                Log.i("这里","正在放松一条普通广播");
                Intent intent=new Intent();
                intent.putExtra("msg","我发送了一条普通广播");
                intent.setAction("BC_One");
                sendBroadcast(intent);

                break;

            case R.id.startsercive:
                Log.i("这里","启动服务");
                startService(intentT);

                break;
            case R.id.stopservice:
                Log.i("这里","关闭服务");
                stopService(intentT);

                break;
            case R.id.getnum:

                Log.i("这里","碎鸡蛋糊");
                if (bound){
                    int num=myService.getRandomNumber();
                    Log.i("这里","获得随机数："+num);
                }

                break;

            default:
                break;
        }
    }
}
