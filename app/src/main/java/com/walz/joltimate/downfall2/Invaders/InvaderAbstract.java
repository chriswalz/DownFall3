package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by chris on 7/2/16.
 */
public abstract class InvaderAbstract {

    // The player ship will be represented by a Bitmap
    RectF rect;

    private static Random generator = new Random();


    // size
    float width;
    float height;

    // X is the far left of the rectangle which forms our paddle
    float x;

    // Y is the top coordinate
    float y;

    // This will hold the pixels per second speedthat the paddle will move
    public static int baseSpeed;

    protected int currentSpeed;

    boolean isVisible;

    public InvaderAbstract(Context context, int screenX, int screenY) {

        // Initialize a blank RectF
        rect = new RectF();
        isVisible = true;

//        x = (float) (Math.random() * screenWidth); //column * (width + padding);
 //       y = (float) -(Math.random() * screenHeight);//row * (width + padding/4);
        currentSpeed = baseSpeed;


    }
    public abstract void update(int fps);

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
    public RectF getRect() {
        return rect;
    }
}
