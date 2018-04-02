package com.example.saurabh.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.PathDashPathEffect.Style.TRANSLATE;

public class Polygons extends View {

    PathMeasure pathMeasure = new PathMeasure();
    private Paint mCirclePaint;
    private Paint mDotPaint;
    private float cx;
    private float cy;
    private Path circlePath;
    private Path pathDot;
    private int phase = 0;
    private int speed = 1;


    public Polygons(Context context) {
        super(context);
        init();
    }


    public Polygons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Polygons(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Polygons(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(1);
        circlePath = new Path();

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setColor(Color.BLUE);
        mDotPaint.setStyle(Paint.Style.FILL);

        pathDot = new Path();
        float radius = 10;
        pathDot.addCircle(radius / 2, radius / 2, radius, Path.Direction.CW);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float radius = cx - mCirclePaint.getStrokeWidth() / 2;
        circlePath = createPath(6, radius);
        canvas.drawPath(circlePath, mCirclePaint);

        phase += speed;
        pathMeasure.setPath(circlePath, true);
        mDotPaint.setPathEffect(new PathDashPathEffect(pathDot, pathMeasure.getLength(), phase, TRANSLATE));
        canvas.drawPath(circlePath, mDotPaint);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xpad = getPaddingLeft() + getPaddingRight();
        float ypad = getPaddingTop() + getPaddingBottom();
        cy = (h - ypad) / 2;
        cx = (w - xpad) / 2;
    }

    public void setWidth(int width) {
        mCirclePaint.setStrokeWidth(width);
        invalidate();
    }

    private Path createPath(int sides, float radius) {
        Path path = new Path();
        float angle = (float) (2.0 * Math.PI / sides);
        path.moveTo(
                (float) (cx + radius * Math.cos(0.0)),
                (float) (cy + radius * Math.sin(0.0))
        );
        for (int i = 0; i < sides; i++) {
            path.lineTo(
                    (float) (cx + radius * Math.cos(angle * i)),
                    (float) (cy + radius * Math.sin(angle * i))
            );
        }
        path.close();
        return path;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
