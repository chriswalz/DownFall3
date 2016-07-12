package com.walz.joltimate.downfall2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

/**
 * Created by chris on 7/2/16.
 */
public class PlayerShip {
    RectF rect;

    // The player ship will be represented by a Bitmap
    private Bitmap bitmap;

    // How long and high our paddle will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public PlayerShip(Context context){

        // Initialize a blank RectF
        rect = new RectF();

        length = Levels.screenWidth/6;
        height = Levels.screenHeight/6;

        // Start ship in roughly the screen centre
        x = Levels.screenWidth / 2;
        y = Levels.screenHeight - 20;

        // Initialize the bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);

    }
    public void setLocation(float x, float y) {
        this.x = x - length / 2;
        this.y = y - height / 2;

        rect.top = this.y;
        rect.bottom = this.y + height;
        rect.left = this.x;
        rect.right = this.x + length;
    }
    // This is a getter method to make the rectangle that
    // defines our paddle available in BreakoutView class
    public Bitmap getBitmap(){
        return bitmap;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public RectF getRect(){
        return rect;
    }


}
