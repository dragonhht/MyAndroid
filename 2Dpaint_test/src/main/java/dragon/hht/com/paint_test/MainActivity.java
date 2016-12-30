package dragon.hht.com.paint_test;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import dragon.hht.com.view.MyView;
/*
 * 绘制android机器人
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //获取布局文件中的帧布局管理器
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.frame_layout);
        //将自定义视图添加到帧布局管理器中
        frameLayout.addView(new MyView(this));

    }
}
