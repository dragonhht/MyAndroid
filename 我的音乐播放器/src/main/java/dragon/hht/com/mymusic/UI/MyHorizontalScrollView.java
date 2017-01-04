package dragon.hht.com.mymusic.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by 游戏2 on 2017/1/2.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mSongList,mContent;
    private  int mScreenWidth;

    private int mmSongListLeftPadding=50,mContentPadding=32;

    private int mmSongListWidth,mContentWidth;

    private boolean once=false;

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取屏幕宽度
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;

        //将dp转换成像素
        mmSongListLeftPadding=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics());
        mContentPadding=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,32,context.getResources().getDisplayMetrics());
    }

    //设置view的宽和高，设置自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once){
            mWapper= (LinearLayout) getChildAt(0);
            mContent= (ViewGroup) mWapper.getChildAt(0);
            mSongList= (ViewGroup) mWapper.getChildAt(1);
            //mmSongListWidth
            mmSongListWidth=mSongList.getLayoutParams().width=mScreenWidth-mmSongListLeftPadding;
            //设置content的宽度
            mContentPadding=mContent.getLayoutParams().width=mScreenWidth-mContentPadding;
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
                    this.smoothScrollTo(mContentPadding,0);
                }else{
                    this.smoothScrollTo(0,0);
                }

                return true;
        }

        return super.onTouchEvent(ev);
    }
}
