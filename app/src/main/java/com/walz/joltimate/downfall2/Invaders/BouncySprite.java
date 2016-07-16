package com.walz.joltimate.downfall2.Invaders;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class BouncySprite extends OneRectAbstract {
    private boolean goDown = true;

    public BouncySprite(Context context, float xVal, float yVal, int width) {

        this.width = width;
        this.height = Levels.screenHeight/20;

        this.x = xVal;
        this.y = yVal - height;
    }

    public void update(int fps) {
        if (goDown){
            y += yVelocity;
            if(y > (Levels.screenHeight) ){
                goDown = false;
            }
        } else {
            y -= yVelocity;
        }


        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    public void draw(Canvas c) {
        c.drawRect(rect, invaderPaint);
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.rect);
    }
}
