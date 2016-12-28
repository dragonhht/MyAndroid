package com.example.dragon.myfirst;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by huang on 16-12-28.
 */

public class MyService extends Service{

    private final IBinder binder=new MyBinder();   //创建一个IBinder对象
    private final Random generator=new Random();   //声明并实例化一个Radom对象

    //编写一个继承IBinder的内部类
    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }

    }



    //必须实现的方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("这里","onBind方法被调用");

        return binder;
    }

    //Service被创建时回调
    @Override
    public void onCreate() {
        Log.i("这里","onCreate方法被调用");
        super.onCreate();

    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        Log.i("这里","onDestroy被调用");
        super.onDestroy();
    }

    //Service被启动时回调
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("这里","onStartCommand被调用");
        return super.onStartCommand(intent, flags, startId);
    }

    //获取随机数的方法
    public int getRandomNumber(){
        return generator.nextInt(100);
    }
}
