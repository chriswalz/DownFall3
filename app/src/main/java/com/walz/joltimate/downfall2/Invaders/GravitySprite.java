package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class GravitySprite extends OneRectAbstract{
    private int centerX;
    private int centerY;

    private int maxSpeed;
    private int acceleration = 1;

    private int xVelocity;

    private PlayerShip ship;

    public GravitySprite(Context context, PlayerShip ship, float xVal, float yVal) {

        this.width = Levels.screenWidth/12;
        this.height = Levels.screenWidth/12;

        this.ship = ship;

        this.x = xVal;
        this.y = yVal - height;

        maxSpeed = Levels.screenHeight/80;

        xVelocity = baseSpeed;
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }

    @Override
    public void update(int fps) {
        super.update(fps);

        centerX = (int) (x + width/2);
        centerY = (int) (y + height/2);

        if (ship.getCenterX() > centerX){
            if ( xVelocity < maxSpeed){
                xVelocity += acceleration;
            }
        }
        if (ship.getCenterX() < centerX){
            if ( xVelocity > -maxSpeed){
                xVelocity -= acceleration;
            }
        }
        if (ship.getCenterY() > centerY ){
            if ( yVelocity < maxSpeed){
                yVelocity += acceleration;
            }
        }
        if (ship.getCenterY() < centerY){
            if ( yVelocity > -maxSpeed){
                yVelocity -= acceleration;
            }
        }

        x += xVelocity;
        y += yVelocity;

        // Update hitbox which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    @Override
    public void draw(Canvas c) {
        super.draw(c);
        c.drawRect(rect, invaderPaint);
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.hitbox);
    }
}
