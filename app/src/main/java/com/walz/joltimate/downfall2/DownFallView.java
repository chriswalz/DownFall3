package com.walz.joltimate.downfall2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.walz.joltimate.downfall2.Invaders.BackgroundBlock;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

// what customization options do i want for a level & invader
// attach attributes to an invader
// create level structure.
// create give on edge of screen, create give on hitbox
// randomness
// review button, share button
// teleport animation

// USE STRING BUFFER INSTEAD

// animate the title, show it often so it gets ingrained in their head.
// encourage user to challenge their friends, encourage 5 star rating.

// Test out GLSurfaceView??
// Use the NDK??

// --- General Principles --- //
// Keep interface slick and simple (few buttons)
// Make it clear when a user loses or wins.
// More levels, make the progression slower.
// test on amazon cloud
// level selector

// PSYCHOLOGY - Show difficulty rating, Progress Part 2 (Level 3/100) ,
// make levels longer
// only a certain number of lives per day (40)!

// marketing
// email wilson w later
// use Firebase for: cloud notifications, IAP,
// firebase app indexing
// firebase share with friends

// Describing the game to a player:
// Surive by avoiding the red enemies
//
// A series of 50 mini levels
// The goal is to avoid the red blocks
// You can drag your finger to move around, the kicker is that you teleport as well!

// Notes from people observations:
// Game didnt get hard quick enough
// win animation is confusing.
// trail animation is a bit long. Go back to original

// localization / use res strings :(
// ease hit box
// change play store category
// soundpool
// tweak initial levels

// add notification service (determine best time and day??)
// 25 lives per day. Watch ad to get +25 ads (max is 100)
// beautiful animations, points

public class DownFallView extends SurfaceView implements Runnable{

    private Context context;

    // This is our thread
    private Thread gameThread = null;

    // Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;


    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;

    private Paint winCirclePaint;

    private boolean triggerWinAnimation = false;
    private boolean triggerLoseAnimation = false;
    private int animationFrames = 0;

    // This variable tracks the game frame rate
    private int fps;

    // This is used to help calculate the fps
    private long timeThisFrame;
    public static long numFrames;

    // The players ship
    public static PlayerShip playerShip; // Player

    // Up to 60 invaders
    private InvaderAbstract[] invaders = new InvaderAbstract[60];

    // Background parellax
    private BackgroundBlock[] backgroundBlocks = new BackgroundBlock[10];

    // The score
    private int score = 0;

    private final String scoreText = "Score: ";


    private int winCircleRadius = 0;

    private Paint textPaint;
    private Paint hudPaint;
    private int alpha = 255;
    private boolean increase = false;

    private int gameState;
    private final int STARTSCREEN = 0;
    public static final int PLAYINGSCREEN = 1;
    private final int RETRYSCREEN = 2;
    private final int WINSCREEN = 3;

    private float distanceFromEdge;
    private Vibrator v;

    private DownFallActivity downFallActivity;

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
        v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        gameState = STARTSCREEN;

