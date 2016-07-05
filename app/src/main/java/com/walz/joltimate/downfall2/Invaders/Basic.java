package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;

/**
 * Created by chris on 7/4/16.
 */
public class Basic extends InvaderAbstract{

    public Basic(Context context, int screenX, int screenY) {
        super(context, screenX, screenY);

        length = screenX;
        height = screenY/10;

        x = 0;
        y = -height;
        shipSpeed += 0;
    }

    public void update(long fps) {
        y += shipSpeed / fps;

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
}
