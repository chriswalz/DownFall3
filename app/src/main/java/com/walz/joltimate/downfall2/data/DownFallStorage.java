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

    public static int currentLevel = 36;
    public static int highestLevel = 50;
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




}
