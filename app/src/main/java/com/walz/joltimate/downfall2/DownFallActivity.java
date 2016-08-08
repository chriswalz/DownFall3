package com.walz.joltimate.downfall2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DownFallActivity extends AppCompatActivity {

    // downFallView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    private DownFallView downFallView;
    private View secondLayerView;

    //AppCompatButton retryButton;
    //RelativeLayout retryLayer;

    private RelativeLayout winLayer;
    private AppCompatButton winButton;

    private LinearLayoutCompat supportMenu;
    private AppCompatImageView playButton;
    private AppCompatImageButton ratingButton;
    private AppCompatTextView titleText;

    private AppCompatTextView currentLevelTextView;

    private View v;
    private NumberPicker levelPicker;

    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle bundle;

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    private int requestAdAmount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = getWindow().getDecorView();
        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        Resources resources = getResources();
        int navSize;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navSize = resources.getDimensionPixelSize(resourceId);
        } else {
            navSize = 0;
        }
        Levels.init(getApplicationContext(), size.x, size.y + navSize);


        // Initialize gameView and set it as the view
        downFallView = new DownFallView(this);
        setContentView(downFallView);

        // Use this to put a view over the surfaceview
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_game_screen_overlay, null, false);
        addContentView(secondLayerView, lp);

        /* retryLayer = (RelativeLayout) secondLayerView.findViewById(R.id.retryLayer);

        retryButton = (AppCompatButton) secondLayerView.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryLayer.setVisibility(View.GONE);
                downFallView.prepareCurrentLevel();
            }
        }); */
        levelPicker = (NumberPicker) secondLayerView.findViewById(R.id.level_picker);
        levelPicker.setMinValue(0);
        levelPicker.setMaxValue(Levels.highestLevel);
        levelPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {

            }
        });

        playButton = (AppCompatImageView) secondLayerView.findViewById(R.id.play_button);

        currentLevelTextView = (AppCompatTextView) secondLayerView.findViewById(R.id.current_level_textview);

        titleText = (AppCompatTextView) secondLayerView.findViewById(R.id.title_text);

        ratingButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.rating_button);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });

        supportMenu = (LinearLayoutCompat) secondLayerView.findViewById(R.id.support_menu);


        winLayer = (RelativeLayout) secondLayerView.findViewById(R.id.winLayer);
        winButton = (AppCompatButton) secondLayerView.findViewById(R.id.next_button);
        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("winButtonClicked!!","winButtonClicked!!");
                startScreen();
            }
        });

        setToStartScreen();

        if (!Levels.debug) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));
            mInterstitialAd = new InterstitialAd(this);

            mInterstitialAd.setAdUnitId(getString(R.string.image_interstitial));
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    //beginPlayingGame();
                }
            });
            requestNewInterstitial();
        }


    }
    public void requestNewInterstitial() {
        if (!Levels.debug) {
            adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    public void showInterstitialIfReady() {

        if (!Levels.debug && requestAdAmount % 7 == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }
        requestAdAmount++;
    }

    public void setToStartScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startScreen();

            }
        });
    }
    private void startScreen() {
        //retryLayer.setVisibility(View.VISIBLE);
        levelPicker.setMaxValue(Levels.highestLevel);
        levelPicker.setValue(Levels.currentLevel);
        //titleText.setVisibility(View.VISIBLE);
        titleText.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).y(Levels.screenHeight/13);
        //playButton.setVisibility(View.VISIBLE);
        playButton.animate().alpha(1.0f).setDuration(175);
        playButton.setEnabled(false);
        playButton.setClickable(false);
        //winLayer.setVisibility(View.GONE);
        winLayer.setY(-Levels.screenHeight/2);
        winLayer.animate().alpha(0.0f);
        winButton.setClickable(false);
        supportMenu.setVisibility(View.VISIBLE);

        downFallView.prepareCurrentLevel();
    }
    public void setToPlayingScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //retryLayer.setVisibility(View.VISIBLE);
                //titleText.setVisibility(View.GONE);
                titleText.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).y(Levels.screenHeight/8);
                //playButton.setVisibility(View.GONE);
                playButton.animate().alpha(0.0f);
                //winLayer.setVisibility(View.GONE);
                winLayer.animate().alpha(0.0f);
                supportMenu.setVisibility(View.GONE);

            }
        });
    }
    public void setToWinScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                supportMenu.setVisibility(View.VISIBLE);
                //winLayer.setVisibility(View.VISIBLE);
                winLayer.setScaleX(0.5f);
                winLayer.setScaleY(0.5f);
                winLayer.animate().y(0).alpha(1.0f).scaleX(1.0f).scaleY(1.0f);
                winButton.setClickable(true);
                //android.content.res.Resources res = getResources();
                //String.format(res.getString(R.string.level_textview), Levels.currentLevel+1, Levels.levels.length)
                currentLevelTextView.setText((Levels.currentLevel+1) + "/" + Levels.levels.length); //"Level " + (Levels.currentLevel+1) + " of " + (Levels.levels.length));
            }
        });
    }

    public void fireLoseEvent(int completionPercentage) {
        if (!Levels.debug) {
            bundle = new Bundle();
            bundle.putInt("level_number", Levels.currentLevel);
            bundle.putInt("completion_percentage", completionPercentage);
            mFirebaseAnalytics.logEvent("lose_level", bundle);
        }
    }
    public void fireWinEvent() {
        if (!Levels.debug) {
            bundle = new Bundle();
            bundle.putInt("level_number", Levels.currentLevel);
            bundle.putInt("number_attempts", Levels.numberAttempts);
            mFirebaseAnalytics.logEvent("win_level", bundle);
        }
    }
    public void setUserExperiment() {

    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        // Tell the gameView resume method to execute
        downFallView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        downFallView.pause();
    }
}