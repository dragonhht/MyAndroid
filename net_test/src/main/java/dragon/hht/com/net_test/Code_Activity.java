package dragon.hht.com.net_test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by 游戏2 on 2017/1/15.
 *
 * 代码查看器
 */

public class Code_Activity extends Activity {

    private TextView codeText;
    private EditText editText;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x001){
                codeText.setText(msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        codeText= (TextView) findViewById(R.id.code_text);
        editText=(EditText)findViewById(R.id.editText);

    }

    //按钮点击事件
    public void codeClick(View v){
        switch (v.getId()){
            case R.id.code_lookbtn:
                //获取url
                final String urlpath=editText.getText().toString().trim();
                //网络请求在子线程中进行
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            //创建URL对象
                            URL url=new URL(urlpath);
                            //获取HttpURLConnection
                            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                            //设置请求的方法，默认为GET,方法大写
                            connection.setRequestMethod("GET");
                            //设置连接超时时间
                            connection.setConnectTimeout(30*1000);
                            //获取响应码
                            int responseode=connection.getResponseCode();
                            Log.i("info","Code"+responseode);
                            if (responseode==200){
                                //请求成功
                                //获取输入流
                                InputStream inputStream=connection.getInputStream();
                                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                int len=-1;
                                byte[] bytes=new byte[1024];
                                while ((len=inputStream.read(bytes))!=-1){
                                    byteArrayOutputStream.write(bytes,0,len);
                                }
                                byte[] code=byteArrayOutputStream.toByteArray();
                                inputStream.close();
                                byteArrayOutputStream.close();
                                String condeString=new String(code);
                                Message message=new Message();
                                message.obj=condeString;
                                message.what=0x001;
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


                break;
        }
    }

}