        numFrames = 0;

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);               // if you like bold
        textPaint.setShadowLayer(5, 5, 5, Color.GRAY); // add shadow
        textPaint.setTextAlign(Paint.Align.CENTER);

        winCirclePaint = new Paint();
        winCirclePaint.setColor(Color.argb(255, 162, 255, 159));

        hudPaint = new Paint();
        hudPaint.setTextSize(80);
        hudPaint.setColor(Color.argb(255, 40, 40, 60));

        downFallActivity = (DownFallActivity) context;

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

        distanceFromEdge = 1;

        prepareCurrentLevel();

    }

    public void prepareCurrentLevel(){
        // reset game
        if (playerShip == null) {
            playerShip = new PlayerShip(context);
        } else {
            playerShip.reset();
        }
        numFrames = 0;
        gameState = STARTSCREEN;
        Levels.prepareLevel(playerShip, invaders, context);

    }

    @Override
    public void run() {

        while (playing) {


            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();


            updateBackground();
            if(gameState == PLAYINGSCREEN){
                updateForeground();
                if (!triggerLoseAnimation) {
                }
                playerShip.update(); // for loseAnimation only
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

            //canvas.drawArc(20, 20, 100, 100, 90, 20, true, paint);
            //canvas.


            drawBackground();
            drawForeground();
            if (triggerWinAnimation) {
                drawWinAnimation();
            }
            if (triggerLoseAnimation) {
                drawLoseAnimation();
            }
            drawHUD();

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void drawWinAnimation() {
        if (winCircleRadius < 3*Levels.screenHeight/4) {

            canvas.drawCircle((float) Levels.screenWidth/2, (float) Levels.screenHeight/2, winCircleRadius, winCirclePaint);
            winCircleRadius += Levels.screenHeight/60;
            numFrames--;

        } else {
            winCircleRadius = 0;
            gameState = WINSCREEN; // paused = true;
            triggerWinAnimation = false;
            score = 0;
            Levels.updateCurrentLevel(); ;
            prepareCurrentLevel();
            downFallActivity.setToWinScreen();
        }
    }
    private void drawLoseAnimation() {
        if (animationFrames < 60) {
            playerShip.setAlive(false);
            animationFrames++;
        } else {

            triggerLoseAnimation = false;
            animationFrames = 0;
            gameState = RETRYSCREEN; // paused = true;
            score = 0;
            downFallActivity.setToStartScreen();
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
            // player won -> go to win screen
            triggerWinAnimation = true;

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
            if (invaders[i].isColliding(playerShip)) { //invaders[i].isVisible && RectF.intersects(invaders[i].hitbox, playerShip.hitbox
                if (triggerWinAnimation) {
                    break;
                }
                if (!triggerLoseAnimation) {
                    v.vibrate(20);
                }
                triggerLoseAnimation = true;
            }
        }


    }

    private void drawBackground() {
        // Draw the background color
        //canvas.drawColor(Color.argb(255, 26, 128, 182));
        canvas.drawColor(Color.argb(255, 253, 234, 175));// rgb(253, 234, 175) Color.argb(255, 20, 20, 20));

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
        paint.setTextSize(20);
        if (Levels.debug) {
            canvas.drawText(scoreText + score + " Invaders: " + Levels.numInvaders + " Levels: " + Levels.currentLevel + " FPS: " + fps + " Difficulty Rating: " + Levels.difficultyRating + " Highest Level: " + Levels.highestLevel, 10,50, paint);
        }

        hudPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("" + (Math.round(numFrames*100/Levels.levelTimeLimit)) + "%" , 2 * Levels.screenWidth/100, 98 * Levels.screenHeight / 100, hudPaint);
        hudPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(""+Levels.score, 98 * Levels.screenWidth/100, 98 * Levels.screenHeight / 100, hudPaint);

        if (alpha >= 255) {
            increase = false;
        }
        if (alpha < 150) {
            increase = true;
        }
        if (increase) {
            alpha += 2;
        } else {
            alpha -= 2;
        }
        if (gameState == STARTSCREEN) {
            textPaint.setColor(Color.argb(255, 255, 255, 255));
            textPaint.setTextSize(80);
            canvas.drawText(Levels.startText, Levels.screenWidth/2, 5*Levels.screenHeight/16, textPaint);
            textPaint.setColor(Color.argb(alpha, 255, 255, 255));
            textPaint.setTextSize(30);
            //canvas.drawText("Tap to Start", Levels.screenWidth/2, 3*Levels.screenHeight/5, textPaint);
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

    private float pX;
    private float pY;
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        pX = motionEvent.getX();
        pY = motionEvent.getY();

        // only have error touching prevention for bottom 3 / 8;
        if (pY > 3 * Levels.screenHeight && (pX < distanceFromEdge || pY < distanceFromEdge || pX > Levels.screenWidth - distanceFromEdge || pY > Levels.screenHeight - distanceFromEdge)) {
        } else {
            playerShip.setLocation(motionEvent.getX(), motionEvent.getY(), gameState);
        }




        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen

            case MotionEvent.ACTION_DOWN:
                if (gameState == STARTSCREEN) {
                    if (pY > Levels.screenHeight/5 && pY < 4*Levels.screenHeight/5) {
                        downFallActivity.setToPlayingScreen();
                        gameState = PLAYINGSCREEN;
                    }
                }
                // TODO research motionEvent.getPrecision

                break;
            default:
                break;
        }
        return true;
    }
}