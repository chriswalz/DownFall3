package com.walz.joltimate.downfall2.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.walz.joltimate.downfall2.DownFallActivity;
import com.walz.joltimate.downfall2.DownFallView;
import com.walz.joltimate.downfall2.Invaders.BackgroundBlock;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.animations.LoseAnimation;
import com.walz.joltimate.downfall2.animations.WinAnimation;
import com.walz.joltimate.downfall2.data.DownFallStorage;

/**
 * Created by chris on 8/22/16.
 */
public class DownFallGame {
    private volatile boolean playing;
    public static long numFrames;

    // The players ship
    public PlayerShip playerShip; // Player


    // Up to 60 invaders
    private InvaderAbstract[] invaders = new InvaderAbstract[60];

    // Background parellax
    private BackgroundBlock[] backgroundBlocks = new BackgroundBlock[5];

    private int gameState;
    private final int STARTSCREEN = 0;
    private final int PLAYINGSCREEN = 1;
    private final int WINSCREEN = 3;

    // A Canvas and a Paint object
    private Canvas canvas;
    private final DownFallActivity downFallActivity;
    private DownFallView downFallView;
    private Context context;
    private SurfaceHolder ourHolder;
    public Levels levels;

    // Move out of this object??, make levels non-static?
    private int fps;
    private long timeThisFrame;

    private Paint paint;

    private final String scoreText = "Score: ";

    private Paint textPaint;
    private Paint hudPaint;

    private WinAnimation winAnimation;
    private LoseAnimation loseAnimation;



    public DownFallGame(Context context, final DownFallActivity downFallActivity, DownFallView downFallView, SurfaceHolder ourHolder, final int widthX, int widthY) {
        this.downFallActivity = downFallActivity;
        this.downFallView = downFallView;
        this.context = context;

        DownFallStorage.init(context, widthX, widthY);
        playerShip = new PlayerShip(context);
        levels = new Levels(playerShip);

        gameState = STARTSCREEN;
        numFrames = 0;

        int size = DownFallStorage.screenHeight/15;
        int sizeX;
        int sizeY;
        for(int i = 0; i < backgroundBlocks.length; i++){
            sizeX = (int) (Math.random() * DownFallStorage.screenWidth/12 + size);
            sizeY = (int) (Math.random() * DownFallStorage.screenWidth/12 + size);
            backgroundBlocks[i] = new BackgroundBlock(context, (int)(Math.random()* DownFallStorage.screenWidth), (int)(Math.random()* DownFallStorage.screenHeight), sizeX, sizeY);
        }

        this.ourHolder = ourHolder;

        paint = new Paint();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);               // if you like bold
        textPaint.setShadowLayer(5, 5, 5, Color.GRAY); // add shadow
        textPaint.setTextAlign(Paint.Align.CENTER);

        Typeface tf = Typeface.create("Roboto", Typeface.NORMAL);
        hudPaint = new Paint();
        hudPaint.setTypeface(tf);
        hudPaint.setTextSize(60);
        hudPaint.setColor(Color.argb(255, 40, 40, 60));

        winAnimation = new WinAnimation() {
            @Override
            public void onComplete() {
                win();
            }
        };
        loseAnimation = new LoseAnimation() {
            @Override
            public void onComplete() {
                lose();
            }
        };
    }
    private void win() {
        downFallActivity.playWinSound();
        downFallActivity.fireWinEvent();
        levels.updateCurrentLevel(); ;
        prepareCurrentLevel();
        gameState = WINSCREEN;
        downFallActivity.setToWinScreen();
        downFallActivity.showInterstitialIfReady();
    }
    private void lose() {
        downFallActivity.fireLoseEvent(Math.round(numFrames*100/ levels.getCurrentLevel().levelTimeLimit));
        DownFallStorage.numberAttempts++;
        gameState = STARTSCREEN; // paused = true;
        downFallActivity.setToStartScreen();
        downFallActivity.showInterstitialIfReady();
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
        numFrames = 0;
        gameState = STARTSCREEN;
        levels.prepareLevel(invaders, context);
        playerShip.reset();
        downFallActivity.setHelpTextView(getCurrentLevel().startText);
    }
    private void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            //canvas.drawArc(20, 20, 100, 100, 90, 20, true, paint);
            //canvas.


            drawBackground();
            winAnimation.draw(canvas, playerShip);
            drawForeground();
            loseAnimation.draw(playerShip);
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
        if (numFrames >= levels.getCurrentLevel().levelTimeLimit && !loseAnimation.isAnimating()) {
            // player won -> go to win screen
            winAnimation.start();


        }
        if (!winAnimation.isAnimating()) {
            numFrames++;
        }

        // Update all the invaders
        for(int i = 0; i < getCurrentLevel().numInvaders; i++){
            // Move the next invader
            if (invaders[i] == null) {
                continue;
            }
            invaders[i].update(fps);

        }


        // Has an invader touched the player ship
        for (int i = 0; i < getCurrentLevel().numInvaders; i++) {
            if (invaders[i] == null) {
                continue;
            }
            // Lost level
            if (invaders[i].isColliding(playerShip)) { //invaders[i].isVisible && RectF.intersects(invaders[i].hitbox, playerShip.hitbox
                if (winAnimation.isAnimating()) {
                    break;
                }
                if (!loseAnimation.isAnimating()) {
                    downFallActivity.playLoseSound();
                }
                loseAnimation.start();
            }
        }
        if (numFrames % 50 == 0 && !loseAnimation.isAnimating()) {
            DownFallStorage.score += 1;
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
        for(int i = 0; i < getCurrentLevel().numInvaders; i++){
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
        if (DownFallStorage.debug) {
            canvas.drawText(levels.getCurrentLevel().startText+"  levelTimeLimit: " + levels.getCurrentLevel().levelTimeLimit+ " gamestate"+gameState+": " + scoreText + DownFallStorage.score + " Invaders: " + getCurrentLevel().numInvaders + " Levels: " + DownFallStorage.currentLevel + " FPS: " + fps + " Difficulty Rating: " + getCurrentLevel().difficultyRating + " Highest Level: " + DownFallStorage.highestLevel, 10,50, paint);
        }

        hudPaint.setTextAlign(Paint.Align.LEFT);
        if (gameState == PLAYINGSCREEN) {
            canvas.drawText("" + (Math.round(numFrames*100/ (levels.getCurrentLevel().levelTimeLimit))) + "%" , 2 * DownFallStorage.screenWidth/100, 98 * DownFallStorage.screenHeight / 100, hudPaint);
        } else {
            canvas.drawText("Level: "+(DownFallStorage.currentLevel+1), 2 * DownFallStorage.screenWidth/100, 98 * DownFallStorage.screenHeight / 100, hudPaint);
        }
        hudPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(""+ DownFallStorage.score, 98 * DownFallStorage.screenWidth/100, 98 * DownFallStorage.screenHeight / 100, hudPaint);

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
        if (pY > (3* DownFallStorage.screenHeight/4) && (pX < distanceFromEdge || pY < distanceFromEdge || pX > DownFallStorage.screenWidth - distanceFromEdge || pY > DownFallStorage.screenHeight - distanceFromEdge)) {
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
                    if (pY > DownFallStorage.screenHeight/5 && pY < 4* DownFallStorage.screenHeight/5) {
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
        DownFallStorage.saveEverything();
    }
    public void resume() {
        playing = true;
    }
    public Level getCurrentLevel() {
        return levels.getCurrentLevel();
    }

}
