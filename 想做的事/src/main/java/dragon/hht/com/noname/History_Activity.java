package dragon.hht.com.noname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dragon.hht.com.noname.DataBase.MyData;
import dragon.hht.com.noname.Utils.SelectFile;

/**
 * Created by 游戏2 on 2017/1/14.
 */

public class History_Activity extends Activity {
    private ListView historyList;
    //当前事件Id
    private String thingId;
    private MyData myData;
    //该事件所有记录
    private List<Map<String,String>> historyValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myData=new MyData(History_Activity.this);

        SharedPreferences preferences=getSharedPreferences("NowThing",MODE_PRIVATE);
        thingId=preferences.getString("_id","");

        historyList= (ListView) findViewById(R.id.history_listview);
        historyList.setAdapter(new MyAdapter(thingId));

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String commentId=historyValues.get(position).get("_id");
                Intent intent=new Intent(History_Activity.this,Record_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("commentid",commentId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



    public class MyAdapter extends BaseAdapter{



        public MyAdapter(String id){
            historyValues=myData.getValues("evaluate",null,"thing_id=?",new String[]{id});
        }

        @Override
        public int getCount() {
            return historyValues.size();
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
                view=View.inflate(History_Activity.this,R.layout.historylist,null);
                ((TextView)view.findViewById(R.id.history_date)).setText(historyValues.get(position).get("date"));
                ((TextView)view.findViewById(R.id.history_grade)).setText(historyValues.get(position).get("grade"));
            }else {
                view=convertView;
                ((TextView)view.findViewById(R.id.history_date)).setText(historyValues.get(position).get("date"));
                ((TextView)view.findViewById(R.id.history_grade)).setText(historyValues.get(position).get("grade"));
            }
            return view;
        }
    }
}
