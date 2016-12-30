package dragon.hht.com.paint_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 游戏2 on 2016/12/30.
 */

public class DrawView extends View{

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //重写onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一个带阴影的红色矩形
//        Paint paint=new Paint();  //定义一个画笔
//        paint.setColor(Color.RED);  //设置颜色
//        paint.setShadowLayer(2,3,3,Color.BLACK);  //设置阴影
//        canvas.drawRect(404,440,200,100,paint);   //绘制矩形





    }
}
