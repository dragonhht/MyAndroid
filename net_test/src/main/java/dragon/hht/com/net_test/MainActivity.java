package dragon.hht.com.net_test;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doClick(View v){
        switch (v.getId()){
            case R.id.btu1:

                Intent intent=new Intent(MainActivity.this,Code_Activity.class);
                startActivity(intent);
                break;
            case R.id.btu2:
                Intent intent1=new Intent(MainActivity.this,Image_Activity.class);
                startActivity(intent1);
                break;
        }
    }


}
