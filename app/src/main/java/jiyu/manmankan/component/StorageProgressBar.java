package jiyu.manmankan.component;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import jiyu.manmankan.R;

/**
 * Created by z on 2017/10/11.
 */

public class StorageProgressBar extends ProgressBar {
    String TAG="StorageProgressBar";
    private Paint paint=new Paint();
    protected int mFitColor;
    protected int mUnFitColor;
    private int defaultColor;
    //填充颜色占画布的比例
    private double progressBarScale=0.5;

    public StorageProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultColor= Color.WHITE;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.StorageProgressBar);
        mFitColor=typedArray.getColor(R.styleable.StorageProgressBar_fitBackground,defaultColor);
        mUnFitColor=typedArray.getColor(R.styleable.StorageProgressBar_unfitBackground, defaultColor);
        typedArray.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        canvas.drawColor(mUnFitColor);
        //画前景
        paint.setColor(mFitColor);
        canvas.drawRect(0,0, (float) (getWidth()*progressBarScale),getHeight(),paint);

    }

    public void setProgressBarScale(double progressBarScale) {
        this.progressBarScale = progressBarScale;
        Log.i(TAG, "setProgressBarScale: ==progressBarScale=="+progressBarScale);
        postInvalidate();
    }
}
