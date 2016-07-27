package com.walz.joltimate.downfall2.Invaders;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class BouncySprite extends OneRectAbstract {
    private boolean goDown = true;
    private boolean goRight = true;

    private int xVelocity;

    public BouncySprite(Context context, float xVal, float yVal, int width) {

        this.width = width;
        this.height = Levels.screenHeight/20;

        this.x = xVal;
        this.y = yVal - height;

        xVelocity = baseSpeed/2;
    }
    public BouncySprite(Context context, float xVal, float yVal, int width, int height) {

        this.width = width; //Levels.screenHeight/10;
        this.height = height; //Levels.screenHeight/10;

        this.x = xVal;
        this.y = yVal - height;

        xVelocity = baseSpeed/2;
    }

    public void update(int fps) {
        super.update(fps);
        if (goDown){
            y += yVelocity;
            if(y >= (Levels.screenHeight-height) ){
                goDown = false;
            }
        } else {
            y -= yVelocity;
        }
        if (width != Levels.screenWidth) {
            if (goRight){
                x += xVelocity;
                if(x >= Levels.screenWidth - width ){
                    goRight = false;
                }
            } else {
                x -= xVelocity;
                if(x < 0 ){
                    goRight = true;
                }
            }
        }


        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    public void draw(Canvas c) {
        super.draw(c);
        c.drawRect(rect, invaderPaint);
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.rect);
    }
}
