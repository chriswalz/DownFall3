package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class ClamperSprite extends OneRectAbstract{


    private int xVelocity;

    public static int clamperWidth;

    static {
        clamperWidth = Levels.screenHeight/20;
    }

    public ClamperSprite(Context context, double multiplier, float xVal, float yVal, int height) {

        //this.rect2 = new RectF();

        this.height = height;
        this.width = clamperWidth;

        this.x = xVal;
        this.y = yVal - height;

        this.xVelocity = (int) (baseSpeed * multiplier);

    }

    public void update(int fps) {
        super.update(fps);
        y += yVelocity;
        x += xVelocity;

        if (x > Levels.screenWidth - width || x < 0) {
            xVelocity = -xVelocity;
        }

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