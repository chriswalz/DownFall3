package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;

import com.walz.joltimate.downfall2.Levels;

public class BackgroundBlock extends InvaderAbstract{
    int xSpeed;

    public BackgroundBlock(Context context, float xVal, float yVal, int width, int height) {

        this.width = width;
        this.height = height;

        this.x = xVal;
        this.y = yVal - height;

        this.yVelocity = 2 + (int) (3 *Math.random());
        this.xSpeed = -2 + (int) (5 * Math.random());
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
}
