package com.walz.joltimate.downfall2;

import android.content.Context;
import android.content.SharedPreferences;

import com.walz.joltimate.downfall2.Invaders.AcceleratorSprite;
import com.walz.joltimate.downfall2.Invaders.Basic;
import com.walz.joltimate.downfall2.Invaders.BouncySprite;
import com.walz.joltimate.downfall2.Invaders.ClamperSprite;
import com.walz.joltimate.downfall2.Invaders.FireworkSprite;
import com.walz.joltimate.downfall2.Invaders.GravitySprite;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.Invaders.RainSprite;

/**
 * Created by chris on 7/4/16.
 */
public class Levels {
    // Current Level
    private static boolean lastLevelOfSection = false;
    public static int currentSection;
    public static int currentLevel;
    private static int highestLevel;
    private static int highestSection;

    public static String startText = "";

    public static int numInvaders;
    public static double levelTimeLimit;

    public static int screenWidth, screenHeight;

    private static SharedPreferences mPrefs;

    public static void init(Context context, int screenX, int screenY) {
        InvaderAbstract.baseSpeed = 1 * screenY / 120;
        screenWidth = screenX;
        screenHeight = screenY;

        mPrefs = context.getSharedPreferences("com.google.downfall2", 0);
        currentSection = mPrefs.getInt("currentSection", 0);
        highestSection = mPrefs.getInt("highestSection", 0);
        currentLevel = mPrefs.getInt("currentLevel", 0);
        highestLevel = mPrefs.getInt("highestLevel", 0);

    }

