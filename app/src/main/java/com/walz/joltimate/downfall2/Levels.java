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

// Change class to non-static
public class Levels {
    // Current Level
    private static boolean lastLevelOfSection = false;
    public static int currentSection = 0;
    public static int currentLevel = 0;
    private static int highestLevel;
    private static int highestSection;
    public static int difficultyRating;

    public static String startText = "";

    public static int numInvaders;
    public static int levelTimeLimit;
    public static int timeOffSet = 0;

    public static int screenWidth, screenHeight;

    private static boolean debug = true;

    private static SharedPreferences mPrefs;

    public static void init(Context context, int screenX, int screenY) {
        InvaderAbstract.baseSpeed = 1 * screenY / 110;
        screenWidth = screenX;
        screenHeight = screenY;

        if (!debug) {
            mPrefs = context.getSharedPreferences("com.google.downfall2", 0);
            currentSection = mPrefs.getInt("currentSection", 0);
            highestSection = mPrefs.getInt("highestSection", 0);
            currentLevel = mPrefs.getInt("currentLevel", 0);
            highestLevel = mPrefs.getInt("highestLevel", 0);
        }

    }

    public static void prepareLevel(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        lastLevelOfSection = false;
        switch (currentSection) {
            case 0:
                switch (currentLevel) {
                    case 0:
                        Levels.avoidThreeBlocks(invaders, context);
                        break;
                    case 1:
                        Levels.twoBlocks(invaders, context);
                        break;
                    case 2:
                        Levels.avoidThenJump(invaders, context);
                        break;
                    case 3:
                        Levels.threeInvaders(invaders, context);
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
                        Levels.eightClampers(invaders, context);
                        break;
                    case 3:
                        Levels.sixClampersAndFiveBlocks(invaders, context);
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
                        Levels.threeBouncy(invaders, context);
                        break;
                    case 2:
                        Levels.fourBouncy(invaders, context);
                        break;
                    case 3:
                        Levels.fourBouncyOneClamper(invaders, context);
                        break;
                    case 4:
                        Levels.fourBouncyTwoClamper(invaders, context);
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
                        Levels.threeFireworks(invaders, context);
                        break;
                    case 2:
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
                        break;
                    case 1:
                        Levels.threeBounceOneAccel(invaders, context);
                        break;
                    case 2:
                        Levels.twoBasicTwoAccelerator(invaders, context);
                        lastLevelOfSection = true;
                        break;
                }
                break;
            case 6:
                switch (currentLevel) {
                    case 0:
                        Levels.oneGravity(ship, invaders, context);
                        break;
                    case 1:
                        Levels.oneGravityTwoBouncy(ship, invaders, context);
                        break;
                    case 2:
                        Levels.threeGravity(ship, invaders, context);
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


        if (!debug) {
            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("currentSection", currentSection);
            mEditor.putInt("highestSection", highestSection);
            mEditor.putInt("currentLevel", currentLevel);
            mEditor.putInt("highestLevel", highestLevel);
            mEditor.commit();
        }
    }

    /*public static void avoidTheBlock(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 250 + timeOffSet; // convert to number of frames
        startText = "Drag to move!";

        int width = screenWidth / 4;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, (screenWidth / 2) - (width / 2), 0, width, screenHeight / 4);
        }
    } */
    public static void avoidThreeBlocks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 300 + timeOffSet; // convert to number of frames
        startText = "Drag to move!";
        difficultyRating = 1;

        int blockWidth = screenWidth / 3;
        invaders[0] = new Basic(context, 0, 0, blockWidth, Basic.basicHeight);
        invaders[1] = new Basic(context, 2*blockWidth, 0, blockWidth, screenHeight / 10);
        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), -3*screenHeight/4, screenWidth/4, screenHeight / 4);
        invaders[4] = new Basic(context, 0, -3*screenHeight/4, blockWidth, Basic.basicHeight);

    }

    public static void twoBlocks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 250 + timeOffSet; // convert to number of frames
        startText = "Tap to JUMP!";
        difficultyRating = 2;

        invaders[0] = new Basic(context, 0, 0, screenWidth, screenHeight / 10);
        invaders[1] = new Basic(context, 0, -screenHeight/2, screenWidth, screenHeight / 10);
    }

    public static void avoidThenJump(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 300 + timeOffSet;
        difficultyRating = 3;

        invaders[0] = new Basic(context, 0, 0, screenWidth, screenHeight / 10);
        invaders[1] = new Basic(context, 0, -screenHeight/2, screenWidth, screenHeight / 10);
        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), -3*screenHeight/4, screenWidth/4, screenHeight / 4);
        invaders[3] = new Basic(context, 0, -Levels.screenHeight, screenWidth, Basic.basicHeight);
    }

    public static void threeInvaders(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 5;
        levelTimeLimit = 450 + timeOffSet;
        difficultyRating = 3;

        int diff = 2 * screenHeight / 3;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i ), screenWidth, screenHeight / 10);
        }
        invaders[3] = new Basic(context, 0, -(diff * 3), screenWidth/3, screenHeight/2);
        invaders[4] = new Basic(context, 2*screenWidth/3, -(diff * 3), screenWidth/3, screenHeight/2);
    }
    /*public static void threeInvadersClosest(InvaderAbstract[] invaders, Context context) {
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
    } */

    public static void squareSquadMedium(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 8;
        levelTimeLimit = 750 + timeOffSet;
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
        numInvaders = 5;
        levelTimeLimit = 450 + timeOffSet;
        startText = "Zig Zag";
        difficultyRating = 4;

        invaders[0] = new ClamperSprite(context, 0, 0, 2 * Levels.screenHeight);
        for (int i = 1; i < numInvaders-1; i++) {
            invaders[i] = new Basic(context, 0, -(i-1) * Levels.screenHeight, Levels.screenWidth, Basic.basicHeight);

        }
        invaders[numInvaders-1] = new Basic(context, 0, -9 * Levels.screenHeight/4, Levels.screenWidth, Basic.basicHeight);
    }
    public static void twoClampers(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 450 + timeOffSet;
        startText = "The Crusher";
        difficultyRating = 4;

        invaders[0] = new ClamperSprite(context, 0, 0, 3 * Levels.screenHeight);
        invaders[1] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, 0, 3 * Levels.screenHeight);
        invaders[2] = new Basic(context, 0, -3 * Levels.screenHeight, Levels.screenWidth, Basic.basicHeight);
        invaders[3] = new Basic(context, 0, 0, Levels.screenWidth, Basic.basicHeight);
    }
    public static void eightClampers(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 8;
        levelTimeLimit = 600 + timeOffSet;
        startText = "";
        difficultyRating = 4;

        int rectHeight = 3*Levels.screenHeight/4;

        for (int i = 0; i < numInvaders/2; i+=2) {
            invaders[i] = new ClamperSprite(context, 0, -i * rectHeight, rectHeight);
        }
        for (int i = 1; i < numInvaders/2; i+=2) {
            invaders[i] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -i * rectHeight, rectHeight);
        }

    }
    public static void sixClampersAndFiveBlocks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 12;
        levelTimeLimit = 650 + timeOffSet;
        startText = "(.^.)";
        difficultyRating = 5;

        int rectHeight = 3*Levels.screenHeight/4;
        for (int i = 0; i < numInvaders/2; i+=2) {
            invaders[i] = new ClamperSprite(context, 0, -i * rectHeight, rectHeight);
        }
        for (int i = 1; i < numInvaders/2; i+=2) {
            invaders[i] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, -i * rectHeight, rectHeight);
        }
        for (int i = numInvaders/2; i < numInvaders; i++) {
            invaders[i] = new Basic(context, 0, -(i-numInvaders/2) * rectHeight, Levels.screenWidth, Basic.basicHeight);
        }

    }

    public static void oneBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375 + timeOffSet;
        startText = "Bouncy.";
        difficultyRating = 2;

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, 0, Levels.screenWidth);
        }
    }

    public static void threeBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 525 + timeOffSet;
        startText = "Bouncy.";
        difficultyRating = 4;

        int diff = 2*Levels.screenHeight/3;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
    }
    public static void fourBouncy(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 675 + timeOffSet;
        startText = "Bouncy.";
        difficultyRating = 5;

        int diff = 65*Levels.screenHeight/100;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
    }

    // bouncy, clamper, basic,
    public static void fourBouncyOneClamper(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 5;
        levelTimeLimit = 675 + timeOffSet;
        startText = "Bouncy.";
        difficultyRating = 8;

        int diff = 65*Levels.screenHeight/100;
        for (int i = 0; i < numInvaders-1; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
        invaders[numInvaders-1] = new ClamperSprite(context, 0, 0, 2 *screenHeight);
    }


    public static void fourBouncyTwoClamper(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 6;
        levelTimeLimit = 675 + timeOffSet;
        startText = "Bouncy.";
        difficultyRating = 9;

        int diff = 75*Levels.screenHeight/100;
        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
        }
        invaders[4] = new ClamperSprite(context, 0, 0, 2*Levels.screenHeight);
        invaders[5] = new ClamperSprite(context, Levels.screenWidth-ClamperSprite.width, 0, 2*Levels.screenHeight);
    }

    public static void oneFirework(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 375 + timeOffSet;
        startText = "It explodes";
        difficultyRating = 2;

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 40, Levels.screenWidth/2);
        }
    }
    public static void threeFireworks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 375 + timeOffSet;
        startText = "Fireworks!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
        }
    }
    public static void fourFireworks(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 375 + timeOffSet;
        startText = "Fireworks!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
        }
    }

    public static void oneRain(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 675 + timeOffSet;
        startText = "Let it rain!";

        for (int i = 0; i < numInvaders; i++) {
            invaders[i] = new RainSprite(context, 40, 0);
        }
    }

    public static void twoRain(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 2;
        levelTimeLimit = 1025 + timeOffSet;
        startText = "More rain";

        invaders[0] = new RainSprite(context, 40, 0);
        invaders[1] = new RainSprite(context, 40, (int) levelTimeLimit / 2);
    }

    public static void oneAccelerator(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 1;
        levelTimeLimit = 150 + timeOffSet;
        startText = "It accelerates.";

        invaders[0] = new AcceleratorSprite(context, 0, 0, Levels.screenWidth, false);
    }
    public static void twoBasicTwoAccelerator(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 450 + timeOffSet;
        startText = "Slow & Fast";

        invaders[0] = new Basic(context, 0, 0, Levels.screenWidth, Basic.basicHeight);
        invaders[1] = new Basic(context, 0, -Levels.screenHeight/2, Levels.screenWidth, Basic.basicHeight);
        invaders[2] = new AcceleratorSprite(context, 0, -3 * Levels.screenHeight/4, Levels.screenWidth, false);
        invaders[3] = new AcceleratorSprite(context, 0, -Levels.screenHeight/4, Levels.screenWidth, false);
    }

    public static void threeBounceOneAccel(InvaderAbstract[] invaders, Context context) {
        numInvaders = 3;
        levelTimeLimit = 550 + timeOffSet;
        startText = "";

        int diff = 4 * screenHeight / 10;
        for (int i = 0; i < numInvaders - 1; i++) {
            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
        }
        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -Levels.screenHeight / 2, Levels.screenWidth, false);
    }
    public static void oneGravity(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        numInvaders = 1;
        levelTimeLimit = 450 + timeOffSet;
        startText = "Gravity";

        invaders[0] = new GravitySprite(context, ship, 0, 0);
    }
    public static void oneGravityTwoBouncy(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 3;
        levelTimeLimit = 450 + timeOffSet;
        startText = "";

        invaders[0] = new GravitySprite(context, ship, (float) (Levels.screenWidth * Math.random()), 0);
        invaders[1] = new BouncySprite(context, 0, 0, Levels.screenWidth);
        invaders[2] = new BouncySprite(context, 0, -Levels.screenHeight/2, Levels.screenWidth);
    }
    public static void threeGravity(PlayerShip ship, InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 4;
        levelTimeLimit = 500 + timeOffSet;
        startText = "It accelerates.";

        invaders[0] = new GravitySprite(context, ship, 1*Levels.screenWidth/5, 6*Levels.screenHeight/7);
        invaders[1] = new GravitySprite(context, ship, 2*Levels.screenWidth/4, Levels.screenHeight/6);
        invaders[2] = new GravitySprite(context, ship, 4*Levels.screenWidth/5, 6*Levels.screenHeight/7);
        invaders[3] = new Basic(context, 0, 0, Levels.screenWidth, Levels.screenHeight/4);
    }


}
