package dragon.hht.com.mina.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by 游戏2 on 2017/1/2.
 */

public class MainHorizontalScrollView extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mColor,mOther,mXiangxi;
    private  int mScreenWidth;

    private int mXiangxiPadding=32;

    private int mOtherWidth,mXiangxiWidth,mColorWidth=50;

    private boolean once=false;

    public MainHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取屏幕宽度
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;

        //将dp转换成像素
        mColorWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics());
        mXiangxiPadding=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,32,context.getResources().getDisplayMetrics());
    }

    //设置view的宽和高，设置自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once){
            mWapper= (LinearLayout) getChildAt(0);
            mColor= (ViewGroup) mWapper.getChildAt(0);
            mOther= (ViewGroup) mWapper.getChildAt(1);
            mXiangxi=(ViewGroup)mWapper.getChildAt(2);

            mColor.getLayoutParams().width=mColorWidth;
            //mOtherWidth
            mOtherWidth=mOther.getLayoutParams().width=mScreenWidth-mColorWidth;
            //mXiangxiWidth
            mXiangxiWidth=mXiangxi.getLayoutParams().width=mScreenWidth-mXiangxiPadding;
            once=true;
        }
    }

    //通过设置偏移量将menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed){
            this.smoothScrollTo(0,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:   //抬起手指
                //隐藏的宽度
                int scrollX=getScrollX();
                if (scrollX>=mScreenWidth/2){
                    this.smoothScrollTo(mScreenWidth,0);
                }else{
                    this.smoothScrollTo(0,0);
                }

                return true;
        }

        return super.onTouchEvent(ev);
    }
}
