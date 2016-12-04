package me.shuobi_wu.awwtter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Chris on 12/3/16.
 */

public class MapCanvasView extends View {

    public static Paint mPaint;
    public static Canvas mCanvas;

    private int mPivotX = 0;
    private int mPivotY = 0;
    private int radius = 60;



    /**
     * constructor
     *
     * @param context
     * @param attrs
     */
    public MapCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    /**
     * draw a circle
     */
    public void drawCircle() {

        int minX = radius * 2;
        int maxX = getWidth() - (radius *2 );

        int minY = radius * 2;
        int maxY = getHeight() - (radius *2 );

        //Generate random numbers for x and y locations of the circle on screen
        Random random = new Random();
        mPivotX = random.nextInt(maxX - minX + 1) + minX;
        mPivotY = random.nextInt(maxY - minY + 1) + minY;

        //important. Refreshes the view by calling onDraw function
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        super.onDraw(mCanvas);
        canvas.drawColor(Color.GRAY);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mPivotX, mPivotY, radius, mPaint);
    }
}
