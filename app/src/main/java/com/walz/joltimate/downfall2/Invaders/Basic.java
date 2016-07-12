package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;

public class Basic extends InvaderAbstract{

    public Basic(Context context, float xVal, float yVal, int width, int height) {
        super(context, width, height);

        this.width = width;
        this.height = height;

        x = xVal;
        y = yVal - height;
        currentSpeed += 0;
    }

    public void update(int fps) {
        y += currentSpeed ;

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;
    }
}
