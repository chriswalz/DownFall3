package com.walz.joltimate.downfall2;

import android.content.Context;
import android.content.SharedPreferences;

import com.walz.joltimate.downfall2.Invaders.AcceleratorSprite;
import com.walz.joltimate.downfall2.Invaders.Basic;
import com.walz.joltimate.downfall2.Invaders.BitmapSprite;
import com.walz.joltimate.downfall2.Invaders.BouncySprite;
import com.walz.joltimate.downfall2.Invaders.ClamperSprite;
import com.walz.joltimate.downfall2.Invaders.FireworkSprite;
import com.walz.joltimate.downfall2.Invaders.GravitySprite;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.Invaders.RainSprite;

// Change class to non-static
public class Levels {
    // Current Level
    public static int score;
    public static int currentLevel = 2;
    public static int highestLevel=40;
    public static int numberAttempts;
    public static int difficultyRating;
    public static int requestAdAmount = 0;

    public static String startText = "";

    public static int numInvaders;
    public static int levelTimeLimit;
    public static double timeOffSetMultiplier = 1.55;

    public static int screenWidth, screenHeight;

    public static boolean debug = false;

    private static SharedPreferences mPrefs;

    public static Level[] levels;

    private static String packageNameStr;
    private static String currentLevelStr;
    private static String highestLevelStr;
    private static String scoreStr;
    private static String numberAttemptsStr;
    private static String requestAdAmountStr;

    private static int i;

    public static void init(Context context, int screenX, int screenY) {
        InvaderAbstract.BASE_SPEED = 1 * screenY / 160;
        screenWidth = screenX;
        screenHeight = screenY;

        levelTimeLimit = 500; // arbitrary # to fix divide by 0 error

        if (!debug) {
            packageNameStr = context.getString(R.string.package_name);
            currentLevelStr = context.getString(R.string.current_level);
            highestLevelStr = context.getString(R.string.highest_level);
            scoreStr = context.getString(R.string.score);
            numberAttemptsStr = context.getString(R.string.number_attempts);
            requestAdAmountStr = context.getString(R.string.request_ad_amount);

            mPrefs = context.getSharedPreferences(packageNameStr, 0);
            currentLevel = mPrefs.getInt(currentLevelStr, 0);
            highestLevel = mPrefs.getInt(highestLevelStr, 0);
            numberAttempts = mPrefs.getInt(numberAttemptsStr, 0);
            requestAdAmount = mPrefs.getInt(requestAdAmountStr, 0);
            score = mPrefs.getInt(scoreStr, 0);
        }

        levels = new Level[]{
                // CHAPTER 1, youse a basic bitch
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (300 * timeOffSetMultiplier); // convert to number of frames
                        startText = "Drag to move!";
                        difficultyRating = 1;

                        int blockWidth = screenWidth / 3;
                        int offset = -1*screenWidth/3;
                        invaders[0] = new Basic(context, 0, offset, blockWidth);
                        invaders[1] = new Basic(context, 2*blockWidth, offset, blockWidth);
                        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), (-3*screenHeight/4) + offset, screenWidth/4, screenHeight / 4);
                        invaders[3] = new Basic(context, 0, (-7*Levels.screenHeight/5) + offset, blockWidth);
                        invaders[4] = new Basic(context, 2*blockWidth, (-7*Levels.screenHeight/5) + offset, blockWidth);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4; // one is a overlay
                        levelTimeLimit = (int) (250 * timeOffSetMultiplier); // convert to number of frames
                        startText = "Tap above the block!";
                        difficultyRating = 2;

