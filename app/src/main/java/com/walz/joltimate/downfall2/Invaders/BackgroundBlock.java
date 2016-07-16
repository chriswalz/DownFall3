package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class BackgroundBlock extends OneRectAbstract{
    int xSpeed;

    public BackgroundBlock(Context context, float xVal, float yVal, int width, int height) {

        this.width = width;
        this.height = height;

        this.x = xVal;
        this.y = yVal - height;

        this.yVelocity = 1; //+ (int) (2 *Math.random());
        this.xSpeed = -1 + (int) (3 * Math.random());
    }

    public void update(int fps) {
        if (y > Levels.screenHeight) {
            y = -height;
        }
        if (x > Levels.screenWidth) {
            x = -width;
        }
        if (x < -width) {
            x = Levels.screenWidth;
        }
        y += yVelocity;
        x += xSpeed;

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    public void draw(Canvas c) {
        c.drawRect(rect, backgroundBlockPaint);
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return false;
    }
}
