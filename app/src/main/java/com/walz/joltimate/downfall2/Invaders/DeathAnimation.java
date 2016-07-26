package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class DeathAnimation extends MultRectAbstract{

    private int length;
    private int lenSep;

    private int[] xRand;
    private int[] yRand;

    private int[] xRandIncrement;
    private int[] yRandIncrement;


    private int widthR;
    private int heightR;

    private int shipWidth;

    private int explosionHeight = 0;

    public DeathAnimation(Context context, int length, float x, float y, int shipWidth) {
        super(length);

        this.lenSep = length/4;
        this.length = length;

        this.x = x;
        this.y = y;

        this.shipWidth = shipWidth;

        xRand = new int[length];
        yRand = new int[length];

        xRandIncrement = new int[length];
        yRandIncrement = new int[length];



        prepareAnimation();
    }
    int movement = baseSpeed * 3;
    private void prepareAnimation() {
        this.explosionHeight = 0;
        for ( int i = 0; i < length; i++){
            if (i % 4 == 0) {
                xRandIncrement[i] = (int) ((Math.random() * movement)) - movement/2;  // (int) ((Math.random() * movement));
                yRandIncrement[i] = (int) ((Math.random() * movement)) + movement/4;  //(int) ((Math.random() * movement) - 2);
            } else if (i % 4 == 1) {
                xRandIncrement[i] = (int) ((Math.random() * movement)) - movement/2;
                yRandIncrement[i] = (int) ((Math.random() * movement)) + movement/4;
            } else if (i % 4 == 2) {
                xRandIncrement[i] = (int) ((Math.random() * movement)) - movement/2;
                yRandIncrement[i] = (int) ((Math.random() * movement)) + movement/4;
            } else if (i % 4 == 3) {
                xRandIncrement[i] = (int) ((Math.random() * movement)) - movement/2;
                yRandIncrement[i] = (int) ((Math.random() * movement)) + movement/4;
            }


        }
        int width = (int) Math.sqrt(rects.length);
        widthR = shipWidth/width;  //Levels.screenWidth / 40;
        heightR = shipWidth/width;  //Levels.screenWidth / 40;
        for (int i = 0; i < rects.length; i++) {
            int xI = (int) this.x + xRand[i];
            int yI = (int) this.y + yRand[i];

            updateRectF(rects[i], xI, yI, widthR, heightR);
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                xRand[(i * width) + j] = i * (widthR);
                yRand[(i * width) + j] = j * widthR;
            }
        }
    }
    @Override
    public void update(int fps) {
        y += yVelocity;

        for (int i = 0; i < rects.length; i++){
            int xI = (int) x + xRand[i];
            int yI = (int) y + yRand[i];
            updateRectF(rects[i], xI, yI, widthR, heightR);
        }

        if ( y > explosionHeight){
            // possibly add color

            for (int i = 0; i < rects.length; i++){
                xRand[i] += xRandIncrement[i];
                yRand[i] += yRandIncrement[i];
                yVelocity = 0;
            }
        }

    }
    @Override
    public void draw(Canvas c) {
        for(RectF r: rects){
            c.drawRect(r, PlayerShip.paint);
        }
    }
    @Override
    public boolean isColliding(PlayerShip playerShip) {
        for (RectF r: rects) {
            if (RectF.intersects(r, playerShip.rect)) {
                return true;
            }
        }
        return false;
    }
    public void setOrigin(float x, float y) {
        this.x = x;
        this.y = y;
        this.explosionHeight = (int) y;
    }
    public void reset() {
        prepareAnimation();
    }
}
