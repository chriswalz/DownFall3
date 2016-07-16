package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class ClamperSprite extends OneRectAbstract{
    private RectF rect2;
    private float x2;

    private int xVelocity;

    public ClamperSprite(Context context, float xVal, float yVal, int height) {

        this.rect2 = new RectF();

        this.width = Levels.screenHeight/20;
        this.height = height;

        this.x = xVal;
        this.y = yVal - height;

        this.x2 = xVal + Levels.screenWidth - width;

        this.xVelocity = Levels.screenWidth/130;

    }

    public void update(int fps) {
        y += yVelocity;
        x += xVelocity;

        if (x > Levels.screenWidth - width || x < 0) {
            xVelocity = -xVelocity;
        }

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;

        rect2.top = y;
        rect2.bottom = y + height;
        rect2.left = x2 - x; // offset by x position
        rect2.right = x2 - x + width;
    }
    public void draw(Canvas c) {
        c.drawRect(rect, invaderPaint);
        c.drawRect(rect2, invaderPaint);
    }
    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.rect) || RectF.intersects(rect2, playerShip.rect);
    }
}