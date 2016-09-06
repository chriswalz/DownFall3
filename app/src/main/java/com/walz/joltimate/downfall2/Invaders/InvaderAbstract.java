package com.walz.joltimate.downfall2.Invaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.walz.joltimate.downfall2.game.PlayerShip;

/**
 * Created by chris on 7/2/16.
 */
public abstract class InvaderAbstract {

    // X is the far left of the rectangle which forms our paddle
    public float x;

    // Y is the top coordinate
    public float y;

    public static int BASE_SPEED;

    // This will hold the pixels per second speedthat the paddle will move
    public int baseSpeed;

    protected float yVelocity;

    public boolean isVisible;

    protected static Paint backgroundBlockPaint;
    protected static Paint invaderPaint;
    protected int i;


    static {
        //253, 234, 175
        backgroundBlockPaint = new Paint();
        backgroundBlockPaint.setColor(Color.argb(255, 253, 236, 178)); //Color.argb(255, 24, 24, 24));canvas.drawColor(Color.argb(255, 253, 234, 175))

        invaderPaint = new Paint();
        invaderPaint.setColor(Color.argb(255, 255, 41, 83) );//Color.argb(255, 203, 232, 107)); // rgb(255, 41, 83)
    //

    }

    public InvaderAbstract() {

        // Initialize a blank RectF
        baseSpeed = BASE_SPEED;
        isVisible = true;

//        x = (float) (Math.random() * screenWidth); //column * (clamperWidth + padding);
 //       y = (float) -(Math.random() * screenHeight);//row * (clamperWidth + padding/4);
        yVelocity = baseSpeed;


    }
    public abstract void update(int fps);
    public abstract void draw(Canvas c);
    public abstract boolean isColliding(PlayerShip playerShip);

}
