package com.gamecodeschool.snakegame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

class SnakeGame extends SurfaceView implements Runnable{

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;
    private volatile boolean gotReset = true;

    // for playing sound effects
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    private int mBlockSize;

    //get the screen range
    private Point mScreenRange;

    // How many points does the player have
    private int mScore;

    // Objects for drawing
    protected Canvas mCanvas;
    protected SurfaceHolder mSurfaceHolder;
    protected Paint mPaint;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;

    private Interfaces mInterfaces;


    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);

        setBlocks(size);

        setSounds(context);

        setObjects(context);
    }

    private void setBlocks(Point size){
        // Work out how many pixels each block is
        mBlockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / mBlockSize;
    }

    private void setSounds(Context context){
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
    }

    private void setObjects(Context context){
        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        mScreenRange = new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Call the constructors of our two game objects
        mApple = new Apple(context, mScreenRange, mBlockSize);
        mSnake = new Snake(context, mScreenRange, mBlockSize);
        mInterfaces = new Interfaces(context, mScreenRange, mBlockSize); //user interface
    }

    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawn();

        // Reset the mScore
        mScore = 0;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {

        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if(mSnake.checkDinner(mApple.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // Pause the game ready to start again
            mSP.play(mCrashID, 1, 1, 0, 0, 1);

            mPaused =true;
            gotReset = true;
        }

    }


    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 75, 83, 32));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            // Draw some text while paused
            if(mPaused && gotReset){
                startPage();
            }else{
                inGamePage();
            }

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void startPage(){
        // Set the size and color of the mPaint for the text
        mPaint.setTextSize(250);

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

    private void inGamePage(){
        // Draw the score
        mCanvas.drawText("Score: " + mScore, (mCanvas.getWidth()/10)*7, 120, mPaint);

        if(mPaused){
            mInterfaces.draw(mCanvas, mPaint, false);   //draw play button
        }else{
            mInterfaces.draw(mCanvas, mPaint, true);    //draw pause button
        }

        // Draw the apple and the snake
        mApple.draw(mCanvas, mPaint);
        mSnake.draw(mCanvas, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                return validTouch(motionEvent);

            default:
                break;

        }
        return true;
    }

    //MAKE STARTPAGE CLASS & INGAMEPAGE CLASS AS CHILD CLASSES OF SNAKEGAME
    private boolean validTouch(MotionEvent motionEvent){
        if (mPaused && gotReset) {  //for new start
            mPaused = false;
            gotReset = false;
            newGame();

            return true;
        }else if(!mPaused && mInterfaces.buttonRange(motionEvent)){ //to pause button
            mPaused = true;

        }else if(mPaused && mInterfaces.buttonRange(motionEvent)){  //to play button
            mPaused = false;

        }else if(!mPaused){                                     //when the game is playing
            // Let the Snake class handle the input
            mSnake.switchHeading(motionEvent);
        }

        // Don't want to process snake direction for this tap
        return true;
    }


    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }
}
