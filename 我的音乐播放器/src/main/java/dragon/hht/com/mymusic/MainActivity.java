package dragon.hht.com.mymusic;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import dragon.hht.com.mymusic.Utils.BaseActivity;
import dragon.hht.com.mymusic.service.PlayService;

import static dragon.hht.com.mymusic.R.styleable.View;

public class MainActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener{


    private static final int UPDATE_PROGRESS =0x001;
    private static final int UPDATE_COUNTTIME =0x002;
    private static final int UPDATE_SONFNAME =0x003;
    private PlayService playService;
    private boolean bound=true;
    private ImageButton playOrPauseBtn,modeBtn;
    private SeekBar seekBar;
    private TextView songNmae,artistBtn,name;
    private ListView songList;
    private int position=0,mode=0;
    private String grade;

    private Handler  handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
                case UPDATE_COUNTTIME:
                    updateCountTime();
                    break;
                case UPDATE_SONFNAME:
                    updateSongName();
                    break;
                case 7:
                    initUI();
                    songList.setAdapter(new MyAdapter());
                    //选择音频
                    songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            playService.setNowMusic(playService.getAudios().get(position).get("path"));
                            playService.getMediaPlayer().pause();
                            playService.play();
                            playService.setNum(position);
                            updateBtn();
                            updateCountTime();
                            updateProgress();
                            updateSongName();
                        }
                    });
                    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+playService);
                    break;
            }

        }
    };


    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.MyBinder binder= (PlayService.MyBinder) service;
            playService=binder.getService();


            Log.i("info","onServiceConnected");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+playService);

//        isGranted();
        getGrant(Manifest.permission.READ_EXTERNAL_STORAGE);

    }

    @Override
    public void startRun() {
        super.startRun();
        System.out.println("Permission"+getPermissionList().size());
        for (Map<String,Object> map:getPermissionList()){
            if (map.get("permission")==Manifest.permission.READ_EXTERNAL_STORAGE){
                Intent intent=new Intent(MainActivity.this,PlayService.class);
                bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

                handler.sendEmptyMessageDelayed(7,500);
                break;
            }
        }


    }

    //初始化UI
    public void initUI(){
        playOrPauseBtn= (ImageButton) findViewById(R.id.playOrPause);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        songNmae= (TextView) findViewById(R.id.songName);
        artistBtn= (TextView) findViewById(R.id.arists);
        modeBtn= (ImageButton) findViewById(R.id.modeBtn);
        seekBar.setOnSeekBarChangeListener(this);
        songList= (ListView) findViewById(R.id.songs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("info","OnStart");
    }

    //点击事件
    public void doClick(android.view.View v){

        switch (v.getId()){
            case R.id.playOrPause:

                if (playService.getListSize()>0) {
                    playService.play();
                    updateBtn();
                    updateCountTime();
                    updateProgress();
                    updateSongName();
                }else{
                    Toast.makeText(MainActivity.this,"暂无音频文件可播放",Toast.LENGTH_SHORT);
                }

                break;
            case R.id.proviewbtn:
                if (playService.getListSize()>0) {
                playService.proview();
                updateBtn();
                updateCountTime();
                updateProgress();
                updateSongName();
                }else{
                    Toast.makeText(MainActivity.this,"暂无音频文件可播放",Toast.LENGTH_SHORT);
                }

                break;
            case R.id.nextbtn:
                if (playService.getListSize()>0) {
                playService.next();
                updateBtn();
                updateCountTime();
                updateProgress();
                updateSongName();
        }else{
            Toast.makeText(MainActivity.this,"暂无音频文件可播放",Toast.LENGTH_SHORT);
        }

                break;
            case R.id.modeBtn:
                if (mode==0){
                    modeBtn.setImageResource(R.mipmap.mode);
                    playService.setMode(1);
                    mode=1;
                }else {
                    modeBtn.setImageResource(R.mipmap.nextmode);
                    playService.setMode(0);
                    mode=0;
                }
                break;
            default:
                break;
        }

    }

    //更新播放暂停按钮
    public void updateBtn(){
        if (playService.isPlaying()){
            playOrPauseBtn.setImageResource(R.mipmap.pause);

        }else {
            playOrPauseBtn.setImageResource(R.mipmap.play);
        }
    }

    //更新进度
    public void updateProgress(){
        seekBar.setProgress(playService.getCurrentPosition());
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,500);
    }
    //更新总时长
    public void updateCountTime(){
        seekBar.setMax(playService.getSongDuration());
        handler.sendEmptyMessageDelayed(UPDATE_COUNTTIME,500);
    }
    //更新音频名
    public void updateSongName(){
        songNmae.setText(playService.getSongName());
        artistBtn.setText(playService.getArtist());
        handler.sendEmptyMessageDelayed(UPDATE_SONFNAME,500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound){
            unbindService(serviceConnection);
            bound=false;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        position=progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        playService.setPosition(position);
        playService.getMediaPlayer().pause();
        playService.play();
        updateBtn();
    }

    //音频列表适配器
    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return playService.getAudios().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;

            if (convertView==null){
                //通过xml文件创建复杂的ListView
                view= android.view.View.inflate(MainActivity.this,R.layout.songtype,null);
                ((TextView)view.findViewById(R.id.name)).setText(playService.getAudios().get(position).get("title"));
                ((TextView)view.findViewById(R.id.art)).setText(playService.getAudios().get(position).get("artist"));
            }else {
                view=convertView;
                ((TextView)view.findViewById(R.id.name)).setText(playService.getAudios().get(position).get("title"));
                ((TextView)view.findViewById(R.id.art)).setText(playService.getAudios().get(position).get("artist"));
            }

            return view;
        }
    }

   /* //权限判断
    public void isGranted(){

        //检查是否拥有权限
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            Log.i("info","权限申请");
            //未授权时
            //申请权限
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);


        }else {
            Log.i("info","已有权限");
            startPlayService();
        }
    }



    //处理权限是否被授予
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("info","权限授予");
        switch (requestCode){
            case 0:

                //访问SD卡回调处理
                //判断第一个请求权限是否被受理
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startPlayService();
                }else{
                    //提示用户
                    Log.i("info","权限未授予");
                }

                break;
        }
    }*/


    /*//开启播放器服务
    public void startPlayService(){
        Intent intent=new Intent(MainActivity.this,PlayService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

        handler.sendEmptyMessageDelayed(7,500);
    }*/



}
