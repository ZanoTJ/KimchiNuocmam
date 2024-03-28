package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

class Button {
    private Bitmap mBitmapPlay;
    private Bitmap mBitmapPause;

    public Button(Context context){
        mBitmapPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        mBitmapPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
    }

    public void draw(Canvas canvas, Paint paint, boolean play){
        if (!play){
            canvas.drawBitmap(mBitmapPlay, 25, 55, paint);

            paint.setTextSize(250);
            canvas.drawText("Paused", canvas.getWidth()/4, canvas.getHeight()/2, paint);
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
