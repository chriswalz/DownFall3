package com.walz.joltimate.downfall2.game;

import android.content.Context;

import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

/**
 * Created by chris on 7/31/16.
 */
public abstract class Level {
    private static double timeOffSetMultiplier = 1.50;

    public int numInvaders;
    public int levelTimeLimit;
    public String startText;
    public int difficultyRating;
    public Level(int numInvaders, int levelTimeLimit, String startText, int difficultyRating) {
        this.numInvaders = numInvaders;
        this.levelTimeLimit = (int) (levelTimeLimit * timeOffSetMultiplier);
        this.startText = startText;
        this.difficultyRating = difficultyRating;
    }
    public abstract void prepare(InvaderAbstract[] invaders, Context context);

}
