package dragon.hht.com.mina;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dragon.hht.com.mina.Utils.BaseActivity;
import dragon.hht.com.mina.Utils.DirectoryChooser;

/**
 * Created by 游戏2 on 2017/1/9.
 *
 * 选择目录
 */

public class SelectDirectory_Activity extends BaseActivity {

    ListView SelectList;
    private String path="",pathRoot="";
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_directory);

        SelectList= (ListView) findViewById(R.id.directoryList);
        bundle=getIntent().getExtras();

        path="/";
        path= String.valueOf(Environment.getExternalStorageDirectory());
        pathRoot=String.valueOf(Environment.getExternalStorageDirectory());
        SelectList.setAdapter(new SelectAdapter(path));
        SelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=((TextView)view).getText().toString();
                path=path+"/"+name;
                SelectList.setAdapter(new SelectAdapter(path));
            }
        });


    }

    //点击事件
    public void selectClick(View v){
        switch (v.getId()){
            case R.id.select_back:  //上一级目录
                if (!path.equals(pathRoot)) {
                    path = path.substring(0, path.lastIndexOf("/"));
                    /*if (path.equals("")||path==null){
                        path="/";
                    }*/
                    Log.i("info", "path" + path);
                    SelectList.setAdapter(new SelectAdapter(path));
                }
                break;
            case R.id.select_this:  //选择这个目录

                Log.i("info","选择的目录:"+path);
                Intent intent=new Intent(SelectDirectory_Activity.this,ExportData_Activity.class);
                bundle.putString("path",path);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
    }

    public class SelectAdapter extends BaseAdapter{

        List<Map<String,String>> directoryList;


        public SelectAdapter(String fileName){
            DirectoryChooser chooser=new DirectoryChooser();
            directoryList=chooser.getDirectory(fileName);

        }

        @Override
        public int getCount() {
            return directoryList.size();
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

                textView=new TextView(SelectDirectory_Activity.this);


            }else {

                textView= (TextView) convertView;

            }

            textView.setText(directoryList.get(position).get("name"));
            textView.setTextSize(30l);
            return textView;
        }
    }

}
