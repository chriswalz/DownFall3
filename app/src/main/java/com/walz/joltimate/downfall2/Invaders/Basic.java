package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Canvas;

public class Basic extends InvaderAbstract{

    public Basic(Context context, float xVal, float yVal, int width, int height) {

        this.width = width;
        this.height = height;

        this.x = xVal;
        this.y = yVal - height;
    }

    public void update(int fps) {
        y += yVelocity;

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
    public void draw(Canvas c) {
        c.drawRect(rect, invaderPaint);
    }
}
