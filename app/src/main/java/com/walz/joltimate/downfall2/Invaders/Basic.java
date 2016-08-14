package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;

public class Basic extends OneRectAbstract{

    public static int basicHeight;
    static {
        basicHeight = Levels.screenHeight / 10;
    }
    public Basic(Context context, float xVal, float yVal, int width) {
        super();
        this.width = width;
        this.height = basicHeight;

        this.x = xVal;
        this.y = yVal - height;
    }
    public Basic(Context context, float xVal, float yVal, int width, int height) {
        this(context, xVal, yVal, width);
        this.height = height;
        this.y = yVal - height;
    }
    public Basic(Context context, float xVal, float yVal, int width, int height, boolean reverse) {
        this(context, xVal, yVal, width, height);
        this.y = (-yVal) + Levels.screenHeight;
        if (reverse) {
            yVelocity = -yVelocity;
        }
    }


    @Override
    public void update(int fps) {
        super.update(fps);

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
        if (rect.top > Levels.screenHeight || rect.bottom < 0 || rect.right > Levels.screenWidth || rect.left < 0) {

        } else {
            c.drawRect(rect, invaderPaint);
        }
    }

    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return RectF.intersects(rect, playerShip.hitbox);
    }
}
