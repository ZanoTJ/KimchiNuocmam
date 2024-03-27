package com.gamecodeschool.snakegame;

/**
 * Created by taekjinjung on 3/26/2024.
 */

import android.content.Context;
import android.graphics.Point;

public class GameObject {
    //The range of entire grid
    protected Point mScreenRange;
    protected int mSegmentSize;  //each grid size

    public GameObject(Context context, Point mr, int s){
        mScreenRange = mr;
        mSegmentSize = s;
    }

    
}
