package com.walz.joltimate.downfall2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.walz.joltimate.downfall2.Invaders.BackgroundBlock;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

// create level object ?
// what customization options do i want for a level & invader
// attach attributes to an invader
// create level structure.
// create homescreen
// saved data
// create give on edge of screen, create give on rect
// randomness

// Test out GLSurfaceView??
// Use the NDK??

// --- General Principles --- //
// Keep interface slick and simple (few buttons)
// Make it clear when a user loses or wins.
// More levels, make the progression slower.
// test on amazon cloud
// level selector


// marketing
// email wilson w later
// use Firebase for: cloud notifications, IAP,
// firebase app indexing
// firebase share with friends

// Describing the game to a player:
// A series of 50 mini levels
// The goal is to avoid the white blocks
// You can drag your finger to move around, the kicker is that you teleport as well!


public class DownFallView extends SurfaceView implements Runnable{

    private Context context;

    // This is our thread
    private Thread gameThread = null;

    // Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;

    // Game is paused at the start
    private boolean paused = true;

    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;

    // This variable tracks the game frame rate
    private int fps;

    // This is used to help calculate the fps
    private long timeThisFrame;
    private long numFrames;

    // The players ship
    private PlayerShip playerShip; // Player

    // Up to 60 invaders
    private InvaderAbstract[] invaders = new InvaderAbstract[60];

    // Background parellax
    private BackgroundBlock[] backgroundBlocks = new BackgroundBlock[10];

    // The score
    private int score = 0;

    private final String scoreText = "Score: ";



    private Paint textPaint;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public DownFallView(Context context) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);               // if you like bold
        textPaint.setShadowLayer(5, 5, 5, Color.GRAY); // add shadow
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(120);

        textPaint.setColor(Color.argb(255, 255, 255, 255));

        int size = Levels.screenHeight/15;
        int distX = Levels.screenWidth - size;
        int distY = Levels.screenHeight - size;
        int sizeX;
        int sizeY;
        for(int i = 0; i < backgroundBlocks.length; i++){
            sizeX = (int) (Math.random() * Levels.screenWidth/8 + size);
            sizeY = (int) (Math.random() * Levels.screenWidth/8 + size);
            backgroundBlocks[i] = new BackgroundBlock(context, (int)(Math.random()*Levels.screenWidth), (int)(Math.random()*Levels.screenHeight), sizeX, sizeY);
        }

        prepareCurrentLevel();
    }

    private void prepareCurrentLevel(){

        // Here we will initialize all the game objects



        // Make a new player space ship


        playerShip = new PlayerShip(context);
        numFrames = 0;

        Levels.prepareLevel(invaders, context);

    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();


            updateBackground();
            if(!paused){
                updateForeground();
            }

            draw();
            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = (int) (1000 / timeThisFrame);
            }

        }
    }
    private void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            drawBackground();
            drawForeground();
            drawHUD();

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void updateBackground() {
        // Update all of the background
        for(int i = 0; i < backgroundBlocks.length; i++){
            backgroundBlocks[i].update(fps);
        }

    }
    private void updateForeground() {

        // Beat level
        if (numFrames >= Levels.levelTimeLimit) {
            paused = true;
            score = 0;
            //prepareCurrentLevel();
            Levels.currentLevel++;

            // player won -> go to win screen

            Intent intent = new Intent(context, WinScreenActivity.class);
            context.startActivity(intent);

        }
        numFrames++;

        // Update all the invaders
        for(int i = 0; i < Levels.numInvaders; i++){
            // Move the next invader
            if (invaders[i] == null) {
                continue;
            }
            invaders[i].update(fps);

        }


        // Has an invader touched the player ship
        for (int i = 0; i < Levels.numInvaders; i++) {
            if (invaders[i] == null) {
                continue;
            }
            // Lost level
            if (invaders[i].isColliding(playerShip)) { //invaders[i].isVisible && RectF.intersects(invaders[i].rect, playerShip.rect
                paused = true;
                score = 0;
                //prepareCurrentLevel();

                // player died -> go to homescreen
                Intent intent = new Intent(context, RetryScreenActivity.class);
                context.startActivity(intent);
            }
        }


    }
    private void drawBackground() {
        // Draw the background color
        //canvas.drawColor(Color.argb(255, 26, 128, 182));
        canvas.drawColor(Color.argb(255, 20, 20, 20));

        // Draw the background blocks
        for(int i = 0; i < backgroundBlocks.length; i++){
            backgroundBlocks[i].draw(canvas);
            if(backgroundBlocks[i].isVisible) {
            }
        }

    }

    private void drawForeground(){

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 203, 232, 107));

            // Now draw the player spaceship
            //canvas.drawBitmap(playerShip.bitmap, playerShip.x, playerShip.y, paint);
            playerShip.draw(canvas);


            // Draw the invaders
            for(int i = 0; i < Levels.numInvaders; i++){
                if(invaders[i] != null && invaders[i].isVisible) {
                    invaders[i].draw(canvas);
                    //canvas.drawBitmap(invaders[i].getBitmap(), invaders[i].getX(), invaders[i].getY(), paint);
                }
            }

    }
    private void drawHUD() {
        // Draw the score and remaining lives
        // Change the brush color
        paint.setColor(Color.argb(255,  249, 129, 0));
        paint.setTextSize(40);
        canvas.drawText(scoreText + score + " Invaders: " + Levels.numInvaders + " Levels: " + Levels.currentLevel + " FPS: " + fps, 10,50, paint);
        canvas.drawText(" Timer: " + (Levels.levelTimeLimit - numFrames), 10, Levels.screenHeight - 20, paint);

        if (paused) {
            canvas.drawText(Levels.startText, Levels.screenWidth/2, Levels.screenHeight/2, textPaint);
        }

    }

    // If DownFallActivity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If DownFallActivity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        playerShip.setLocation(motionEvent.getX(), motionEvent.getY());


        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen

            case MotionEvent.ACTION_DOWN:
                paused = false;
                // TODO research motionEvent.getPrecision

                break;
            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                // change animation state
                break;
        }
        return true;
    }
}