package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

/**
 * Created by chris on 7/2/16.
 */
public abstract class InvaderAbstract {

    // The player ship will be represented by a Bitmap
    RectF rect;

    Random generator = new Random();

    // size
    float length;
    float height;

    // X is the far left of the rectangle which forms our paddle
    float x;

    // Y is the top coordinate
    float y;

    // This will hold the pixels per second speedthat the paddle will move
    float shipSpeed;

    boolean isVisible;

    public InvaderAbstract(Context context, int screenX, int screenY) {

        // Initialize a blank RectF
        rect = new RectF();
        isVisible = true;

//        x = (float) (Math.random() * screenX); //column * (length + padding);
 //       y = (float) -(Math.random() * screenY);//row * (length + padding/4);

        // How fast is the invader in pixels per second
        shipSpeed = 800;
    }
    public abstract void update(long fps);

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
