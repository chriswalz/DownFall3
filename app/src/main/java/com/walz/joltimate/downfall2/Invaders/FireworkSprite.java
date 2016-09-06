package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.DownFallActivity;
import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

public class FireworkSprite extends MultRectAbstract{

    private DownFallActivity downFallActivity;

    private int lenSep;

    private float[] xRand;
    private float[] yRand;

    private float[] xRandIncrement;
    private float[] yRandIncrement;

    private boolean playSound = true;

    private int widthR;
    private int heightR;

    private int explosionHeight = 0;

    public FireworkSprite(Context context, int length, float x) {
        super(length);

        this.downFallActivity = (DownFallActivity) context;

        this.lenSep = length/4;

        widthR = DownFallStorage.screenWidth / 40;
        heightR = DownFallStorage.screenWidth / 40;

        this.x = x;
        this.y = -widthR;

        xRand = new float[length];
        yRand = new float[length];

        xRandIncrement = new float[length];
        yRandIncrement = new float[length];

        this.explosionHeight = (int) (Math.random() *(DownFallStorage.screenHeight/4 + (3 * DownFallStorage.screenHeight/ 4)) );

        float fireworkSpeedMultiplier = DownFallStorage.baseAcceleration;

        for (i = 0; i < length/4; i++){
            xRandIncrement[i] = i * fireworkSpeedMultiplier;
            yRandIncrement[i] = (length/4 - i) * fireworkSpeedMultiplier;

            xRandIncrement[i+lenSep] = -i * fireworkSpeedMultiplier;
            yRandIncrement[i+lenSep] = (length/4 - i) * fireworkSpeedMultiplier;

            xRandIncrement[i+(2*lenSep)] = i * fireworkSpeedMultiplier;
            yRandIncrement[i+(2*lenSep)] = -(length/4 - i) * fireworkSpeedMultiplier;

            xRandIncrement[i+(3*lenSep)] = -i * fireworkSpeedMultiplier;
            yRandIncrement[i+(3*lenSep)] = -(length/4 - i) * fireworkSpeedMultiplier;

        }


        for (i = 0; i < rects.length; i++) {
            xRand[i] = (int) (0 - widthR / 2);
            yRand[i] = 0;

            updateRectF(rects[i], this.x + xRand[i], this.y + yRand[i], widthR, heightR);
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
            updateRectF(rects[i], x + xRand[i], y + yRand[i], widthR, heightR);
        }

        if ( y > explosionHeight){
            if (playSound) {
                downFallActivity.playExplosionSound();
                playSound = false;
            }
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