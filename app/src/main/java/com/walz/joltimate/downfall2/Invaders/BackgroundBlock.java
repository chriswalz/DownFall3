package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;

import com.walz.joltimate.downfall2.Levels;

public class BackgroundBlock extends InvaderAbstract{

    public BackgroundBlock(Context context, float xVal, float yVal, int width, int height) {
        super(context, width, height);

        this.width = width;
        this.height = height;

        x = xVal;
        y = yVal - height;

        currentSpeed = 3 + (int) (4 *Math.random());
    }

    public void update(int fps) {
        if (y > Levels.screenHeight) {
            y = -height;
        }
        y += currentSpeed;

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + width;


    }
}
