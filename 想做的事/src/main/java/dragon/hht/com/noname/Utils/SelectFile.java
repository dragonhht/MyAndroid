package dragon.hht.com.noname.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dragon.hht.com.noname.Add_Activity;
import dragon.hht.com.noname.R;

/**
 * Created by 游戏2 on 2017/1/13.
 */

public class SelectFile extends Activity {

    ListView selectfileList;
    private String fileRoot="",filepath="";
    //选定的文件路径
    private String toFilePath="";
    //上一个文件
    private int proview=-1;
    //上一级文件名
    private String proviewName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfile);
        selectfileList= (ListView) findViewById(R.id.selectfileList);

        filepath= String.valueOf(Environment.getExternalStorageDirectory());
        fileRoot=String.valueOf(Environment.getExternalStorageDirectory());
        selectfileList.setAdapter(new MyAdapter(filepath));
        selectfileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击事件
                String name=((TextView)view).getText().toString();
                String houzhui=null;
                try {
                    houzhui = name.substring(name.lastIndexOf(".")+1, name.length());
                    if (houzhui.equals("txt")){

                        ((TextView)view).setBackgroundColor(Color.BLUE);
                        toFilePath=filepath+"/"+name;
                        proview=position;
                        proviewName= String.valueOf(((TextView)view).getText());
                    }else{
                        filepath=filepath+"/"+name;
                        selectfileList.setAdapter(new MyAdapter(filepath));
                    }
                }catch (Exception e){
                    filepath=filepath+"/"+name;
                    selectfileList.setAdapter(new MyAdapter(filepath));
                }

            }
        });

    }

    //点击事件
    public void selectOnclick(View v){
        switch (v.getId()){
            case R.id.select_back:  //上一级

                if (!filepath.equals(fileRoot)) {
                    filepath = filepath.substring(0, filepath.lastIndexOf("/"));
                    selectfileList.setAdapter(new MyAdapter(filepath));
                }

                break;
            case R.id.select_yes: //确定

                Intent intent=new Intent(SelectFile.this, Add_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("filepath",toFilePath);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
        }
    }


    public class MyAdapter extends BaseAdapter{

        List<Map<String,String>> files;

        public MyAdapter(String file){
            files=FileChooer.getFiles(file);
        }

        @Override
        public int getCount() {
            return files.size();
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

            TextView textView=null;
            if (convertView==null){
                textView = new TextView(SelectFile.this);
                textView.setBackgroundColor(Color.WHITE);
                if (files.get(position).get("flag").equals("1")) {
                    textView.setBackgroundColor(Color.RED);
                }
                textView.setTextSize(30);
                textView.setText(files.get(position).get("name"));
            }else {
                textView = (TextView) convertView;
                textView.setBackgroundColor(Color.WHITE);
                if (files.get(position).get("flag").equals("1")) {
                    textView.setBackgroundColor(Color.RED);
                }
                textView.setTextSize(30);
                textView.setText(files.get(position).get("name"));
            }

            return textView;
        }
    }

}
