package com.nightfarmer.bezierdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangfan on 16-8-23.
 */
public class BezierDrawer extends View {
    private Paint paint;
    private Paint lineEndPaint;
    private Paint pointPaint;
    private Paint linePaint;

    List<Point> pointList = new ArrayList<>();
    private int touchedPoint;

    public BezierDrawer(Context context) {
        this(context, null);
    }

    public BezierDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//抗锯齿

        lineEndPaint = new Paint();
        lineEndPaint.setColor(Color.BLUE);
        lineEndPaint.setStyle(Paint.Style.FILL);
        lineEndPaint.setAntiAlias(true);//抗锯齿

        pointPaint = new Paint();
        pointPaint.setStrokeWidth(8);
        pointPaint.setColor(Color.DKGRAY);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setAntiAlias(true);//抗锯齿

        linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setColor(Color.DKGRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);//抗锯齿
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.
        Path path = new Path();
        Point startPoint = pointList.get(0);
        Point anchorPoint = pointList.get(1);
        Point endPoint = pointList.get(2);
        path.moveTo(startPoint.x, startPoint.y);

//        path.cubicTo(anchorPoint.x, anchorPoint.y, 265, 405, 405, 5);
        path.quadTo(anchorPoint.x, anchorPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(path, paint);

        canvas.drawCircle(startPoint.x, startPoint.y, paint.getStrokeWidth() / 2, lineEndPaint);
        canvas.drawCircle(endPoint.x, endPoint.y, paint.getStrokeWidth() / 2, lineEndPaint);


        canvas.drawLine(startPoint.x, startPoint.y, anchorPoint.x, anchorPoint.y, linePaint);
        canvas.drawLine(anchorPoint.x, anchorPoint.y, endPoint.x, endPoint.y, linePaint);

        for (Point point : pointList) {
            canvas.drawCircle(point.x, point.y, 20, pointPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (pointList.size() == 0) {
            pointList.add(new Point(width / 5, height / 2));
            pointList.add(new Point(width / 2, height / 4));
            pointList.add(new Point(width / 5 * 4, height / 2));
            if (onChangeListener != null) onChangeListener.onchange(pointList);
        }
    }

    int touchDist = 50;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point point = new Point((int) event.getX(), (int) event.getY());
                touchedPoint = getNearestPoint(point);
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchedPoint != -1) {
                    Point pointMove = new Point((int) event.getX(), (int) event.getY());
                    pointList.set(touchedPoint, pointMove);
                    invalidate();
                    if (onChangeListener != null) onChangeListener.onchange(pointList);
                }
                break;

            case MotionEvent.ACTION_UP:
                touchedPoint = -1;
                break;
            default:

        }
        return true;
    }

    private int getNearestPoint(Point point) {
        for (int i = 0; i < pointList.size(); i++) {
            Point p = pointList.get(i);
            if (Math.abs(p.x - point.x) < touchDist && Math.abs(p.y - point.y) < touchDist) {
                return i;
            }
        }
        return -1;
    }

    interface OnChangeListener {
        void onchange(List<Point> pointList);
    }
    private OnChangeListener onChangeListener;
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }
}
