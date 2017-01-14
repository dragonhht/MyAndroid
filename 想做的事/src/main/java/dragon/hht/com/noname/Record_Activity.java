package dragon.hht.com.noname;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dragon.hht.com.noname.DataBase.MyData;

/**
 * Created by 游戏2 on 2017/1/14.
 */

public class Record_Activity extends Activity {

    private TextView comment_text,record_date,record_grade;
    MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        comment_text= (TextView) findViewById(R.id.record_comment);
        record_date= (TextView) findViewById(R.id.record_date);
        record_grade= (TextView) findViewById(R.id.record_grade);

        myData=new MyData(Record_Activity.this);
        Bundle bundle=getIntent().getExtras();
        String commentId=bundle.getString("commentid");
        List<Map<String,String>> value=myData.getValues("evaluate",null,"_id=?",new String[]{commentId});
        comment_text.setText(value.get(0).get("comment"));
        record_date.setText(value.get(0).get("date"));
        record_grade.setText(value.get(0).get("grade"));
    }
}
