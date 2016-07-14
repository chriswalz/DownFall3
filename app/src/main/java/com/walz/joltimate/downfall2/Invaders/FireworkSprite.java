package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;

public class FireworkSprite extends InvaderAbstract{

    private int length = 40; // number must be a multiple of FOUR!!
    private RectF[] equalRects = new RectF[length];

    private int lenSep = length/4;

    private int[] xRand = new int[length];
    private int[] yRand = new int[length];

    private int[] xRandIncrement = new int[length];
    private int[] yRandIncrement = new int[length];


    private int widthR;
    private int heightR;

    private int explosionHeight = 0;

    public FireworkSprite(Context context) {


        this.x = Levels.screenWidth/2;
        this.y = 0;

        this.explosionHeight = (int) (Math.random() *(Levels.screenHeight/4 + (3 *Levels.screenHeight/ 4)) );

        for ( int i = 0; i < length/4; i++){
            xRandIncrement[i] = i;
            yRandIncrement[i] = length/4 - i;

            xRandIncrement[i+lenSep] = -i;
            yRandIncrement[i+lenSep] = length/4 - i;

            xRandIncrement[i+(2*lenSep)] = i;
            yRandIncrement[i+(2*lenSep)] = -(length/4 - i);

            xRandIncrement[i+(3*lenSep)] = -i;
            yRandIncrement[i+(3*lenSep)] = -(length/4 - i);

        }

        widthR = Levels.screenWidth / length;
        heightR = Levels.screenWidth / length;
        for ( int i = 0; i < equalRects.length; i++) {
            xRand[i] = (int) (width / 2 - widthR / 2);
            yRand[i] = 0;
            int xI = (int) this.x + xRand[i];
            int yI = (int) this.y + yRand[i];

            equalRects[i] = new RectF();

            equalRects[i].top = yI;
            equalRects[i].bottom = yI + heightR;
            equalRects[i].left = xI;
            equalRects[i].right = xI + widthR;
        }
    }

    public void update(int fps) {
        y += yVelocity;

        for ( int i = 0; i < equalRects.length; i++){
            int xI = (int) x + xRand[i];
            int yI = (int) y + yRand[i];
            equalRects[i].top = yI;
            equalRects[i].bottom = yI + heightR;
            equalRects[i].left = xI;
            equalRects[i].right = xI + widthR ;
        }

        if ( y > explosionHeight){
            // possibly add color

            for ( int i = 0; i < equalRects.length; i++){
                xRand[i] += xRandIncrement[i];
                yRand[i] += yRandIncrement[i];
                yVelocity = 0;
            }
        }

    }
    public void draw(Canvas c) {
        for(RectF r: equalRects){
            c.drawRect(r, invaderPaint);
        }
    }
}