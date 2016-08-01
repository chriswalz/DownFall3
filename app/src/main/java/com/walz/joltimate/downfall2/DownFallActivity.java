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
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

public class DownFallActivity extends AppCompatActivity {

    // downFallView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    DownFallView downFallView;
    View secondLayerView;

    //AppCompatButton retryButton;
    //RelativeLayout retryLayer;

    RelativeLayout winLayer;
    AppCompatButton winButton;

    LinearLayoutCompat supportMenu;
    AppCompatImageButton playButton;
    AppCompatImageButton ratingButton;
    AppCompatTextView titleText;

    AppCompatTextView currentLevelTextView;

    private View v;
    private NumberPicker levelPicker;

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

        playButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.play_button);

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
                playButton.setVisibility(View.VISIBLE);
                winLayer.setVisibility(View.GONE);
                supportMenu.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.VISIBLE);
                //downFallView.prepareCurrentLevel();
            }
        });


    }

    public void setToStartScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //retryLayer.setVisibility(View.VISIBLE);
                levelPicker.setMaxValue(Levels.highestLevel);
                levelPicker.setValue(Levels.currentLevel);
                titleText.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                winLayer.setVisibility(View.GONE);
                supportMenu.setVisibility(View.VISIBLE);
                downFallView.prepareCurrentLevel();
            }
        });
    }
    public void setToPlayingScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //retryLayer.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.GONE);
                playButton.setVisibility(View.GONE);
                winLayer.setVisibility(View.GONE);
                supportMenu.setVisibility(View.GONE);

            }
        });
    }
    public void setToWinScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                supportMenu.setVisibility(View.VISIBLE);
                winLayer.setVisibility(View.VISIBLE);
                currentLevelTextView.setText("Level " + (Levels.currentLevel+1) + " of " + (Levels.levels.length));
            }
        });
    }
    public void setOverlayInvisible() {

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