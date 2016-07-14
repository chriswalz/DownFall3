package com.walz.joltimate.downfall2;

import android.content.Context;

import com.walz.joltimate.downfall2.Invaders.Basic;
import com.walz.joltimate.downfall2.Invaders.BouncySprite;
import com.walz.joltimate.downfall2.Invaders.ClamperSprite;
import com.walz.joltimate.downfall2.Invaders.FireworkSprite;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;

/**
 * Created by chris on 7/4/16.
 */
public class Levels {
    // Current Level
    public static int currentLevel = 8;
    public static String startText = "";

    public static int numInvaders;
    public static double levelTimeLimit;

    public static int screenWidth, screenHeight;

    public static void init(int screenX, int screenY) {
        InvaderAbstract.baseSpeed = 1*screenY/120;
        screenWidth = screenX;
        screenHeight = screenY;
    }

    public static void prepareLevel(InvaderAbstract[] invaders, Context context) {
        switch (currentLevel) {
            case 0:
                Levels.avoidTheBlock(invaders, context);
                break;
            case 1:
                Levels.oneBlock(invaders, context);
                break;
            case 2:
                Levels.threeInvaders(invaders, context);
                break;
            case 3:
                Levels.threeInvadersClose(invaders, context);
                break;
            case 4:
                Levels.squareSquadBig(invaders, context);
                break;
            case 5:
                Levels.squareSquadMedium(invaders, context);
                break;
            case 6:
                Levels.oneClamper(invaders, context);
                break;
            case 7:
                Levels.oneBouncy(invaders, context);
                break;
            case 8:
                Levels.oneFirework(invaders, context);

        }
    }

    public static void avoidTheBlock(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 250; // convert to number of frames
        startText = "Drag to move!";

        int width = screenWidth/4;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, (screenWidth/2) - (width/2), 0, width, screenHeight/4);
        }
    }

    public static void oneBlock(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 250; // convert to number of frames
        startText = "Tap to jump!";
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, 0, 0, screenWidth, screenHeight/10);
        }
    }
    public static void threeInvaders(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 450;
        startText = "";
        int diff = 3*screenHeight/4;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, 0, -(diff * i), screenWidth, screenHeight/10);
        }
    }
    public static void threeInvadersClose(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 450;
        int diff = 2*screenHeight/3;
        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new Basic(context, 0, -(diff * i), screenWidth, screenHeight/10);
        }
    }
    public static void squareSquadBig(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 10;
        levelTimeLimit = 850;
        startText = "Checkerboard";

        double ratio = 1.1;

        int diff = (int) (screenHeight/ratio)*2;
        for(int i = 0; i < numInvaders/2; i++){
            invaders[i] = new Basic(context, 0, (int) -(diff * i), (int) (screenWidth/2), (int) (screenHeight/ratio));
        }
        for(int i = numInvaders/2; i < numInvaders; i++){
            invaders[i] = new Basic(context, screenWidth/2, (int) (-(diff * (i-numInvaders/2))-diff/2), (int) (screenWidth/2), (int) (screenHeight/ratio));
        }
    }
    public static void squareSquadMedium(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 10;
        levelTimeLimit = 750;
        startText = "Tap & Drag in one motion!";
        double ratio = 2.1;

        int diff = (int) (screenHeight/ratio)*2;
        for(int i = 0; i < numInvaders/2; i++){
            invaders[i] = new Basic(context, 0, -(diff * i), (int) (screenWidth/2), (int) (screenHeight/ratio));
        }
        for(int i = numInvaders/2; i < numInvaders; i++){
            invaders[i] = new Basic(context, screenWidth/2, (int) (-(diff * (i-numInvaders/2))-diff/2), (int) (screenWidth/2), (int) (screenHeight/ratio));
        }
    }
    public static void oneClamper(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 450;
        startText = "Crusher";

        invaders[0] = new ClamperSprite(context, 0, 0, 2 * Levels.screenHeight);
    }
    public static void oneBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375;
        startText = "Bouncy.";

        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new BouncySprite(context, 0, 0, Levels.screenWidth);
        }
    }
    public static void oneFirework(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375;
        startText = "Bouncy.";

        for(int i = 0; i < numInvaders; i++){
            invaders[i] = new FireworkSprite(context);
        }
    }
}