    public static void prepareLevel(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        lastLevelOfSection = false;
        switch (currentSection) {
            case 0:
                switch (currentLevel) {
                    case 0:
                        Levels.avoidTheBlock(invaders, context);
                        break;
                    case 1:
                        Levels.avoidThreeBlocks(invaders, context);
                        break;
                    case 2:
                        Levels.oneBlock(invaders, context);
                        break;
                    case 3:
                        Levels.twoInvaders(invaders, context);
                        break;
                    case 4:
                        Levels.threeInvaders(invaders, context);
                        break;
                    case 5:
                        Levels.threeInvadersClosest(invaders, context);
                        break;
                    case 6:
                        Levels.squareSquadBig(invaders, context);
                        break;
                    case 7:
                        Levels.squareSquadMedium(invaders, context);
                        lastLevelOfSection = true;
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (currentLevel) {
                    case 0:
                        Levels.oneClamper(invaders, context);
                        break;
                    case 1:
                        Levels.twoClampers(invaders, context);
                        break;
                    case 2:
                        Levels.fourClampers(invaders, context);
                        break;
                    case 3:
                        Levels.fourClampersAndTwoBlocks(invaders, context);
                        lastLevelOfSection = true;
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (currentLevel) {
                    case 0:
                        Levels.oneBouncy(invaders, context);
                        break;
                    case 1:
                        Levels.twoBouncy(invaders, context);
                        break;
                    case 2:
                        Levels.threeBouncy(invaders, context);
                        break;
                    case 3:
                        Levels.fourBouncy(invaders, context);
                        lastLevelOfSection = true;
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (currentLevel) {
                    case 0:
                        Levels.oneFirework(invaders, context);
                        break;
                    case 1:
                        Levels.twoFireworks(invaders, context);
                        break;
                    case 2:
                        Levels.threeFireworks(invaders, context);
                        break;
                    case 3:
                        Levels.fourFireworks(invaders, context);
                        lastLevelOfSection = true;
                        break;
                }
                break;
            case 4:
                switch (currentLevel) {
                    case 0:
                        Levels.oneRain(invaders, context);
                        break;
                    case 1:
                        Levels.twoRain(invaders, context);
                        lastLevelOfSection = true;
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                switch (currentLevel) {
                    case 0:
                        Levels.oneAccelerator(invaders, context);
                        lastLevelOfSection = true;
                        break;
                    default:
                        Levels.oneAccelerator(invaders, context);
                        break;
                }
                break;
            case 6:
                switch (currentLevel) {
                    case 0:
                        Levels.oneGravity(ship, invaders, context);
                        lastLevelOfSection = true;
                        break;
                }
                break;
            default:
                break;

        }
    }

    public static void updateCurrentLevel() {
        if (lastLevelOfSection) {
            Levels.currentLevel = 0;
            Levels.currentSection++;
        } else {
            Levels.currentLevel++;
        }
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("currentSection", currentSection);
        mEditor.putInt("highestSection", highestSection);
        mEditor.putInt("currentLevel", currentLevel);
        mEditor.putInt("highestLevel", highestLevel);
        mEditor.commit();
    }

    public static void avoidTheBlock(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 250; // convert to number of frames
        startText = "Drag to move!";

        int width = screenWidth / 4;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, (screenWidth / 2) - (width / 2), 0, width, screenHeight / 4);
        }
    }
    public static void avoidThreeBlocks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 350; // convert to number of frames
        startText = "Drag to move!";

        int blockWidth = screenWidth / 3;
        invaders[0] = new Basic(context, 0, 0, blockWidth, screenHeight / 10);
        invaders[1] = new Basic(context, 2*blockWidth, 0, blockWidth, screenHeight / 10);
        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), -3*screenHeight/4, screenWidth/4, screenHeight / 4);

    }

    public static void oneBlock(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 250; // convert to number of frames
        startText = "Tap to JUMP!";
        invaders[0] = new Basic(context, 0, 0, screenWidth, screenHeight / 10);
    }

    public static void twoInvaders(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 450;
        int diff = 4 * screenHeight / 5;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i), screenWidth, screenHeight / 10);
        }
    }

    public static void threeInvaders(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 450;
        int diff = 4 * screenHeight / 5;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i), screenWidth, screenHeight / 10);
        }
    }
    public static void threeInvadersClosest(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 450;
        int diff = 2 * screenHeight / 3;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i), screenWidth, screenHeight / 10);
        }
    }

    public static void squareSquadBig(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 10;
        levelTimeLimit = 1150;
        startText = "Checkerboard";

        double ratio = 1.1;

        int diff = (int) (screenHeight / ratio) * 2;
        for (int i = 0; i < numInvaders / 2; i++) {
            invaders[i] = new Basic(context, 0, (int) -(diff * i), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
        for (int i = numInvaders / 2; i < numInvaders; i++) {
            invaders[i] = new Basic(context, screenWidth / 2, (int) (-(diff * (i - numInvaders / 2)) - diff / 2), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
    }

    public static void squareSquadMedium(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 10;
        levelTimeLimit = 850;
        startText = "Tap & Drag!";
        double ratio = 1.5;

        int diff = (int) (screenHeight / ratio) * 2;
        for (int i = 0; i < numInvaders / 2; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
        for (int i = numInvaders / 2; i < numInvaders; i++) {
            invaders[i] = new Basic(context, screenWidth / 2, (int) (-(diff * (i - numInvaders / 2)) - diff / 2), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
    }

    public static void oneClamper(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 450;
        startText = "Zig Zag";

        invaders[0] = new ClamperSprite(context, 0, 0, 2 * Levels.screenHeight);
    }
    public static void twoClampers(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 450;
        startText = "The Crusher";

        invaders[0] = new ClamperSprite(context, 0, 0, 2 * Levels.screenHeight);
        invaders[1] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, 0, 2 * Levels.screenHeight);
    }
    public static void fourClampers(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 500;
        startText = "The Crusher";

        int rectHeight = 3*Levels.screenHeight/4;

        invaders[0] = new ClamperSprite(context, 0, 0, rectHeight);
        invaders[1] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -rectHeight, rectHeight);
        invaders[2] = new ClamperSprite(context, 0, -2*rectHeight, rectHeight);
        invaders[3] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -3*rectHeight, rectHeight);
    }
    public static void fourClampersAndTwoBlocks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 6;
        levelTimeLimit = 550;
        startText = "(.^.)";

        int rectHeight = 3*Levels.screenHeight/4;

        invaders[0] = new ClamperSprite(context, 0, 0, rectHeight);
        invaders[1] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -rectHeight, rectHeight);
        invaders[2] = new ClamperSprite(context, 0, -2*rectHeight, rectHeight);
        invaders[3] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -3*rectHeight, rectHeight);
        invaders[4] = new Basic(context, 0, 0, Levels.screenWidth, Basic.basicHeight);
        invaders[5] = new Basic(context, 0, -4*rectHeight, Levels.screenWidth, Basic.basicHeight);
    }

    public static void oneBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375;
        startText = "Bouncy.";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, 0, Levels.screenWidth);
        }
    }
    public static void twoBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 475;
        startText = "Bouncy.";

        int diff = 3 * Levels.screenHeight;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
    }
    public static void threeBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 525;
        startText = "Bouncy.";

        int diff = 3*Levels.screenHeight/4;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
    }
    public static void fourBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 675;
        startText = "Bouncy.";

        int diff = 3*Levels.screenHeight/4;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
    }

    public static void oneFirework(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375;
        startText = "It explodes";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 40, Levels.screenWidth/2);
        }
    }
    public static void twoFireworks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 375;
        startText = "They explode";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
        }
    }
    public static void threeFireworks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 375;
        startText = "Fireworks!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
        }
    }
    public static void fourFireworks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 375;
        startText = "Fireworks!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
        }
    }

    public static void oneRain(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 675;
        startText = "Let it rain!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new RainSprite(context, 40, 0);
        }
    }

    public static void twoRain(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 875;
        startText = "Let it rain!";

        invaders[0] = new RainSprite(context, 40, 0);
        invaders[1] = new RainSprite(context, 40, (int) levelTimeLimit / 2);
    }

    public static void oneAccelerator(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 150;
        startText = "It accelerates.";

        invaders[0] = new AcceleratorSprite(context, 0, 0, Levels.screenWidth, false);
    }

    public static void threeBounceOneAccel(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 550;
        startText = "";

        int diff = 4 * screenHeight / 10;
        for (int i = 0; i < numInvaders - 1; i++) {
            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
        }
        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -Levels.screenHeight / 2, Levels.screenWidth, false);
    }
    public static void oneGravity(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 350;
        startText = "It accelerates.";

        invaders[0] = new GravitySprite(context, ship, 0, 0);
    }


}
