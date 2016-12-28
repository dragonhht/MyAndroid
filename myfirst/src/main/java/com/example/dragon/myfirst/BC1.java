package com.example.dragon.myfirst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by huang on 16-12-28.
 */

public class BC1 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("这里","已收到广播");
        String s=intent.getStringExtra("msg");
        Log.i("这里","BC 一个搜到"+s);
    }
}
