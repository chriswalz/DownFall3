package com.walz.joltimate.downfall2.Invaders;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.walz.joltimate.downfall2.PlayerShip;

/**
 * Created by chris on 7/16/16.
 */
public abstract class OneRectAbstract extends InvaderAbstract {
    // The player ship will be represented by a RectF
    public RectF rect;

    // size
    public float width;
    public float height;

    public OneRectAbstract() {
        super();
        rect = new RectF();
    }

    public abstract void update(int fps);
    public abstract void draw(Canvas c);
    public abstract boolean isColliding(PlayerShip playerShip);

}
