package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

/**
 * Created by taekjinjung on 3/26/2024.
 */

public class StartPage extends SnakeGame {
    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    public StartPage(Context context, Point size){
        super(context, size);
    };

    public void draw(){
        // Set the size and color of the mPaint for the text
        super.mPaint.setTextSize(250);

        // Draw the message
        mCanvas.drawText(getResources().
                        getString(R.string.tap_to_play),
                200, 700, mPaint);

        //Draw our names
        mPaint.setColor(Color.argb(255, 64, 224, 208));
        mPaint.setTextSize(120);
        mCanvas.drawText("David Pham", (mCanvas.getWidth()/10)*6, (mCanvas.getHeight()/10) * 1, mPaint);
        mCanvas.drawText("Taekjin Jung", (mCanvas.getWidth()/10)*6, (mCanvas.getHeight()/10)*2, mPaint);
    }
}
