package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

/**
 * Created by taekjinjung on 3/22/2024.
 */

class Button extends GameObject {
    private Bitmap mBitmapPlay;
    private Bitmap mBitmapPause;

    public Button(Context context, Point mr, int ss){
        mBitmapPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        mBitmapPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
    }

    public void draw(Canvas canvas, Paint paint, boolean play){
        if (!play){
            canvas.drawBitmap(mBitmapPlay, 25, 55, paint);
        }else {
            canvas.drawBitmap(mBitmapPause, 25, 55, paint);
        }
    }

    public boolean buttonRange(MotionEvent motionEvent){
        float left = 25;
        float right = left + mBitmapPlay.getWidth();
        float top = 55;
        float bottom = top + mBitmapPlay.getHeight();

        return motionEvent.getX() >= left && motionEvent.getX() <= right && motionEvent.getY() >= top && motionEvent.getY() <= bottom;
    }
}
