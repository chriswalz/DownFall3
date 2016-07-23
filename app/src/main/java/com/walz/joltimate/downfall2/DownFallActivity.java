package com.walz.joltimate.downfall2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DownFallActivity extends AppCompatActivity {

    // downFallView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    DownFallView downFallView;
    View secondLayerView;
    AppCompatButton retryButton;
    RelativeLayout retryLayer;
    RelativeLayout winLayer;
    AppCompatButton winButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        Levels.init(getApplicationContext(), size.x, size.y);



        // Initialize gameView and set it as the view
        downFallView = new DownFallView(this);
        setContentView(downFallView);

        // Use this to put a view over the surfaceview
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_game_screen_overlay, null, false);
        addContentView(secondLayerView, lp);

        retryLayer = (RelativeLayout) secondLayerView.findViewById(R.id.retryLayer);

        retryButton = (AppCompatButton) secondLayerView.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryLayer.setVisibility(View.GONE);
                downFallView.prepareCurrentLevel();
            }
        });

        winLayer = (RelativeLayout) secondLayerView.findViewById(R.id.winLayer);
        winButton = (AppCompatButton) secondLayerView.findViewById(R.id.next_button);
        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winLayer.setVisibility(View.GONE);
                downFallView.prepareCurrentLevel();
            }
        });


    }
    public void setToRetryScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                retryLayer.setVisibility(View.VISIBLE);
            }
        });
    }
    public void setToWinScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                winLayer.setVisibility(View.VISIBLE);
            }
        });
    }
    public void setOverlayInvisible() {

    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

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