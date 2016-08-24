package com.walz.joltimate.downfall2.animations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.walz.joltimate.downfall2.game.PlayerShip;
import com.walz.joltimate.downfall2.data.DownFallStorage;


public abstract class WinAnimation {
    private boolean triggerAnimation;
    private int winCircleRadius = 0;
    private Paint winCirclePaint;

    public WinAnimation() {
        winCirclePaint = new Paint();
        winCirclePaint.setColor(Color.argb(255, 162, 255, 159));

        reset();
    }
    public void start() {
        if (!triggerAnimation) {
            reset();
            triggerAnimation = true;
        }
    }

    public void draw(Canvas canvas, PlayerShip playerShip) {
        if (triggerAnimation) {
            if (winCircleRadius < DownFallStorage.screenHeight) {
                canvas.drawCircle(playerShip.getCenterX(), playerShip.getCenterY(), winCircleRadius, winCirclePaint);
                winCircleRadius += DownFallStorage.screenHeight/40;

            } else {
                onComplete();
                reset();
            }
        }
    }
    private void reset() {
        winCircleRadius = 0;
        triggerAnimation = false;
    }
    public boolean isAnimating() {
        return triggerAnimation;
    }
    public abstract void onComplete();
}
