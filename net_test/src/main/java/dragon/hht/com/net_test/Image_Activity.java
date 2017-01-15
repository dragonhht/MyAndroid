package dragon.hht.com.net_test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 游戏2 on 2017/1/15.
 *
 * 图片查看器
 */

public class Image_Activity extends Activity {

    EditText editText2;
    Button imget_btn;
    ImageView imageView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x001){
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView= (ImageView) findViewById(R.id.imageView);
        editText2= (EditText) findViewById(R.id.editText2);
        imget_btn= (Button) findViewById(R.id.imget_btn);

        imget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取url
                final String urlpath=editText2.getText().toString().trim();
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
                                //通过流创建Bitmap对象
                                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);


                                Message message=new Message();
                                message.obj=bitmap;
                                message.what=0x001;
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

    }
}
