package com.walz.joltimate.downfall2.Invaders;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.DownFallView;
import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class AcceleratorSprite extends OneRectAbstract {
    private boolean goDown = true;
    private int acceleration;
    private boolean bounce;

    public AcceleratorSprite(Context context, float xVal, float yVal, int width, boolean bounce) {

        this.width = width;
        this.height = Levels.screenHeight/40;

        this.acceleration = 1;

        this.x = xVal;
        this.y = yVal - height;

        this.bounce = bounce;
    }

    public void update(int fps) {
        if (goDown){
            y += yVelocity;
            if ( y > Levels.screenHeight/12 && DownFallView.numFrames % 2 == 0) {
                yVelocity += acceleration;
            }
            if(y > (Levels.screenHeight) ){
                goDown = false;
            }
        } else {
            if (bounce) {
                y -= yVelocity;
            } else {
                y += yVelocity;
            }
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
        return RectF.intersects(rect, playerShip.hitbox);
    }
}

