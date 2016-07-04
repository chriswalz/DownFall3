package com.walz.joltimate.downfall2;

import android.content.Context;

/**
 * Created by chris on 7/4/16.
 */
public class Levels {
    public static int numInvaders;
    public static void easy(Invader[] invaders, Context context, int screenX, int screenY) {
        // Build an army of invaders
        numInvaders = 5;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Invader(context, screenX, screenY);
        }
    }
    public static void tenInvaders(Invader[] invaders, Context context, int screenX, int screenY) {
        // Build an army of invaders
        numInvaders = 15;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Invader(context, screenX, screenY);
        }
    }
}
