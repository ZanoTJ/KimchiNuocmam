package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

abstract class GameObject {
    //The range of entire grid
    protected Point mScreenRange;
    protected int mSegmentSize;  //each grid size

    public GameObject(Context context, Point mr, int s){
        mScreenRange = mr;
        mSegmentSize = s;
    }

    abstract void draw(Canvas canvas, Paint paint);
}
