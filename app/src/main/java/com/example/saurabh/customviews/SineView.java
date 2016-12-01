package com.example.saurabh.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by saurabh on 20/11/16.
 */

public class SineView extends View {
    public static final String TAG= SineView.class.getSimpleName();
    private float mPitch =1;
    private Paint mLinePaint;
    private float top;
    private float bottom;
    private float hh;
    private float ww;
    private float mid;
    private int step ;
    private int mWaveColor;
    private  int mWaveWidth;
    Path path;


    private void init(){
        mLinePaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mWaveColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mWaveWidth);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        path= new Path();
        step= (int) (50*mPitch);
        if (mPitch==0)
            throw new IllegalArgumentException("Pitch cannot be zero!");
    }


    public SineView(Context context) {
        super(context);
    }

    public SineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SineView, 0, 0);
        try {
            mPitch = array.getFloat(R.styleable.SineView_pitch, 1);
            mWaveColor=array.getColor(R.styleable.SineView_waveColor,Color.BLUE);
            mWaveWidth=array.getDimensionPixelSize(R.styleable.SineView_waveWidth,1);
        } finally {
            array.recycle();
        }
        init();
    }

    public SineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xpad= getPaddingLeft()+getPaddingRight();
        float ypad= getPaddingTop()+getPaddingBottom();
        hh= h-ypad;
        ww=w-xpad;
        mid=h/2;
        top= h-getPaddingTop();
        bottom= getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculatePath2();
        canvas.drawPath(path,mLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(), ""+event.getY(), Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

    private void calculatePath() {
        path.reset();
        float ix=0;
        float iy=getCurrentY(0);
        path.moveTo(ix,iy);
        StringBuilder builder= new StringBuilder();
        builder.append("x=").append(0).append(",y=").append(getCurrentY(0)).append('\n');
        for (int i=1;i<ww;i+=20){
            path.quadTo(ix,iy,i,getCurrentY(i));
            ix=i;
            iy=getCurrentY(i);
            builder.append("x=").append(i).append("y=").append(getCurrentY(i)).append('\n');
        }
        Log.d(TAG, "calcuatePath: "+builder.toString());
    }
    private void calculatePath2(){
        path.reset();
        int x=getPaddingLeft();
        path.moveTo(x,mid);
        boolean upper=true;
        while (x<ww){
            path.quadTo(x+=step,upper?top:bottom,x+=step,mid);
            upper=!upper;
        }
    }

    private float getCurrentY(int x){
        return (float) (Math.sin(x)*mid+mid);
    }

    public float getPitch() {
        return mPitch;
    }

    public void setPitch(float pitch) {
        this.mPitch = pitch;
        mPitch= mPitch==0? (float) 0.1 :mPitch;
        step= (int) (50*mPitch);
        invalidate();
        requestLayout();
    }

    public int getmWaveColor() {
        return mWaveColor;
    }

    public void setWaveColor(int waveColor) {
        this.mWaveColor = waveColor;
        invalidate();
    }

    public void setWaveWidth(int WaveWidth) {
        this.mWaveWidth = WaveWidth;
        mLinePaint.setStrokeWidth(mWaveWidth);
        invalidate();
    }

    public int getmWaveWidth() {
        return mWaveWidth;
    }
}
