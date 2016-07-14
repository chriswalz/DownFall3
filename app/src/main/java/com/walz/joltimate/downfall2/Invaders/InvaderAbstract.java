package com.walz.joltimate.downfall2.Invaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by chris on 7/2/16.
 */
public abstract class InvaderAbstract {

    // The player ship will be represented by a RectF
    public RectF rect;

    // size
    public float width;
    public float height;

    // X is the far left of the rectangle which forms our paddle
    public float x;

    // Y is the top coordinate
    public float y;

    // This will hold the pixels per second speedthat the paddle will move
    public static int baseSpeed;

    protected int yVelocity;

    public boolean isVisible;

    protected static Paint backgroundBlockPaint;
    protected static Paint invaderPaint;

    static {
        backgroundBlockPaint = new Paint();
        backgroundBlockPaint.setColor(Color.argb(255, 24, 24, 24));

        invaderPaint = new Paint();
        invaderPaint.setColor(Color.argb(255, 203, 232, 107));
    }

    public InvaderAbstract() {

        // Initialize a blank RectF
        rect = new RectF();
        isVisible = true;

//        x = (float) (Math.random() * screenWidth); //column * (width + padding);
 //       y = (float) -(Math.random() * screenHeight);//row * (width + padding/4);
        yVelocity = baseSpeed;


    }
    public abstract void update(int fps);
    public abstract void draw(Canvas c);

}
