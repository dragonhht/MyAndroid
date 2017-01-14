package dragon.hht.com.noname;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import dragon.hht.com.noname.DataBase.MyData;
import dragon.hht.com.noname.Utils.BaseActivity;

public class MainActivity extends BaseActivity {

    MyData myData;
    TextView textView;
    private SharedPreferences.Editor editor;
    private int minId=-1,maxId=-1;
    private Random random=new Random();
    //退出判定
    private boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);

        myData=new MyData(MainActivity.this);
        minId=myData.getMinId("thing");
        Log.i("info","minId:"+minId);
        maxId=myData.getMaxId("thing");
        Log.i("info","maxId:"+maxId);


        //用于记录当前未完成的事件
        SharedPreferences preferences=getSharedPreferences("NowThing",MODE_PRIVATE);
        editor=preferences.edit();
        if (minId==-1&&maxId==-1){
            editor.putBoolean("flag",true);
            editor.putString("name", "你还没有添加事件数据，请点击右上角导入数据");
            editor.putString("_id", "");
            editor.commit();
        }else {
            boolean ok=preferences.getBoolean("flag",true);
            if (ok) {
                String id = String.valueOf(random.nextInt(maxId - minId) + minId);
                List<Map<String, String>> thing = myData.getValues("thing", null, "_id=?", new String[]{id});
                editor.putString("name", thing.get(0).get("name"));
                editor.putString("_id", thing.get(0).get("_id"));
                editor.putBoolean("flag", false);
                editor.commit();
            }
        }

        String thingName=preferences.getString("name","你还没有添加事件数据，请点击右上角导入数据");
        String thingId=preferences.getString("_id","");
        textView.setText(thingName);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        minId=myData.getMinId("thing");
        Log.i("info","minId:"+minId);
        maxId=myData.getMaxId("thing");
        Log.i("info","maxId:"+maxId);
        SharedPreferences preferences=getSharedPreferences("NowThing",MODE_PRIVATE);
        editor=preferences.edit();
        if (minId==-1&&maxId==-1){
            editor.putBoolean("flag",true);
            editor.putString("name", "你还没有添加事件数据，请点击右上角导入数据");
            editor.putString("_id", "");
            editor.commit();
        }else {
            boolean ok=preferences.getBoolean("flag",true);
            if (ok) {
                String id = String.valueOf(random.nextInt(maxId - minId) + minId);
                List<Map<String, String>> thing = myData.getValues("thing", null, "_id=?", new String[]{id});
                editor.putString("name", thing.get(0).get("name"));
                editor.putString("_id", thing.get(0).get("_id"));
                editor.putBoolean("flag", false);
                editor.commit();
            }
        }

        String thingName=preferences.getString("name","你还没有添加事件数据，请点击右上角导入数据");
        String thingId=preferences.getString("_id","");
        textView.setText(thingName);
    }

    //创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //实例化MenuInflater
        MenuInflater inflater=new MenuInflater(MainActivity.this);
        //解析菜单文件
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:  //导入数据

                //跳转至添加数据界面
                Intent intent=new Intent(MainActivity.this,Add_Activity.class);
                startActivity(intent);

                break;
            case R.id.item2:  //清除所有数据

                if (toEmpty()){
                    Toast.makeText(MainActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(MainActivity.this,"清除失败",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.item3:  //查看以往记录

                Intent intent1=new Intent(MainActivity.this,History_Activity.class);

                startActivity(intent1);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //清空数据
    public boolean toEmpty(){
        boolean ok=false;

        /*myData.delete("thing",null,null);
        myData.delete("evaluate",null,null);*/
        myData.delTable("thing");
        myData.delTable("evaluate");
        try {
            myData.getValues("evaluate",null,null,null);
        }catch (Exception e){
            myData.createTeble();
            ok=true;
        }

        return ok;
    }

    //按钮点击事件
    public void toselfBtn(View view){
        switch (view.getId()){
            case R.id.selfText:

                Intent intent=new Intent(MainActivity.this,Self_Activity.class);
                startActivity(intent);

                break;
        }
    }

    //按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){

            exitApp();
        }

        return false;

    }

    //双击返回键退出程序
    public void exitApp(){
        Timer exitTimer=null;
        if (!exit){
            exit=true;
            Toast.makeText(MainActivity.this,"再按一次返回键退出应用",Toast.LENGTH_SHORT).show();
            exitTimer=new Timer();
            exitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    exit=false;//如果2秒内没有第二次点击返回则取消第一次点击
                }
            },2000);
        }else {
            finish();
            System.exit(0);
        }
    }

}
