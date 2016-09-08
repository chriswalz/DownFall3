package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

public class RainSprite extends MultRectAbstract{
    private float[] xRand;
    private float[] yRand;
    private float[] ySpeedIncrement;

    private int rWidth;

    private int height;



    public static int HEIGHT = 2 * DownFallStorage.screenHeight;

    public RainSprite(Context context, int length) {
        super(length);
        prepare(0, HEIGHT, length);
    }
    public RainSprite(Context context, int length, int height) {
        super(length);
        prepare(0, height, length);
    }
    public RainSprite(Context context, int startY, int length, int height) {
        super(length);
        prepare(startY, height, length);
    }
    private void prepare(int startY, int height, int length) {
        this.height = height;

        rWidth = DownFallStorage.screenWidth/20;

        this.x = 0;
        this.y = -(height + rWidth - startY);

        xRand = new float[length];
        yRand = new float[length];
        ySpeedIncrement = new float[length];


        float rX;
        float rY;
        for (int i = 0; i < rects.length; i++){
            xRand[i] = (float)((5*Math.random()* DownFallStorage.screenWidth/4)- DownFallStorage.screenWidth/8);
            yRand[i] = (float) (Math.random()*height);
            rX = this.x+ xRand[i];
            rY = this.y + yRand[i];

            updateRectF(rects[i], rX, rY, rWidth, rWidth);
        }

        for(int i = 0; i < rects.length; i++){
            ySpeedIncrement[i] =  (float) (Math.random()*DownFallStorage.screenHeight/240); // determines speed of each rain box
        }
    }

    @Override
    public void update(int fps) {

        y += yVelocity/2;

        for (int i = 0; i < rects.length; i++){
            updateRectF(rects[i], x + xRand[i], y+yRand[i], rWidth, rWidth);
        }
        if ( y > -(height + rWidth) ) {
            for(int i = 0; i < rects.length; i++){
                yRand[i] += ySpeedIncrement[i]; // adds 1 or 0, making some sprites faster
            }
        }

    }
    @Override
    public void draw(Canvas c) {
        for (RectF r : rects) {
            c.drawRect(r, invaderPaint);
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        for (RectF r : rects) {
            if (RectF.intersects(r, playerShip.hitbox)) {
                return true;
            }
        }
        return false;
    }
}
