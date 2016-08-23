package com.walz.joltimate.downfall2.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.walz.joltimate.downfall2.DownFallActivity;
import com.walz.joltimate.downfall2.DownFallView;
import com.walz.joltimate.downfall2.Invaders.BackgroundBlock;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

/**
 * Created by chris on 8/22/16.
 */
public class DownFallGame {
    private volatile boolean playing;
    public static long numFrames;

    // The players ship
    public static PlayerShip playerShip; // Player


    // Up to 60 invaders
    private InvaderAbstract[] invaders = new InvaderAbstract[60];

    // Background parellax
    private BackgroundBlock[] backgroundBlocks = new BackgroundBlock[10];

    private int gameState;
    private final int STARTSCREEN = 0;
    private final int PLAYINGSCREEN = 1;
    private final int WINSCREEN = 3;

    // A Canvas and a Paint object
    private Canvas canvas;
    private DownFallActivity downFallActivity;
    private DownFallView downFallView;
    private Context context;
    private SurfaceHolder ourHolder;

    // Move out of this object??, make levels non-static?
    private int fps;
    private long timeThisFrame;
    private boolean triggerWinAnimation = false;
    private boolean triggerLoseAnimation = false;
    private int animationFrames = 0;

    private Paint paint;
    private Paint winCirclePaint;
    private final String scoreText = "Score: ";
    private int winCircleRadius = 0;
    private Paint textPaint;
    private Paint hudPaint;
    private int alpha = 255;
    private boolean increase = false;


    public DownFallGame(Context context, DownFallActivity downFallActivity, DownFallView downFallView, SurfaceHolder ourHolder, int widthX, int widthY) {
        this.downFallActivity = downFallActivity;
        this.downFallView = downFallView;
        this.context = context;

        Levels.init(context, widthX, widthY, playerShip);

        gameState = STARTSCREEN;
        numFrames = 0;

        int size = Levels.screenHeight/15;
        int sizeX;
        int sizeY;
        for(int i = 0; i < backgroundBlocks.length; i++){
            sizeX = (int) (Math.random() * Levels.screenWidth/8 + size);
            sizeY = (int) (Math.random() * Levels.screenWidth/8 + size);
            backgroundBlocks[i] = new BackgroundBlock(context, (int)(Math.random()*Levels.screenWidth), (int)(Math.random()*Levels.screenHeight), sizeX, sizeY);
        }

        this.ourHolder = ourHolder;

        paint = new Paint();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);               // if you like bold
        textPaint.setShadowLayer(5, 5, 5, Color.GRAY); // add shadow
        textPaint.setTextAlign(Paint.Align.CENTER);

        winCirclePaint = new Paint();
        winCirclePaint.setColor(Color.argb(255, 162, 255, 159));

