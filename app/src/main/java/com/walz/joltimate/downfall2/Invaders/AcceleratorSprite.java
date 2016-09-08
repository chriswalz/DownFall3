package com.walz.joltimate.downfall2.Invaders;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;
import com.walz.joltimate.downfall2.game.DownFallGame;

public class AcceleratorSprite extends OneRectAbstract {
    private boolean goDown = true;
    private float acceleration;
    private boolean bounce;

    public AcceleratorSprite(Context context, float xVal, float yVal, int width, boolean bounce) {

        this.width = width;
        this.height = DownFallStorage.screenHeight/40;

        this.acceleration = DownFallStorage.baseAcceleration;

        this.x = xVal;
        this.y = yVal - height;

        this.bounce = bounce;
    }

    public void update(int fps) {
        if (goDown){
            y += yVelocity;
            if ( y > DownFallStorage.screenHeight/10) {
                yVelocity += acceleration;
            }
            if(y > (DownFallStorage.screenHeight) ){
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

