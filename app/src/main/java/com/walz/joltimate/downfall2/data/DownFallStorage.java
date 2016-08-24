package com.walz.joltimate.downfall2.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.walz.joltimate.downfall2.BuildConfig;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.R;

// Change class to non-static
public class DownFallStorage {
    // Current Level
    public static int score;

    public static int requestAdAmount = 0;
    public static boolean viewedWarning = false;

    public static int screenWidth, screenHeight;

    public static boolean debug = false;

    private static SharedPreferences mPrefs;

    public static int currentLevel = 11;
    public static int highestLevel = 40;
    public static int numberAttempts = 0;

    public static int numLevels;



    private static String packageNameStr;
    private static String currentLevelStr;
    private static String highestLevelStr;
    private static String scoreStr;
    private static String numberAttemptsStr;
    private static String requestAdAmountStr;
    private static String viewedWarningStr;




    //private static int i;

    public static void init(Context context, final int screenX, int screenY) {
        InvaderAbstract.BASE_SPEED = 1 * screenY / 160;
        screenWidth = screenX;
        screenHeight = screenY;

        debug = BuildConfig.DEBUG;

        if (!debug) {
            packageNameStr = context.getString(R.string.package_name);
            currentLevelStr = context.getString(R.string.current_level);
            highestLevelStr = context.getString(R.string.highest_level);
            scoreStr = context.getString(R.string.score);
            numberAttemptsStr = context.getString(R.string.number_attempts);
            requestAdAmountStr = context.getString(R.string.request_ad_amount);
            viewedWarningStr = context.getString(R.string.viewed_warning);

            mPrefs = context.getSharedPreferences(packageNameStr, 0);

            requestAdAmount = mPrefs.getInt(requestAdAmountStr, 0);
            currentLevel = mPrefs.getInt(currentLevelStr, 0);
            highestLevel = mPrefs.getInt(highestLevelStr, 0);
            numberAttempts = mPrefs.getInt(numberAttemptsStr, 0);
            score = mPrefs.getInt(scoreStr, 0);
            viewedWarning = mPrefs.getBoolean(viewedWarningStr, false);
        }

    }





    private static SharedPreferences.Editor mEditor;

    public static void saveEverything() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(currentLevelStr, currentLevel);
            mEditor.putInt(highestLevelStr, highestLevel);
            mEditor.putInt(scoreStr, score);
            mEditor.putInt(numberAttemptsStr, numberAttempts);
            mEditor.putInt(requestAdAmountStr, requestAdAmount);
            mEditor.putBoolean(viewedWarningStr, viewedWarning);

            mEditor.apply();
        }
    }

    /*private static void saveData() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(currentLevelStr, currentLevel);
            mEditor.putInt(highestLevelStr, highestLevel);
            mEditor.putInt(scoreStr, score);
            mEditor.putInt(numberAttemptsStr, numberAttempts);
            mEditor.putInt(requestAdAmountStr, requestAdAmount);
            mEditor.apply();
        }
    }

    private static void saveRequestAdAmount() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(requestAdAmountStr, requestAdAmount);
            mEditor.apply();
        }
    }
    private static void saveNumberAttempts() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(numberAttemptsStr, numberAttempts);
            mEditor.apply();
        }
    }
    private static void saveViewedWarning() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putBoolean(viewedWarningStr, viewedWarning);
            mEditor.apply();
        }
    }
    private static void saveScore() {
        if (!debug) {
            mEditor = mPrefs.edit();
            mEditor.putInt(scoreStr, score);
            mEditor.apply();
        }
    } */




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
