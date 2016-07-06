package com.walz.joltimate.downfall2;

import android.content.Context;

import com.walz.joltimate.downfall2.Invaders.Basic;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

/**
 * Created by chris on 7/4/16.
 */
public class Levels {
    public static int numInvaders;
    public static double levelTimeLimit;
    public static void oneBlock(InvaderAbstract[] invaders, Context context, int screenX, int screenY) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 100; // convert to number of frames
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, screenX, screenY);
        }
    }
    public static void tenInvaders(InvaderAbstract[] invaders, Context context, int screenX, int screenY) {
        // Build an army of invaders
        numInvaders = 15;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, screenX, screenY);
        }
    }
}
