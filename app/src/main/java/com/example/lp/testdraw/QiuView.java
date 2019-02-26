package com.example.lp.testdraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class QiuView extends View  {
private String TAG="QiuView";

    private Bitmap bitmap;
    private float planeX=1f;
    private float planeY=1f;


    public Paint paint = new Paint();
   public   PointF point = new PointF();
    public QiuView(Context context) {
        super(context);

    }

    public QiuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "QiuView: ");
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i(TAG, "onDraw: ");
       // canvas.drawCircle(point.x, point.y, 20, paint);
      bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point2);//通过使用bitmapfactory的decoderesource来获得bitmap
        canvas.drawBitmap(bitmap,planeX-bitmap.getWidth()/2,planeY-bitmap.getHeight()/2,new Paint());
    }



    public void setPlane(float x,float y) {
        planeX=x;
        planeY=y;
       // point.set(x,y);
    }
}

