package com.walz.joltimate.downfall2.animations;

import com.walz.joltimate.downfall2.game.PlayerShip;


public abstract class LoseAnimation {
    private boolean triggerAnimation;
    private int animationFrames;


    public LoseAnimation() {
        reset();
    }
    public void start() {
        if (!triggerAnimation) {
            reset();
            triggerAnimation = true;
        }
    }

    public void draw(PlayerShip playerShip) {

        if (triggerAnimation) {
            if (animationFrames < 40) {
                playerShip.setAlive(false);
                animationFrames++;
            } else {
                onComplete();
                reset();
            }
        }
    }
    private void reset() {
        animationFrames = 0;
        triggerAnimation = false;
    }
    public boolean isAnimating() {
        return triggerAnimation;
    }
    public abstract void onComplete();
}
