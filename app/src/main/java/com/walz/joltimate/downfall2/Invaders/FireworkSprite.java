package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class FireworkSprite extends MultRectAbstract{


    private int lenSep;

    private int[] xRand;
    private int[] yRand;

    private int[] xRandIncrement;
    private int[] yRandIncrement;


    private int widthR;
    private int heightR;

    private int explosionHeight = 0;

    public FireworkSprite(Context context, int length, float x) {
        super(length);

        this.lenSep = length/4;

        widthR = Levels.screenWidth / 40;
        heightR = Levels.screenWidth / 40;

        this.x = x;
        this.y = -widthR;

        xRand = new int[length];
        yRand = new int[length];

        xRandIncrement = new int[length];
        yRandIncrement = new int[length];

        this.explosionHeight = (int) (Math.random() *(Levels.screenHeight/4 + (3 *Levels.screenHeight/ 4)) );



        for (i = 0; i < length/4; i++){
            xRandIncrement[i] = i;
            yRandIncrement[i] = length/4 - i;

            xRandIncrement[i+lenSep] = -i;
            yRandIncrement[i+lenSep] = length/4 - i;

            xRandIncrement[i+(2*lenSep)] = i;
            yRandIncrement[i+(2*lenSep)] = -(length/4 - i);

            xRandIncrement[i+(3*lenSep)] = -i;
            yRandIncrement[i+(3*lenSep)] = -(length/4 - i);

        }


        for (i = 0; i < rects.length; i++) {
            xRand[i] = (int) (0 - widthR / 2);
            yRand[i] = 0;
            int xI = (int) this.x + xRand[i];
            int yI = (int) this.y + yRand[i];

            updateRectF(rects[i], xI, yI, widthR, heightR);
        }
    }
    public FireworkSprite(Context context, int length, float x, float y) {
        this(context, length, x);
        this.y = y; //explodeY;
        //this.explosionHeight = (int) explodeY;
    }
    @Override
    public void update(int fps) {
        y += yVelocity;

        for (i = 0; i < rects.length; i++){
            int xI = (int) x + xRand[i];
            int yI = (int) y + yRand[i];
            updateRectF(rects[i], xI, yI, widthR, heightR);
        }

        if ( y > explosionHeight){
            // possibly add color

            for (i = 0; i < rects.length; i++){
                xRand[i] += xRandIncrement[i];
                yRand[i] += yRandIncrement[i];
                yVelocity = 0;
            }
        }

    }
    @Override
    public void draw(Canvas c) {
        for(RectF r: rects){
            c.drawRect(r, invaderPaint);
        }
    }
    @Override
    public boolean isColliding(PlayerShip playerShip) {
        for (RectF r: rects) {
            if (RectF.intersects(r, playerShip.hitbox)) {
                return true;
            }
        }
        return false;
    }
}