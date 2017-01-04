package dragon.hht.com.mymusic.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 游戏2 on 2017/1/2.
 */

public class PlayService extends Service implements MediaPlayer.OnCompletionListener{

    private final IBinder myBinder=new MyBinder();
    private MediaPlayer mediaPlayer;
    //媒体暂停时间点
    private int position=0;
    private Random random=new Random();
    private Cursor cursor;
    //当前播放文件的路径
    private String nowMusic;
    //当前播放序列
    private int num=0,mode=0;
    //保存音频路径的集合
    List<Map<String,String>> audios=new ArrayList<Map<String,String>>();



    public class MyBinder extends Binder{

        public PlayService getService(){
            Log.i("info","MyBinder");
            return PlayService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info","onCreate");

        mediaPlayer=new MediaPlayer();
        ContentResolver cp=this.getContentResolver();
        //查询手机所有音频
        cursor=cp.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Log.i("info","查询完毕");
        if (cursor!=null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                Log.i("info","显示");
                HashMap map=new HashMap<String,String>();
                //音频名
                map.put("title",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                //音频路径
                map.put("path",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                //音频艺术家
                map.put("artist",cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                Log.i("info","TITLE:"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                Log.i("info","Data:"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                Log.i("info","ARTIST:"+cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                Log.i("info","_______________________________________________");
                //将查询结果放入集合
                audios.add(map);
            }
        }

        mediaPlayer.setOnCompletionListener(this);

    }


    //播放和暂停
    public void play()  {
        Log.i("info","play被调用");
        if (nowMusic!=null){
            //判断是否正在播放
            if (mediaPlayer.isPlaying()){
                position=mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }else {
                //将MediaPlay重置为Idle状态
                mediaPlayer.reset();
                try {

                    mediaPlayer.setDataSource(nowMusic);
                    //将MediaPlay置位prepared状态
                    mediaPlayer.prepare();
                    //播放时间置成暂停点
                    mediaPlayer.seekTo(position);
                    //开始播放
                    mediaPlayer.start();
                    position=0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else {
            setPlayMode();
            nowMusic=audios.get(num).get("path");
            play();
        }

    }

    //上一首
    public void proview(){

        if (mode==0){
            //顺序模式
            if ((num-1)<0){
                num=audios.size()-1;
            }else {
                num--;
            }
        }else {
            //随机模式
            num=random.nextInt(audios.size()-1);
        }

        nowMusic=audios.get(num).get("path");
        mediaPlayer.pause();
        position=0;
        play();
    }

    //下一首
    public void next(){
        setPlayMode();
        nowMusic=audios.get(num).get("path");
        mediaPlayer.pause();
        position=0;
        play();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
        if (cursor!=null){
            cursor.close();
        }
        Log.i("info","onDestroy");
    }

    //切换播放模式
    public void setPlayMode(){

        if (mode==0){
            //顺序模式
            if ((num+1)>audios.size()-1){
                num=0;
            }else {
                num++;
            }
        }else {
            //随机模式
            num=random.nextInt(audios.size()-1);
        }
    }

    //获取当前播放的音频名称
    public String getSongName(){
        return audios.get(num).get("title");
    }
    //获取音频艺术家
    public String getArtist(){
        return audios.get(num).get("artist");
    }
    //获取当前音频的进度
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    //获取当前音频的总长度
    public int getSongDuration(){
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<Map<String, String>> getAudios() {
        return audios;
    }

    //获取歌单长度
    public int getListSize(){
        return audios.size();
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNowMusic(String nowMusic) {
        this.nowMusic = nowMusic;
    }

    //监听音频是否播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
        Log.i("info","播放完成");
    }


}