        Typeface tf = Typeface.create("Roboto", Typeface.NORMAL);
        hudPaint = new Paint();
        hudPaint.setTypeface(tf);
        hudPaint.setTextSize(60);
        hudPaint.setColor(Color.argb(255, 40, 40, 60));
    }
    public void run() {
        while (playing) {


            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            updateBackground();
            if(gameState == PLAYINGSCREEN){
                updateForeground();
            }
            draw();
            if (gameState == PLAYINGSCREEN) {
                playerShip.update(); // for loseAnimation only
            }

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = (int) (1000 / timeThisFrame);
            }

        }
    }
    public void prepareCurrentLevel(){
        // reset game
        downFallActivity.setHelpTextView(Levels.startText);
        numFrames = 0;
        gameState = STARTSCREEN;
        Levels.prepareLevel(playerShip, invaders, context);
        if (playerShip == null) {
            playerShip = new PlayerShip(context);
        } else {
            playerShip.reset();
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
            if (triggerWinAnimation) {
                drawWinAnimation();
            }
            drawForeground();
            if (triggerLoseAnimation) {
                drawLoseAnimation();
            }
            drawHUD();

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void drawWinAnimation() {
        if (winCircleRadius < 4*Levels.screenHeight/4) {

            canvas.drawCircle((float) playerShip.getCenterX(), (float) playerShip.getCenterY(), winCircleRadius, winCirclePaint);
            winCircleRadius += Levels.screenHeight/40;
            numFrames--;

        } else {
            downFallActivity.playWinSound();
            winCircleRadius = 0;
            triggerWinAnimation = false;
            downFallActivity.fireWinEvent();
            Levels.updateCurrentLevel(); ;
            gameState = WINSCREEN; // paused = true;
            prepareCurrentLevel();
            downFallActivity.setToWinScreen();
            downFallActivity.showInterstitialIfReady();
        }
    }
    private void drawLoseAnimation() {
        if (animationFrames < 40) {
            playerShip.setAlive(false);
            animationFrames++;
        } else {
            lose();
        }

    }
    private void lose() {
        Levels.saveNumberAttempts();
        Levels.saveRequestAdAmount();
        Levels.saveScore();
        downFallActivity.fireLoseEvent(Math.round(numFrames*100/Levels.levelTimeLimit));
        Levels.numberAttempts++;
        triggerLoseAnimation = false;
        animationFrames = 0;
        gameState = STARTSCREEN; // paused = true;
        downFallActivity.setToStartScreen();
        downFallActivity.showInterstitialIfReady();
    }
    private void updateBackground() {
        // Update all of the background
        for(int i = 0; i < backgroundBlocks.length; i++){
            backgroundBlocks[i].update(fps);
        }

    }
    private void updateForeground() {

        // Beat level
        if (numFrames >= Levels.levelTimeLimit && !triggerLoseAnimation) {
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
                    downFallActivity.playLoseSound();
                }
                triggerLoseAnimation = true;
            }
        }
        if (numFrames % 50 == 0 && !triggerLoseAnimation) {
            Levels.score += 1;
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
            canvas.drawText("gamestate"+gameState+": " + scoreText + Levels.score + " Invaders: " + Levels.numInvaders + " Levels: " + Levels.currentLevel + " FPS: " + fps + " Difficulty Rating: " + Levels.difficultyRating + " Highest Level: " + Levels.highestLevel, 10,50, paint);
        }

        hudPaint.setTextAlign(Paint.Align.LEFT);
        if (gameState == PLAYINGSCREEN) {
            canvas.drawText("" + (Math.round(numFrames*100/Levels.levelTimeLimit)) + "%" , 2 * Levels.screenWidth/100, 98 * Levels.screenHeight / 100, hudPaint);
        } else {
            canvas.drawText("Level: "+(Levels.currentLevel+1), 2 * Levels.screenWidth/100, 98 * Levels.screenHeight / 100, hudPaint);
        }
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
            //textPaint.setColor(Color.argb(255, 255, 255, 255));
            //textPaint.setTextSize(80);
            //canvas.drawText(Levels.startText, Levels.screenWidth/2, 5*Levels.screenHeight/16, textPaint);
            textPaint.setColor(Color.argb(alpha, 255, 255, 255));
            textPaint.setTextSize(30);
            //canvas.drawText("Tap to Start", Levels.screenWidth/2, 3*Levels.screenHeight/5, textPaint);
        }

    }

    public void touchEvent(MotionEvent motionEvent) {
        float pX;
        float pY;
        int index;
        int distanceFromEdge = 1;

        index = motionEvent.getPointerCount() - 1; //MotionEventCompat.getActionIndex(motionEvent);

        if (motionEvent.getPointerCount() > 1) {
            // the responding View or Activity.
            pX = (int) MotionEventCompat.getX(motionEvent, index);
            pY = (int)MotionEventCompat.getY(motionEvent, index);

        } else {

            pX = (int)MotionEventCompat.getX(motionEvent, index);
            pY = (int)MotionEventCompat.getY(motionEvent, index);
        }

        // pX = motionEvent.getX(motionEvent.getActionIndex());
        //pY = motionEvent.getY(motionEvent.getActionIndex());

        // only have error touching prevention for bottom 3 / 8;
        if (pY > (3*Levels.screenHeight/4) && (pX < distanceFromEdge || pY < distanceFromEdge || pX > Levels.screenWidth - distanceFromEdge || pY > Levels.screenHeight - distanceFromEdge)) {
        } else {
            if (gameState == PLAYINGSCREEN) {
                playerShip.setLocation(pX, pY);
            }
        }

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen

            case MotionEvent.ACTION_DOWN:
                //Log.d("touch", ""+pX + pY + " height: "+Levels.screenHeight);
                if (gameState == STARTSCREEN) {
                    if (pY > Levels.screenHeight/5 && pY < 4*Levels.screenHeight/5) {
                        if (downFallActivity.shouldPrepareLevelAgain()) {
                            prepareCurrentLevel();
                        }
                        downFallActivity.setToPlayingScreen();
                        gameState = PLAYINGSCREEN;
                    }
                }
                if (gameState == WINSCREEN) {
                    downFallActivity.setToStartScreen();
                    gameState = STARTSCREEN;
                }
                if (gameState == PLAYINGSCREEN && playerShip.getAlive()) {
                    downFallActivity.playTeleportSound();
                }
                // TODO research motionEvent.getPrecision

                break;
            default:
                break;
        }
    }
    public void pause() {
        playing = false;
        if (gameState == PLAYINGSCREEN) {
            lose();
        }
    }
    public void resume() {
        playing = true;
    }

}
