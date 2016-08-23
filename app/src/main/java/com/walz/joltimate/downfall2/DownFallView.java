package com.walz.joltimate.downfall2;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.walz.joltimate.downfall2.game.DownFallGame;

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

// New objects
// blinkers, expanders, just x movement bouncers,

// Things to know before playing
// 1. Stay alive.
// 2. Survive by avoiding the red enemies
// 3. But you'll die a lot. And that's okay :)
// 4. Master your movement & Use your thumbs
// 5. Strategize your moves

// lookup game art design
// ASO
// increase number of positive reviews

// Beat level screen

// automatic death at start bug (if you died where a starting block is)
// crash

public class DownFallView extends SurfaceView implements Runnable{

    private Context context;

    // This is our thread
    private Thread gameThread = null;


    private DownFallActivity downFallActivity;
    private DownFallGame downFallGame;

    public DownFallView(Context context, int sizeX, int sizeY) {
        super(context);
        this.context = context;
        downFallActivity = (DownFallActivity) context;
        downFallGame = new DownFallGame(context, downFallActivity, this, getHolder(), sizeX, sizeY);
        downFallGame.prepareCurrentLevel();

    }



    @Override
    public void run() {
        downFallGame.run();
    }


    // If DownFallActivity is paused/stopped
    // shutdown our thread.
    public void pause() {
        downFallGame.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // If DownFallActivity is started then
    // start our thread.
    public void resume() {
        downFallGame.resume();
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.



    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        downFallGame.touchEvent(motionEvent);
        return true;
    }
    public DownFallGame getDownFallGame() {
        return downFallGame;
    }
}