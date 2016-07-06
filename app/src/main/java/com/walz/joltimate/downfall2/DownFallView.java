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

import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

// create level object ?
// what customization options do i want for a level & invader
// attach attributes to an invader
// create level structure.
// create homescreen
// saved data
// create give on edge of screen, create give on rect

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
    private long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;
    private long numFrames;

    // The size of the screen in pixels
    private int screenX;
    private int screenY;

    // Current Level
    private int currentLevel = 0;

    // The players ship
    private PlayerShip playerShip; // Player

    // Up to 60 invaders
    private InvaderAbstract[] invaders = new InvaderAbstract[60];

    // The score
    private int score = 0;

    private final String scoreText = "Score: ";

    private Paint invaderPaint;

    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public DownFallView(Context context, int x, int y) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        invaderPaint = new Paint();
        invaderPaint.setColor(Color.WHITE);

        prepareCurrentLevel();
    }

    private void prepareCurrentLevel(){

        // Here we will initialize all the game objects

        // Make a new player space ship
        playerShip = new PlayerShip(context, screenX, screenY);
        numFrames = 0;

        switch (currentLevel) {
            case 0:
                Levels.oneBlock(invaders, context, screenX, screenY);
                break;
            case 1:
                Levels.tenInvaders(invaders, context, screenX, screenY);
        }


    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            if(!paused){
                update();
            }

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }



    }

    private void update(){

        // Beat level
        if (numFrames > Levels.levelTimeLimit) {
            paused = true;
            score = 0;
            prepareCurrentLevel();

            // player won -> go to win screen
            Intent intent = new Intent(context, WinScreenActivity.class);
            context.startActivity(intent);

        }
        numFrames++;

        // Update all the invaders if visible
        for(int i = 0; i < Levels.numInvaders; i++){
            // Move the next invader
            invaders[i].update(fps);

        }


        // Has an invader touched the player ship
        for (int i = 0; i < Levels.numInvaders; i++) {
            if (invaders[i] == null) {
                continue;
            }
            // Lost level
            if (invaders[i].getVisibility() && RectF.intersects(invaders[i].getRect(), playerShip.getRect())) {
                paused = true;
                score = 0;
                prepareCurrentLevel();

                // player died -> go to homescreen
                Intent intent = new Intent(context, RetryScreenActivity.class);
                context.startActivity(intent);
            }
        }


    }


    private void draw(){
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));

            // Now draw the player spaceship
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX(), playerShip.getY(), paint);

            // Draw the invaders
            for(int i = 0; i < Levels.numInvaders; i++){
                if(invaders[i].getVisibility()) {
                    canvas.drawRect(invaders[i].getRect(), invaderPaint);
                    //canvas.drawBitmap(invaders[i].getBitmap(), invaders[i].getX(), invaders[i].getY(), paint);
                }
            }

            // Draw the score and remaining lives
            // Change the brush color
            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText(scoreText + score, 10,50, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
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