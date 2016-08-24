package com.walz.joltimate.downfall2.Invaders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.PlayerShip;

/**
 * Created by chris on 7/16/16.
 */
public abstract class OneRectAbstract extends InvaderAbstract {
    // The player ship will be represented by a RectF
    public RectF rect;
    public RectF backgroundRect;

    // size
    public float width;
    public float height;

    protected static Paint invaderPreviousPaint;

    static {
        invaderPreviousPaint = new Paint();
        invaderPreviousPaint.setColor(Color.argb(255, 200, 10, 20) );//Color.argb(255, 203, 232, 107)); // rgb(255, 41, 83)
    }

    public OneRectAbstract() {
        rect = new RectF();
        backgroundRect = new RectF();
    }

    public void update(int fps) {
        backgroundRect.top = y;
        backgroundRect.bottom = y + height;
        backgroundRect.left = x;
        backgroundRect.right = x + width;
    }
    public void draw(Canvas c) {
        if (rect.top > DownFallStorage.screenHeight || rect.bottom < 0 || rect.right > DownFallStorage.screenWidth || rect.left < 0) {

        } else {
            c.drawRect(backgroundRect, invaderPreviousPaint);
        }
    }
    public abstract boolean isColliding(PlayerShip playerShip);

}
