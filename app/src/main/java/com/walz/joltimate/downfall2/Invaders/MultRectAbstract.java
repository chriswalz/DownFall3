package com.walz.joltimate.downfall2.Invaders;

import android.graphics.RectF;

/**
 * Created by chris on 7/17/16.
 */
public abstract class MultRectAbstract extends InvaderAbstract{
    protected RectF[] rects;

    public MultRectAbstract(int length){
        rects = new RectF[length];
        for (i = 0; i < rects.length; i++) {
            rects[i] = new RectF();
        }
    }
    protected void updateRectF(RectF r, float rX, float rY, float rWidth, float rHeight) {
        r.top = rY;
        r.bottom = rY + rWidth;
        r.left = rX;
        r.right = rX + rWidth ;
    }
}