                        int diff = 4*screenHeight/9;
                        invaders[0] = new Basic(context, 0, (-1*diff), screenWidth, screenHeight / 10);
                        invaders[1] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, (-2*diff)+(diff/4), Levels.screenWidth/7, Levels.screenWidth/7);
                        invaders[2] = new Basic(context, 0, (-3*diff), screenWidth, screenHeight / 10);
                        invaders[3] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, (-4*diff)+(diff/4), Levels.screenWidth/7, Levels.screenWidth/7);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 2; // one is a overlay
                        levelTimeLimit = (int) (250 * timeOffSetMultiplier); // convert to number of frames
                        startText = "Watch out below!";
                        difficultyRating = 2;

                        int diff = 4*screenHeight/9;
                        invaders[0] = new Basic(context, 0, -1*diff, screenWidth, screenHeight / 10, true);
                       // invaders[1] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, -2*diff, Levels.screenWidth/7, Levels.screenWidth/7);
                        invaders[1] = new Basic(context, 0, -3*diff, screenWidth, screenHeight / 10, true);
                        //invaders[3] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, -4*diff, Levels.screenWidth/7, Levels.screenWidth/7);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4;
                        levelTimeLimit = (int) (300 * timeOffSetMultiplier);
                        difficultyRating = 3;
                        startText = "Tap & Drag, Tap & Drag, Tap & Drag";

                        int diff = 3*screenHeight/9;
                        invaders[0] = new Basic(context, 0, -1*diff, screenWidth, screenHeight / 10);
                        invaders[1] = new Basic(context, 0, -3*diff, screenWidth, screenHeight / 10);
                        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), -6*screenHeight/4, screenWidth/4, screenHeight / 4);
                        invaders[3] = new Basic(context, 0, -7*screenHeight/4, screenWidth, 3*Basic.basicHeight);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 7;
                        levelTimeLimit = (int) (425 * timeOffSetMultiplier);
                        difficultyRating = 3;
                        startText = "Stay near the bottom or else";

                        int diff = 3 * screenHeight / 6;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(diff * i ), screenWidth, screenHeight / 10);
                        }
                        invaders[3] = new Basic(context, 0, -(diff * 3), screenWidth/3, screenHeight/2);
                        invaders[4] = new Basic(context, 2*screenWidth/3, -(diff * 3), screenWidth/3, screenHeight/2);
                        invaders[5] = new Basic(context, 0, -(diff*3), screenWidth);
                        invaders[6] = new Basic(context, 0, -(diff*3 + screenHeight/2), screenWidth);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        difficultyRating = 5;
                        startText = "Large";

                        int diff = 5 * screenHeight / 12;
                        for (int i = 0; i < numInvaders - 2 ; i++) {
                            invaders[i] = new Basic(context, 0, -(14 * diff * i /  6 ), screenWidth, diff);
                        }
                    }
                },
                // CHAPTER 2, Clamper
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        startText = "Chapter 2:\n Things get shifty";
                        difficultyRating = 4;

                        invaders[0] = new ClamperSprite(context, 0.6, 0, 0, 2 * Levels.screenHeight);
                        for (int i = 1; i < numInvaders-1; i++) {
                            invaders[i] = new Basic(context, 0, -(i-1) * Levels.screenHeight, Levels.screenWidth, Basic.basicHeight);

                        }
                        invaders[numInvaders-1] = new Basic(context, 0, -9 * Levels.screenHeight/4, Levels.screenWidth, Basic.basicHeight);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        startText = "The Crusher";
                        difficultyRating = 4;

                        invaders[0] = new ClamperSprite(context, 0.6, 0, 0, 3 * Levels.screenHeight);
                        invaders[1] = new ClamperSprite(context, 0.6, Levels.screenWidth-ClamperSprite.clamperWidth, 0, 3 * Levels.screenHeight);
                        invaders[2] = new Basic(context, 0, -3 * Levels.screenHeight, Levels.screenWidth, Basic.basicHeight);
                        invaders[3] = new Basic(context, 0, 0, Levels.screenWidth, Basic.basicHeight);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 9;
                        levelTimeLimit = (int) (600 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 4;

                        int rectHeight = 3*Levels.screenHeight/4;

                        for (int i = 0; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 1.4, 0, -i * rectHeight, rectHeight);
                        }
                        for (int i = 1; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 0.7, Levels.screenWidth-ClamperSprite.clamperWidth, -i * rectHeight, rectHeight);
                        }
                        invaders[numInvaders-1] = new Basic(context, 0, -(numInvaders-1)*rectHeight/2, screenWidth, screenHeight/2);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 13;
                        levelTimeLimit = (int) (650 * timeOffSetMultiplier);
                        startText = "(.^.)";
                        difficultyRating = 5;

                        int rectHeight = 3*Levels.screenHeight/4;
                        for (int i = 0; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 1.0, 0, -i * rectHeight, rectHeight);
                        }
                        for (int i = 1; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 1.0, Levels.screenWidth-ClamperSprite.clamperWidth, -i * rectHeight, rectHeight);
                        }
                        for (int i = numInvaders/2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(i-numInvaders/2) * rectHeight, Levels.screenWidth, Basic.basicHeight);
                        }
                    }
                },
                // Chapter 3, BOUNCY ------------------------
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 1;
                        levelTimeLimit = (int) (325 * timeOffSetMultiplier);
                        startText = "Bouncy.";
                        difficultyRating = 2;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, 0, Levels.screenWidth);
                        }
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Three bouncy
                        numInvaders = 3;
                        levelTimeLimit = (int) (475 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 4;

                        int diff = 2*Levels.screenHeight/3;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Four bouncy
                        numInvaders = 4;
                        levelTimeLimit = (int) (525 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 5;

                        int diff = 65*Levels.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Clamper plus bouncies
                        numInvaders = 5;
                        levelTimeLimit = (int) (500 * timeOffSetMultiplier);
                        startText = "-|-";
                        difficultyRating = 8;

                        int diff = 65*Levels.screenHeight/100;
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                        invaders[numInvaders-1] = new ClamperSprite(context, 0.2, 0, 0, 2 *screenHeight);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Snake status
                        numInvaders = 48;
                        levelTimeLimit = (int) (525 * timeOffSetMultiplier);
                        startText = "Snake";
                        difficultyRating = 5;

                        int add = (int)(Math.random()*screenWidth);

                        int ratio = 2;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, (i * Levels.screenWidth/(numInvaders/ratio)) % screenWidth, -i * Levels.screenHeight/(numInvaders/ratio), 1*Levels.screenWidth/4, Levels.screenHeight/(numInvaders/ratio));
                        }
                    }
                },
                // Chapter 4, Fireworks! -------------------------
                new Level() {
                    // Firework status
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 2;
                        levelTimeLimit = (int) (325 * timeOffSetMultiplier);
                        startText = "It explodes";
                        difficultyRating = 2;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 40, Levels.screenWidth/2);
                        }
                    }
                },
                new Level() {
                    // Three fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4;
                        levelTimeLimit = (int) (350 * timeOffSetMultiplier);
                        startText = "Fireworks!";
                        difficultyRating = 4;

                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
                        }
                        invaders[numInvaders-1] = new BouncySprite(context, 0, 0, Levels.screenWidth);
                    }
                },
                new Level() {
                    // Four fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4;
                        levelTimeLimit = (int) (375 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 6;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 36, (i * Levels.screenWidth/(numInvaders+1)) + Levels.screenWidth/(numInvaders+1));
                        }
                    }
                },
                new Level() {
                    // Four fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 8;
                        levelTimeLimit = (int) (625 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 6;

                        int diff = 4*Levels.screenHeight/9;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 20, (int)(3*Math.random()*Levels.screenWidth/4) + Levels.screenWidth/8, -i * diff);
                        }
                    }
                },
                // Chapter 5, Let it rain! ----------
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 1;
                        levelTimeLimit = (int) (625 * timeOffSetMultiplier);
                        startText = "Let it rain!";
                        difficultyRating = 3;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new RainSprite(context, 40);
                        }
                    }
                },
                new Level() {
                    // Double the rain
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 2;
                        levelTimeLimit = (int) (750 * timeOffSetMultiplier);
                        startText = "More rain";
                        difficultyRating = 5;

                        invaders[0] = new RainSprite(context, 24);
                        invaders[1] = new RainSprite(context, 24);
                    }
                },
                new Level() {
                    // Double the rain
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (650 * timeOffSetMultiplier);
                        startText = "Rain and Blocks";
                        difficultyRating = 5;

                        invaders[0] = new RainSprite(context, 24);
                        invaders[1] = new RainSprite(context, 24);
                        invaders[2] = new Basic(context, 0, 0, Levels.screenWidth);
                        invaders[3] = new Basic(context, 0, -RainSprite.HEIGHT, Levels.screenWidth);
                        invaders[4] = new Basic(context, 0, -2*RainSprite.HEIGHT, Levels.screenWidth, 2 * Basic.basicHeight);
                    }
                },
                // Chapter 7, Accelerator
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 1;
                        levelTimeLimit = (int) (150 * timeOffSetMultiplier);
                        startText = "It accelerates.";
                        difficultyRating = 2;

                        invaders[0] = new AcceleratorSprite(context, 0, 0, Levels.screenWidth, false);
                    }
                },
                new Level() {
                    // 2 basics, 3 accelerators
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (300 * timeOffSetMultiplier);
                        startText = "Slow & Fast";
                        difficultyRating = 5;

                        invaders[0] = new Basic(context, 0, 0, Levels.screenWidth, Basic.basicHeight);
                        invaders[1] = new Basic(context, 0, -Levels.screenHeight/2, Levels.screenWidth, Basic.basicHeight);
                        invaders[3] = new AcceleratorSprite(context, 0, -Levels.screenHeight/4, Levels.screenWidth, false);
                        invaders[2] = new AcceleratorSprite(context, 0, -3 * Levels.screenHeight/4, Levels.screenWidth, false);
                        invaders[4] = new AcceleratorSprite(context, 0, -5*Levels.screenHeight/4, Levels.screenWidth, false);
                    }
                },
                new Level() {
                    // Three bounce 1 accelerator
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 6;
                        levelTimeLimit = (int) (300 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 5;

                        int diff = 4 * screenHeight / 10;
                        for (int i = 0; i < numInvaders - 2; i++) {
                            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
                        }
                        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -Levels.screenHeight / 2, Levels.screenWidth, false);
                        invaders[numInvaders - 2] = new RainSprite(context, 12, Levels.screenHeight/4);
                    }
                },
                new Level() {
                    // Three bounce 1 accelerator
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 5;
                        levelTimeLimit = (int) (575 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 5;

                        invaders[0] = new Basic(context, 0, -Levels.screenHeight, Levels.screenWidth);
                        invaders[1] = new ClamperSprite(context, 0.6, 0, 0, 2*Levels.screenHeight);
                        invaders[2] = new BouncySprite(context, 0, 0, Levels.screenWidth);
                        invaders[3] = new RainSprite(context, 12, Levels.screenHeight/4);
                        invaders[4] = new AcceleratorSprite(context, 0, -8*Levels.screenHeight/4, Levels.screenWidth, false);
                    }
                },
                // Chapter 8, Gravity Sprite ------------------
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 1;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        startText = "Gravity";
                        difficultyRating = 2;

                        invaders[0] = new GravitySprite(context, DownFallView.playerShip, 0, 0);
                    }
                },
                new Level() {
                    // One gravity and a bouncy
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 3;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        startText = "";
                        difficultyRating = 7;

                        invaders[0] = new GravitySprite(context, DownFallView.playerShip, (float) (Levels.screenWidth * Math.random()), 0);
                        invaders[1] = new BouncySprite(context, 0, 0, Levels.screenWidth);
                        invaders[2] = new BouncySprite(context, 0, -Levels.screenHeight/2, Levels.screenWidth);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 4;
                        levelTimeLimit = (int) (500 * timeOffSetMultiplier);
                        startText = "It gravitates.";

                        invaders[0] = new GravitySprite(context, DownFallView.playerShip, 1*Levels.screenWidth/5, 6*Levels.screenHeight/7);
                        invaders[1] = new GravitySprite(context, DownFallView.playerShip, 2*Levels.screenWidth/4, Levels.screenHeight/6);
                        invaders[2] = new GravitySprite(context, DownFallView.playerShip, 4*Levels.screenWidth/5, 6*Levels.screenHeight/7);
                        invaders[3] = new BouncySprite(context, 0, 0, Levels.screenWidth);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Clamper plus bouncies
                        numInvaders = 5;
                        levelTimeLimit = (int) (550 * timeOffSetMultiplier);
                        startText = "-|-";
                        difficultyRating = 8;

                        int diff = 65*Levels.screenHeight/100;
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                        invaders[numInvaders-1] = new ClamperSprite(context, 0.8, 0, 0, 2 *screenHeight);
                    }
                },
                // v hard levels
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Two clampers and bouncies :D
                        numInvaders = 6;
                        levelTimeLimit = (int) (525 * timeOffSetMultiplier);
                        startText = "-|-|-";
                        difficultyRating = 8;

                        int diff = 75*Levels.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                        invaders[4] = new ClamperSprite(context, 0.6, 0, 0, 7*Levels.screenHeight/3);
                        invaders[5] = new ClamperSprite(context, 0.6, Levels.screenWidth-ClamperSprite.clamperWidth, 0, 7*Levels.screenHeight/3);
                    }
                },

                // v hard levels
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        // Two clampers and bouncies :D
                        numInvaders = 8;
                        levelTimeLimit = (int) (725 * timeOffSetMultiplier);
                        startText = "-|-|-\n-|-|-";
                        difficultyRating = 8;

                        int diff = 75*Levels.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, Levels.screenWidth);
                        }
                        invaders[6] = new ClamperSprite(context, 0.3, 0, 0, diff*numInvaders);
                        invaders[7] = new ClamperSprite(context, 0.3, Levels.screenWidth-ClamperSprite.clamperWidth, 0, diff*numInvaders);
                    }
                },
                new Level() {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        numInvaders = 9;
                        levelTimeLimit = (int) (450 * timeOffSetMultiplier);
                        startText = "The Crusher 2.0";
                        difficultyRating = 4;

                        invaders[0] = new ClamperSprite(context, 0.6, 0, 0, 3 * Levels.screenHeight);
                        invaders[1] = new ClamperSprite(context, 0.6, Levels.screenWidth-ClamperSprite.clamperWidth, 0, 3 * Levels.screenHeight);

                        int diff = (3*Levels.screenHeight)/(numInvaders-2);
                        for (int i = 2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(i-2) * diff, Levels.screenWidth, Basic.basicHeight);
                        }
                    }
                },
        };

    }

    public static void prepareLevel(PlayerShip ship, InvaderAbstract[] invaderAbstracts, Context context) {
        if (currentLevel >= levels.length) {
            currentLevel--;
        }
        for (i = 0; i < invaderAbstracts.length; i++) {
            invaderAbstracts[i] = null;
        }
        levels[currentLevel].prepare(invaderAbstracts, context);
    }

    private static SharedPreferences.Editor mEditor;
    public static void updateCurrentLevel() {
        currentLevel++;
        numberAttempts = 0;
        if (currentLevel > highestLevel) {
            highestLevel = currentLevel;
        }

        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(currentLevelStr, currentLevel);
            mEditor.putInt(highestLevelStr, highestLevel);
            mEditor.putInt(scoreStr, score);
            mEditor.putInt(numberAttemptsStr, numberAttempts);
            mEditor.putInt(requestAdAmountStr, requestAdAmount);
            mEditor.commit();
        }
    }
    public static void saveRequestAdAmount() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(requestAdAmountStr, requestAdAmount);
            mEditor.commit();
        }
    }
    public static void saveNumberAttempts() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(numberAttemptsStr, numberAttempts);
            mEditor.commit();
        }
    }
    public static void saveScore() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(scoreStr, score);
            mEditor.commit();
        }
    }




    // potentially remove
    /*public static void narrowPaths(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 21;
        levelTimeLimit = 450 + timeOffSetMultiplier;
        difficultyRating = 5;

        int diff = 4 * Levels.screenHeight / 9 ;
        int r;
        for (int i = 0; i < numInvaders; i+=3) {
            r = (i / 3) % 3;
            invaders[i] = new Basic(context, 0*screenWidth/3, -i/3 * diff, screenWidth/3, diff);
            invaders[i+1] = new Basic(context, 1*screenWidth/3, -i/3 * diff, screenWidth/3, diff);
            invaders[i+2] = new Basic(context, 2*screenWidth/3, -i/3 * diff, screenWidth/3, diff);

            invaders[i+r] = new Basic(context, -100, -100, 0 ,0);
        }
    } */
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

    /* public static void squareSquadMedium(InvaderAbstract[] invaders, Context context) {
        // Build an army of invaders
        numInvaders = 8;
        levelTimeLimit = 750 + timeOffSetMultiplier;
        startText = "Tap & Drag!";
        double ratio = 1.5;

        int diff = (int) (screenHeight / ratio) * 2;
        for (int i = 0; i < numInvaders / 2; i++) {
            invaders[i] = new Basic(context, 0, -(diff * i), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
        for (int i = numInvaders / 2; i < numInvaders; i++) {
            invaders[i] = new Basic(context, screenWidth / 2, (int) (-(diff * (i - numInvaders / 2)) - diff / 2), (int) (screenWidth / 2), (int) (screenHeight / ratio));
        }
    } */



}
