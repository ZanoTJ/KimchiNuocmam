package com.gamecodeschool.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Apple extends GameObject{
    private static final int INITIAL_OFFSCREEN_X = -10;

    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    //private Point mSpawnRange;
    //private int mSize;

    // An image to represent the apple
    private Bitmap mBitmapApple;

    /// Set up the apple in the constructor
    public Apple(Context context, Point screenRange, int size){
        super(context, screenRange, size);
        location.x = INITIAL_OFFSCREEN_X;
        // Make a note of the passed in spawn range
        //mSpawnRange = sr;
        // Make a note of the size of an apple
        //mSegmentSize = s;
        // Hide the apple off-screen until the game starts

        // Load the image to the bitmap
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);

        // Resize the bitmap
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, size, size, false);
    }

    // This is called every time an apple is eaten
    void spawn(){
        // Choose two random values and place the apple
        location.x = ThreadLocalRandom.current().nextInt(mScreenRange.x) + 1;
        location.y = ThreadLocalRandom.current().nextInt(mScreenRange.y - 1) + 1;
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }

    public void setLocation(Point location){
        this.location = location;
    }

    // Draw the apple
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSegmentSize, location.y * mSegmentSize, paint);

    }

}