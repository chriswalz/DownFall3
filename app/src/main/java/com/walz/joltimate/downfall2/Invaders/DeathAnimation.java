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

    private int explosionHeight = 0;

    public DeathAnimation(Context context, int length, float x, float y) {
        super(length);

        this.lenSep = length/4;
        this.length = length;

        this.x = x;
        this.y = y;

        xRand = new int[length];
        yRand = new int[length];

        xRandIncrement = new int[length];
        yRandIncrement = new int[length];



        prepareAnimation();
    }
    int movement = 2;
    private void prepareAnimation() {
        this.explosionHeight = 0;
        for ( int i = 0; i < length/4; i++){
            xRandIncrement[i] = movement;
            yRandIncrement[i] = movement; //length/4 - i;

            xRandIncrement[i+lenSep] = -movement; //-i;
            yRandIncrement[i+lenSep] = movement; //length/4 - i;

            xRandIncrement[i+(2*lenSep)] = movement;  //i;
            yRandIncrement[i+(2*lenSep)] = -movement; //-(length/4 - i);

            xRandIncrement[i+(3*lenSep)] = -movement; // -i;
            yRandIncrement[i+(3*lenSep)] = -movement; //-(length/4 - i);

        }

        widthR = Levels.screenWidth / 40;
        heightR = Levels.screenWidth / 40;
        for (int i = 0; i < rects.length; i++) {
            int xI = (int) this.x + xRand[i];
            int yI = (int) this.y + yRand[i];

            updateRectF(rects[i], xI, yI, widthR, heightR);
        }
        int width = (int) Math.sqrt(rects.length);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                xRand[(i * width) + j] = i * widthR;
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
