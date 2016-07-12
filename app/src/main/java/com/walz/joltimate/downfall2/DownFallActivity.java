package com.walz.joltimate.downfall2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DownFallActivity extends AppCompatActivity {

    // downFallView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    DownFallView downFallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        Levels.init(size.x, size.y);



        // Initialize gameView and set it as the view
        downFallView = new DownFallView(this);
        setContentView(downFallView);

        // Use this to put a view over the surfaceview
        /* ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_home_screen, null, false);
        addContentView(secondLayerView, lp); */

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