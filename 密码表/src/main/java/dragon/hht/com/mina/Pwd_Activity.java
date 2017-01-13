package dragon.hht.com.mina;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dragon.hht.com.mina.DataBase.Mydata;
import dragon.hht.com.mina.Utils.BaseActivity;


/**
 * Created by 游戏2 on 2017/1/5.
 */

public class Pwd_Activity extends BaseActivity {

    private ListView colorList,otherList;
    private TextView orthername,account,pwd_xiangxi;
    private int nowUser=0;
    Bundle bundle;
    //退出判定
    private boolean exit=false;
    Mydata mydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);

        colorList= (ListView) findViewById(R.id.colorlist);
        otherList= (ListView) findViewById(R.id.otherlist);
        orthername= (TextView) findViewById(R.id.orthername);
        account= (TextView) findViewById(R.id.account);
        pwd_xiangxi= (TextView) findViewById(R.id.pwd_xiangxi);

        mydata=new Mydata(Pwd_Activity.this);

        bundle=getIntent().getExtras();
        nowUser=bundle.getInt("nowUser");

        otherList.setAdapter(new otherAdapter());


        //别名点击事件
        otherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //单机事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String viewText= String.valueOf(((TextView)view).getText());
//                Cursor cursor=db.rawQuery("select * from pwds where othername=? and user_id=?",new String[]{viewText, String.valueOf(nowUser)});
                List<Map<String,String>> values=mydata.getValues("pwds",null,"othername=? and user_id=?",new String[]{viewText, String.valueOf(nowUser)});
                if (values!=null){
                    orthername.setText(values.get(0).get("othername"));
                    account.setText(values.get(0).get("name"));
                    pwd_xiangxi.setText(values.get(0).get("pwd"));
                }

            }
        });

        otherList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               final String  textView= String.valueOf(((TextView)view).getText());

               //AlertDialog对话框
                final AlertDialog delDialog=new AlertDialog.Builder(Pwd_Activity.this).create();
                delDialog.setTitle("提示");
                delDialog.setMessage("是否删除别名为："+textView+" 的密码");
                //添加确定按钮
                delDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //删除相应的数据
                        mydata.delete("pwds","othername=?",new String[]{textView});
                        otherList.setAdapter(new otherAdapter());
                    }
                });
                //添加取消按钮
                delDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info","选择了取消");
                        delDialog.hide();
                    }
                });

                delDialog.show();

                return true;
            }
        });

    }

    //创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //实例化一个对象
        MenuInflater inflater=new MenuInflater(Pwd_Activity.this);
        //解析菜单文件
        inflater.inflate(R.menu.main,menu);

        return true;
    }

    //响应菜单项的选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:

                Intent intent=new Intent(Pwd_Activity.this,Add_Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.item2:
               Intent intent2=new Intent(Pwd_Activity.this,ExportData_Activity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
            case R.id.item3:

                Intent intent1=new Intent(Pwd_Activity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(Pwd_Activity.this,"再按一次返回键退出应用",Toast.LENGTH_SHORT).show();
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

    //颜色Adapter
    public class ColorAdapter extends BaseAdapter{

        public ColorAdapter(){

        }

        @Override
        public int getCount() {



            return 0;
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



            return null;
        }
    }

    //别名
    public class otherAdapter extends BaseAdapter{
        List<Map<String,String>> valueList;


        public otherAdapter(){
            Log.i("info","Adapter________________________"+nowUser);
//            Cursor cursor=db.rawQuery("select * from pwds",null);
//            Cursor cursor=db.rawQuery("select * from pwds where user_id=?",new String[]{String.valueOf(nowUser)});
            valueList=mydata.getValues("pwds",null,"user_id=?",new String[]{String.valueOf(nowUser)});


        }

        @Override
        public int getCount() {


            return valueList.size();
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

            TextView textViewiew=null;new TextView(Pwd_Activity.this);
            if (convertView==null){
                textViewiew=new TextView(Pwd_Activity.this);
            }else {
                textViewiew= (TextView) convertView;
            }
            textViewiew.setText(valueList.get(position).get("othername"));
            textViewiew.setTextSize(30l);
            return textViewiew;
        }
    }


}
