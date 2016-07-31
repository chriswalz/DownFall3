package com.walz.joltimate.downfall2;

import android.content.Context;

import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

/**
 * Created by chris on 7/31/16.
 */
public abstract class Level {
    public Level() {
    }
    public abstract void prepare(InvaderAbstract[] invaders, Context context);

}
