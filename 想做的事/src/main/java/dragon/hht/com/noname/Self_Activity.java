package dragon.hht.com.noname;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import dragon.hht.com.noname.DataBase.MyData;

/**
 * Created by 游戏2 on 2017/1/14.
 */

public class Self_Activity extends Activity implements RadioGroup.OnCheckedChangeListener{

    private RadioButton radioButton;
    private RadioGroup radioRroup0,radioRroup1;
    private boolean groupChang=true;
    private EditText editText;
    private SharedPreferences.Editor editor;
    private String thingId;
    private MyData myData;
    private boolean ok=false;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        radioButton= (RadioButton) findViewById(R.id.radioButton0);
        radioRroup0= (RadioGroup) findViewById(R.id.radioRroup0);
        radioRroup1= (RadioGroup) findViewById(R.id.radioRroup1);
        radioRroup0.setOnCheckedChangeListener(this);
        radioRroup1.setOnCheckedChangeListener(this);
        editText= (EditText) findViewById(R.id.editText);
        myData=new MyData(Self_Activity.this);
        //设置多行文本显示
        editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        //改变默认的单行模式
        editText.setSingleLine(false);
        editText.setVerticalScrollBarEnabled(true);

        preferences=getSharedPreferences("NowThing",MODE_PRIVATE);
        editor=preferences.edit();
        thingId=preferences.getString("_id","");

    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.setText("");

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (groupChang) {
            radioButton= (RadioButton) findViewById(checkedId);
            groupChang=false;
            switch (group.getId()) {
                case R.id.radioRroup0:
                    radioRroup1.clearCheck();
                    break;
                case R.id.radioRroup1:
                    radioRroup0.clearCheck();
                    break;
            }
            groupChang=true;

        }

        return;
    }

    //提交
    public void commitClick(View v){
        if (v.getId()==R.id.commitBtn){
            ok=preferences.getBoolean("flag",false);
            if (!ok) {
                String grade = String.valueOf(radioButton.getText());
                String comment = String.valueOf(editText.getText());
                ContentValues values = new ContentValues();
                values.put("thing_id", thingId);
                values.put("grade", grade);
                values.put("comment", comment);
                values.put("date", getDate(new Date()));
                myData.insert("evaluate",values);
                editor.putBoolean("flag",true);
                editor.commit();
                Toast.makeText(Self_Activity.this,"评价完成",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Self_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(Self_Activity.this,"当前没有事情",Toast.LENGTH_LONG).show();
            }
        }

    }

    //日期格式转换
    public String getDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
