package com.walz.joltimate.downfall2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by chris on 7/2/16.
 */
public class PlayerShip {
    public RectF rect;

    // The player ship will be represented by a Bitmap
    //public Bitmap bitmap;

    // How long and high our paddle will be
    private float width;
    private float height;

    private int curve = 100;
    private boolean increase = false;

    // X is the far left of the rectangle which forms our paddle
    public float x;

    // Y is the top coordinate
    public float y;

    public static Paint paint;

    private final static int frameLen = 3;
    public static Paint[] framePaints = new Paint[frameLen];
    private static RectF[] previousFrames = new RectF[frameLen];

    static {
        paint = new Paint();
        paint.setColor(Color.argb(255,23, 144, 245 )); //rgb(23, 144, 244)
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);


        int diff = 50;
        for (int i = 0; i < frameLen; i++) {
            framePaints[i] = new Paint();
            framePaints[i].setColor(Color.argb(255, (frameLen-1-i)*diff, (frameLen-1-i)*diff, 245));
            framePaints[i].setAntiAlias(true);
        }
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

        rect.top = this.y;
        rect.bottom = this.y + height;
        rect.left = this.x;
        rect.right = this.x + width;

        for (int i = 0; i < previousFrames.length; i++) {
            previousFrames[i] = new RectF();
        }

    }
    public void reset() {
        // Start ship in roughly the screen centre
        x = (Levels.screenWidth / 2) - width/2;
        y = 6*Levels.screenHeight / 9 ;

        rect.top = this.y;
        rect.bottom = this.y + height;
        rect.left = this.x;
        rect.right = this.x + width;


    }
    private int dif = 3;
    public void draw(Canvas c) {
        //c.drawRect(rect, paint);

        for (int i = 0; i < previousFrames.length-1; i++) {
            RectF next = previousFrames[i+1];
            previousFrames[i].top = next.top+dif;
            previousFrames[i].bottom = next.bottom+dif;
            previousFrames[i].left = next.left+dif;
            previousFrames[i].right = next.right-dif;
        }
        RectF newestDelayFrame = previousFrames[previousFrames.length-1];
        newestDelayFrame.top = this.y;
        newestDelayFrame.bottom = this.y + height;
        newestDelayFrame.left = this.x;
        newestDelayFrame.right = this.x + width;

        if (curve > 1*width/3) {
            increase = false;
        }
        if (curve <= width/10 ) {
            increase = true;
        }
        if (increase) {
            curve += 1;
        } else {
            curve -= 1;
        }
        int ratio = 4;
        int i = 0;
        for (RectF r: previousFrames) {
            c.drawRoundRect(r, curve/ratio, curve, framePaints[i]);
            i++;
        }
        c.drawRoundRect(rect, curve/ratio, curve, paint);
        //c.drawCircle(x + width/2, y + width/2, width/4, paint2);
    }
    public void setLocation(float x, float y, int gameState) {
        if (gameState != DownFallView.PLAYINGSCREEN) {
            return;
        }

        this.x = x - width / 2;
        this.y = y - height / 2;

        rect.top = this.y;
        rect.bottom = this.y + height;
        rect.left = this.x;
        rect.right = this.x + width;
    }
    public float getCenterX() {
        return x + width/2;
    }
    public float getCenterY() {
        return y + height/2;
    }



}
