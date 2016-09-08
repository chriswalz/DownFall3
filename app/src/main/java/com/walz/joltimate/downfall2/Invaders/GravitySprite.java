package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

public class GravitySprite extends OneRectAbstract{
    private int centerX;
    private int centerY;

    private int maxSpeed;
    private double acceleration;

    private double xVelocity;

    private PlayerShip ship;

    public static int SIZE = DownFallStorage.screenWidth/12;

    public GravitySprite(Context context, PlayerShip ship, float speedMultiplier, float xVal, float yVal) {

        this.width = SIZE;
        this.height = SIZE;

        this.ship = ship;

        this.x = xVal;
        this.y = yVal - height;

        maxSpeed = DownFallStorage.screenHeight/80;

        acceleration = speedMultiplier * DownFallStorage.baseAcceleration;

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
        if (rect.top > DownFallStorage.screenHeight || rect.bottom < 0 || rect.right < 0 || rect.left > DownFallStorage.screenWidth) {

        } else {
            c.drawRect(rect, invaderPaint);
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.hitbox);
    }
}
