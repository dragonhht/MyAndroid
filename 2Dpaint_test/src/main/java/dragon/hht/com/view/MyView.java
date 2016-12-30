package dragon.hht.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 游戏2 on 2016/12/30.
 */

public class MyView extends View{
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setAntiAlias(true);  //使用抗锯齿功能
        paint.setColor(0xffa4c739);

        //绘制机器人的头
        RectF rectf_head=new RectF(10,10,100,100);
        rectf_head.offset(100,20);
        canvas.drawArc(rectf_head,-10,-160,false,paint);  //绘制弧
        //绘制眼睛
        paint.setColor(Color.WHITE);
        canvas.drawCircle(135,53,4,paint);  //绘制圆
        canvas.drawCircle(175,53,4,paint);
        paint.setColor(0xffa4c739);
        //绘制天线
        paint.setStrokeWidth(2);  //设置笔触的宽度
        canvas.drawLine(120,15,135,35,paint);  //绘制线
        canvas.drawLine(190,15,175,35,paint);
        //绘制身体
        canvas.drawRect(110,75,200,150,paint);  //绘制矩形
        RectF rectf_body=new RectF(110,140,200,160);
        canvas.drawRoundRect(rectf_body,10,10,paint);  //绘制圆角矩形
        //绘制胳膊
        RectF rectf_arm=new RectF(85,75,105,140);
        canvas.drawRoundRect(rectf_arm,10,10,paint);
        rectf_arm.offset(120,0);   //设置在x轴上偏移120像素
        canvas.drawRoundRect(rectf_arm,10,10,paint);
        //绘制腿
        RectF rectf_leg=new RectF(125,150,145,200);
        canvas.drawRoundRect(rectf_leg,10,10,paint);
        rectf_leg.offset(40,0);
        canvas.drawRoundRect(rectf_leg,10,10,paint);

    }
}
