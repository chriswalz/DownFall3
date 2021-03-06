package com.walz.joltimate.downfall2.Invaders;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.DownFallActivity;
import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

public class BouncySprite extends OneRectAbstract {
    private boolean goDown = true;
    private boolean goRight = true;


    private DownFallActivity downFallActivity;

    private int xVelocity;

    public BouncySprite(Context context, float xVal, float yVal, int width) {
        this.downFallActivity = (DownFallActivity) context;

        this.width = width;
        this.height = DownFallStorage.screenHeight/20;

        this.x = xVal;
        this.y = yVal - height;

        xVelocity = baseSpeed/2;
    }
    public BouncySprite(Context context, float xVal, float yVal, int width, int height) {
        this.downFallActivity = (DownFallActivity) context;

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
            if(y >= (DownFallStorage.screenHeight-height) ){
                if (goDown == true) {
                    playBouncySound();
                }
                goDown = false;
            }
        } else {
            y -= yVelocity;
        }
        if (width != DownFallStorage.screenWidth) {
            if (goRight){
                x += xVelocity;
                if(x >= DownFallStorage.screenWidth - width ){
                    if (goRight == true) {
                        playBouncySound();
                    }
                    goRight = false;
                }
            } else {
                x -= xVelocity;
                if(x < 0 ){
                    if (goRight == false) {
                        playBouncySound();
                    }
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
        if (rect.top > DownFallStorage.screenHeight || rect.bottom < 0 || rect.right < 0 || rect.left > DownFallStorage.screenWidth) {

        } else {
            c.drawRect(rect, invaderPaint);
        }
    }

    private void playBouncySound() {
        if (rect.top > DownFallStorage.screenHeight || rect.bottom < 0 || rect.right < 0 || rect.left > DownFallStorage.screenWidth) {

        } else {
            downFallActivity.playBounceSound();
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.hitbox);
    }
}
