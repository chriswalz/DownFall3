package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;

import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

public class BackgroundBlock extends OneRectAbstract{
    private int xSpeed;

    public BackgroundBlock(Context context, float xVal, float yVal, int width, int height) {

        this.width = width;
        this.height = height;

        this.x = xVal;
        this.y = yVal - height;

        this.yVelocity = (int) (3 * Math.random()) - 1; //+ (int) (2 *Math.random());
        if (this.yVelocity == 0) {
            this.yVelocity = -1;
        }
        this.xSpeed = -1 + (int) (3 * Math.random());
    }

    public void update(int fps) {
        if (y > DownFallStorage.screenHeight) {
            y = -height;
        }
        if (y < -height) {
            y = DownFallStorage.screenHeight;
        }
        if (x > DownFallStorage.screenWidth) {
            x = -width;
        }
        if (x < -width) {
            x = DownFallStorage.screenWidth;
        }
        y += yVelocity;
        x += xSpeed;

        // Update hitbox which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    public void draw(Canvas c) {
        if (rect.top > DownFallStorage.screenHeight || rect.bottom < 0 || rect.right > DownFallStorage.screenWidth || rect.left < 0) {

        } else {
            c.drawRect(rect, backgroundBlockPaint);
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return false;
    }
}
