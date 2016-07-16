package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class RainSprite extends InvaderAbstract{
    private int length;
    private RectF[] rainSprites;
    private int[] xRand;
    private int[] yRand;
    private int[] ySpeedIncrement;

    private int rWidth;

    private int height;

    private int delay = 0;

    public RainSprite(Context context, int length, int delay) {

        this.height = 2*Levels.screenHeight;
        this.delay = delay;

        rWidth = Levels.screenWidth/20;

        this.x = 0;
        this.y = -(height + rWidth);
        this.length = length;

        rainSprites = new RectF[length];
        xRand = new int[length];
        yRand = new int[length];
        ySpeedIncrement = new int[length];


        float rX;
        float rY;
        for ( int i = 0; i < rainSprites.length; i++){
            xRand[i] = (int)((5*Math.random()*Levels.screenWidth/4)-Levels.screenWidth/8);
            yRand[i] = (int)(Math.random()*height);
            rX = this.x+ xRand[i];
            rY = this.y + yRand[i];
            rainSprites[i] = new RectF();
            rainSprites[i].top = rY;
            rainSprites[i].bottom = rY + rWidth;
            rainSprites[i].left = rX;
            rainSprites[i].right = rX + rWidth ;
        }

        for( int i = 0; i < rainSprites.length; i++){
            ySpeedIncrement[i] =  (int) (Math.random()*8); // determines speed of each rain box
        }
    }

    @Override
    public void update(int fps) {
        if (delay > 0) {
            delay--;
            return;
        }

        y += yVelocity/2;

        for ( int i = 0; i < rainSprites.length; i++){
            rainSprites[i].top = y + yRand[i];
            rainSprites[i].bottom = y + yRand[i] + rWidth;
            rainSprites[i].left = (x + xRand[i]);
            rainSprites[i].right = x + xRand[i] + rWidth;
        }
        for( int i = 0; i < rainSprites.length; i++){
            yRand[i] += ySpeedIncrement[i]; // adds 1 or 0, making some sprites faster
        }

    }
    @Override
    public void draw(Canvas c) {
        for (RectF r : rainSprites) {
            c.drawRect(r, invaderPaint);
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        for (RectF r : rainSprites) {
            if (RectF.intersects(r, playerShip.rect)) {
                return true;
            }
        }
        return false;
    }
}
