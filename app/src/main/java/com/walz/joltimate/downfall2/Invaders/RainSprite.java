package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Level;
import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class RainSprite extends MultRectAbstract{
    private int[] xRand;
    private int[] yRand;
    private int[] ySpeedIncrement;

    private int rWidth;

    private int height;



    public static int HEIGHT = 2 * Levels.screenHeight;

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

        rWidth = Levels.screenWidth/20;

        this.x = 0;
        this.y = -(height + rWidth - startY);

        xRand = new int[length];
        yRand = new int[length];
        ySpeedIncrement = new int[length];


        float rX;
        float rY;
        for (int i = 0; i < rects.length; i++){
            xRand[i] = (int)((5*Math.random()*Levels.screenWidth/4)-Levels.screenWidth/8);
            yRand[i] = (int)(Math.random()*height);
            rX = this.x+ xRand[i];
            rY = this.y + yRand[i];

            updateRectF(rects[i], rX, rY, rWidth, rWidth);
        }

        for(int i = 0; i < rects.length; i++){
            ySpeedIncrement[i] =  (int) (Math.random()*8); // determines speed of each rain box
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
