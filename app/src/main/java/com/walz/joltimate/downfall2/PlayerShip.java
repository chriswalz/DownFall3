package com.walz.joltimate.downfall2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by chris on 7/2/16.
 */
public class PlayerShip {
    RectF rect;

    // The player ship will be represented by a Bitmap
    //public Bitmap bitmap;

    // How long and high our paddle will be
    private float width;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    public float x;

    // Y is the top coordinate
    public float y;

    public static Paint paint;

    static {
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }
    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public PlayerShip(Context context){

        // Initialize a blank RectF
        rect = new RectF();

        width = Levels.screenWidth/5;
        height = Levels.screenWidth/5;

        // Start ship in roughly the screen centre
        x = (Levels.screenWidth / 2) - width/2;
        y = 6*Levels.screenHeight / 9 ;

        // Initialize the bitmap
        /*bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width),
                (int) (height),
                false); */

    }
    public void draw(Canvas c) {
        c.drawRect(rect, paint);
    }
    public void setLocation(float x, float y) {
        this.x = x - width / 2;
        this.y = y - height / 2;

        rect.top = this.y;
        rect.bottom = this.y + height;
        rect.left = this.x;
        rect.right = this.x + width;
    }


}
