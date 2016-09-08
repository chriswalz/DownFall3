package com.walz.joltimate.downfall2.game;

import android.content.Context;

import com.walz.joltimate.downfall2.Invaders.AcceleratorSprite;
import com.walz.joltimate.downfall2.Invaders.Basic;
import com.walz.joltimate.downfall2.Invaders.BitmapSprite;
import com.walz.joltimate.downfall2.Invaders.BouncySprite;
import com.walz.joltimate.downfall2.Invaders.ClamperSprite;
import com.walz.joltimate.downfall2.Invaders.FireworkSprite;
import com.walz.joltimate.downfall2.Invaders.GravitySprite;
import com.walz.joltimate.downfall2.Invaders.InvaderAbstract;
import com.walz.joltimate.downfall2.Invaders.RainSprite;
import com.walz.joltimate.downfall2.R;
import com.walz.joltimate.downfall2.data.DownFallStorage;

/**
 * Created by chris on 8/22/16.
 */
public class Levels {
    private Level[] levels;
    private PlayerShip playerShip;

    private int screenWidth, screenHeight;


    public Levels(PlayerShip playerShip2) {
        playerShip = playerShip2; // made it static as a workaround :(
        screenWidth = DownFallStorage.screenWidth;
        screenHeight = DownFallStorage.screenHeight;


        levels = new Level[]{
                // CHAPTER 1, youse a basic bitch
                new Level(5, 300, "Drag to move!", 1) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int blockWidth = screenWidth / 3;
                        int offset = -1*screenWidth/3;
                        invaders[0] = new Basic(context, 0, offset, blockWidth);
                        invaders[1] = new Basic(context, 2*blockWidth, offset, blockWidth);
                        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), (-3*screenHeight/4) + offset, screenWidth/4, screenHeight / 4);
                        invaders[3] = new Basic(context, 0, (-7* DownFallStorage.screenHeight/5) + offset, blockWidth);
                        invaders[4] = new Basic(context, 2*blockWidth, (-7* DownFallStorage.screenHeight/5) + offset, blockWidth);
                    }
                },
                new Level(4, 250, "Tap above the block", 2) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 4*screenHeight/9;
                        invaders[0] = new Basic(context, 0, (-1*diff), screenWidth, screenHeight / 10);
                        invaders[1] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, (-2*diff)+(diff/4), DownFallStorage.screenWidth/7, DownFallStorage.screenWidth/7);
                        invaders[2] = new Basic(context, 0, (-3*diff), screenWidth, screenHeight / 10);
                        invaders[3] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, (-4*diff)+(diff/4), DownFallStorage.screenWidth/7, DownFallStorage.screenWidth/7);
                    }
                },
                new Level(3, 270, "Chapter 1\nWatch out below!", 2) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 4*screenHeight/9;
                        invaders[0] = new Basic(context, 0, -1*diff, screenWidth, screenHeight / 10, true);
                        // invaders[1] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, -2*diff, Levels.screenWidth/7, Levels.screenWidth/7);
                        invaders[1] = new Basic(context, 0, -3*diff, screenWidth, screenHeight / 10, true);
                        invaders[2] = new Basic(context, screenWidth/2 - screenWidth/8, -3*diff, (screenWidth/4), screenHeight / 2, true);
                        //invaders[3] = new BitmapSprite(context, R.drawable.ic_hand_touch, screenWidth/2, -4*diff, Levels.screenWidth/7, Levels.screenWidth/7);
                    }
                },
                new Level(7, 575, "Things get a little harder", 3) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {

                        int diff = 3*screenHeight/9;
                        invaders[0] = new Basic(context, 0, -1*diff, screenWidth, screenHeight / 10);
                        invaders[1] = new Basic(context, 0, -3*diff, screenWidth, screenHeight / 10);
                        invaders[2] = new Basic(context, (screenWidth / 2) - (screenWidth / 8), -6*screenHeight/4, screenWidth/4, screenHeight / 4);
                        invaders[3] = new Basic(context, 0, -7*screenHeight/4, screenWidth, 3*Basic.basicHeight);
                        invaders[4] = new Basic(context, 0, -11*screenHeight/4, screenWidth/3, screenHeight);
                        invaders[5] = new Basic(context, 2*screenWidth/3, -11*screenHeight/4, screenWidth/3, screenHeight);
                        invaders[6] = new Basic(context, 0, -15*screenHeight/4, screenWidth);
                    }
                },
                new Level(7, 425, "Enclosure", 3) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 4 * screenHeight / 6;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(diff * i ), screenWidth, screenHeight / 10);
                        }
                        invaders[3] = new Basic(context, 0, -(diff * 0), screenWidth/3, 7*diff);
                        invaders[4] = new Basic(context, 2*screenWidth/3, -(diff * 0), screenWidth/3, 7*diff);
                        invaders[5] = new Basic(context, 0, -(diff*3), screenWidth);
                        invaders[6] = new Basic(context, 0, -(diff*3 + screenHeight/2), screenWidth);
                    }
                },
                new Level(5, 850, "You've reached \nthe final level of chapter 1!\nThe Progression", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 15 * screenHeight / 24;
                        for (int i = 0; i < numInvaders ; i++) {
                            invaders[i] = new Basic(context, 0, -(14 * diff * i /  6 ), screenWidth, (i+1)*diff/(numInvaders));
                        }
                    }
                },
                // CHAPTER 2, Clamper
                new Level(9, 700, "Chapter 2:\n Things get shifty", 4) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 5 * DownFallStorage.screenHeight/6;
                        invaders[0] = new ClamperSprite(context, 0.6, 0, 0, (numInvaders-3) * diff);
                        for (int i = 1; i < numInvaders-1; i++) {
                            invaders[i] = new Basic(context, 0, -(i-1) * diff, DownFallStorage.screenWidth, Basic.basicHeight);

                        }
                        invaders[numInvaders-1] = new Basic(context, 0, -17 * DownFallStorage.screenHeight, DownFallStorage.screenWidth, Basic.basicHeight);
                    }
                },
                new Level(4, 450, "The Crusher\nThis one is extra hard.", 4) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new ClamperSprite(context, 0.5, 0, 0, 3 * DownFallStorage.screenHeight);
                        invaders[1] = new ClamperSprite(context, 0.5, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, 0, 3 * DownFallStorage.screenHeight);
                        invaders[2] = new Basic(context, 0, -3 * DownFallStorage.screenHeight, DownFallStorage.screenWidth, Basic.basicHeight);
                        invaders[3] = new Basic(context, 0, 0, DownFallStorage.screenWidth, Basic.basicHeight);
                    }
                },
                new Level(9, 575, "Irregular", 4) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int rectHeight = 3* DownFallStorage.screenHeight/4;

                        for (int i = 0; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 0.85, 0, -i * rectHeight, rectHeight);
                        }
                        for (int i = 1; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 0.85, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, -i * rectHeight, rectHeight);
                        }
                        invaders[numInvaders-1] = new Basic(context, 0, -(numInvaders-1)*rectHeight/2, screenWidth, screenHeight/2);
                    }
                },
                new Level(13, 650, "(.^.)", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int rectHeight = 3* DownFallStorage.screenHeight/4;
                        for (int i = 0; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 1.6, 0, -i * rectHeight, rectHeight);
                        }
                        for (int i = 1; i < numInvaders/2; i+=2) {
                            invaders[i] = new ClamperSprite(context, 0.8, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, -i * rectHeight, rectHeight);
                        }
                        for (int i = numInvaders/2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(i-numInvaders/2) * rectHeight, DownFallStorage.screenWidth, Basic.basicHeight);
                        }
                    }
                },
                // Chapter 3, BOUNCY ------------------------
                new Level(2, 285, "Chapter 3\nBouncy.", 2) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = screenHeight/2;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, -i * diff, DownFallStorage.screenWidth);
                        }
                    }
                },
                new Level(5, 625, "5 Bouncy Blocks", 4) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 82* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                    }
                },
                new Level(6, 600, "You've reached\nthe 50th percentile\n", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 82* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[numInvaders-1] = new Basic(context, (screenWidth/2) - screenWidth/16, 0, screenWidth/8, diff * (numInvaders-2));
                    }
                },
                new Level(5, 500, "-|-", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 77* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[numInvaders-1] = new ClamperSprite(context, 0.25, 0, 0, diff * (numInvaders-2));
                    }
                },
                new Level(20, 375, "Snake", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int ratio = 2;
                        int xCoordinate;
                        for (int i = 0; i < numInvaders; i++) {
                            xCoordinate = (i * DownFallStorage.screenWidth/(numInvaders/ratio)) % (2*screenWidth);
                            if (xCoordinate >= screenWidth) {
                                xCoordinate = screenWidth - (xCoordinate-screenWidth);
                            }
                            invaders[i] = new BouncySprite(context, xCoordinate, -i * DownFallStorage.screenHeight/(numInvaders/ratio), 1* DownFallStorage.screenWidth/6, DownFallStorage.screenHeight/(numInvaders/ratio));
                        }
                    }
                },
                new Level(36, 525, "Longer Snake", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int ratio = 3;
                        int xCoordinate;
                        for (int i = 0; i < numInvaders; i++) {
                            xCoordinate = (i * DownFallStorage.screenWidth/(numInvaders/ratio)) % (2*screenWidth);
                            if (xCoordinate >= screenWidth) {
                                xCoordinate = screenWidth - (xCoordinate-screenWidth);
                            }
                            invaders[i] = new BouncySprite(context, xCoordinate, -i * DownFallStorage.screenHeight/(numInvaders/ratio), 1* DownFallStorage.screenWidth/6, DownFallStorage.screenHeight/(numInvaders/ratio));
                        }
                    }
                },
                // Chapter 4, Fireworks! -------------------------
                new Level(2, 140, "Chapter 4\nIt explodes", 2) {
                    // Firework status
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 40, DownFallStorage.screenWidth/2);
                        }
                    }
                },
                new Level(4, 200, "Fireworks!", 4) {
                    // Three fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new FireworkSprite(context, 36, (i * DownFallStorage.screenWidth/(numInvaders+1)) + DownFallStorage.screenWidth/(numInvaders+1));
                        }
                        invaders[numInvaders-1] = new BouncySprite(context, 0, 0, DownFallStorage.screenWidth);
                    }
                },
                new Level(4, 150, "4th of July", 6) {
                    // Four fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 36, (i * DownFallStorage.screenWidth/(numInvaders+1)) + DownFallStorage.screenWidth/(numInvaders+1));
                        }
                    }
                },
                new Level(7, 525, "One at a time", 6) {
                    // Four fireworks
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                     int diff = 5* DownFallStorage.screenHeight/9;

                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new FireworkSprite(context, 20, (int)(3*Math.random()* DownFallStorage.screenWidth/4) + DownFallStorage.screenWidth/8, -i * diff);
                        }
                    }
                },
                // Chapter 5, Let it rain! ----------
                new Level(1, 475, "Chapter 5\nLet it rain!", 3) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new RainSprite(context, 40);
                    }
                },
                new Level(3, 625, "More Rain", 5) {
                    // Double the rain
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new RainSprite(context, 0, 24, screenHeight);
                        invaders[1] = new RainSprite(context, -screenHeight, 24, screenHeight);
                        invaders[2] = new Basic(context, 0, -3*screenHeight, DownFallStorage.screenWidth);
                    }
                },
                new Level(5, 625, "Rain and Blocks", 5) {
                    // Double the rain
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                         invaders[0] = new RainSprite(context, 0, 32, screenHeight);
                        invaders[1] = new RainSprite(context, -screenHeight, 32, screenHeight);
                        invaders[2] = new Basic(context, 0, 0, DownFallStorage.screenWidth);
                        invaders[3] = new Basic(context, 0, -RainSprite.HEIGHT, DownFallStorage.screenWidth);
                        invaders[4] = new Basic(context, 0, -2*RainSprite.HEIGHT, DownFallStorage.screenWidth, 3 * Basic.basicHeight);
                    }
                },
                // Chapter 7, Accelerator
                new Level(3, 175, "Chapter 6\nIt accelerates.", 2) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = screenHeight/3;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new AcceleratorSprite(context, 0, -i * diff, DownFallStorage.screenWidth, false);
                        }
                    }
                },
                new Level(5, 275, "Slow & Fast", 5) {
                    // 2 basics, 3 accelerators
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new Basic(context, 0, 0, DownFallStorage.screenWidth, Basic.basicHeight);
                        invaders[1] = new Basic(context, 0, -DownFallStorage.screenHeight/2, DownFallStorage.screenWidth, Basic.basicHeight);
                        invaders[3] = new AcceleratorSprite(context, 0, -DownFallStorage.screenHeight/4, DownFallStorage.screenWidth, false);
                        invaders[2] = new AcceleratorSprite(context, 0, -3 * DownFallStorage.screenHeight/4, DownFallStorage.screenWidth, false);
                        invaders[4] = new AcceleratorSprite(context, 0, -5* DownFallStorage.screenHeight/4, DownFallStorage.screenWidth, false);
                    }
                },
                new Level(6, 450, "Slow & More Fast", 6) {

                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 8 * screenHeight / 10;
                        for (int i = 0; i < numInvaders - 3; i++) {
                            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
                        }
                        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        invaders[numInvaders - 2] = new AcceleratorSprite(context, 0, -3* DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                    }
                },

                new Level(5, 425, "Variety is Good\n For the Soul", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new Basic(context, 0, -DownFallStorage.screenHeight, DownFallStorage.screenWidth);
                        invaders[1] = new ClamperSprite(context, 0.6, 0, 0, 2* DownFallStorage.screenHeight);
                        invaders[2] = new BouncySprite(context, 0, 0, DownFallStorage.screenWidth);
                        invaders[3] = new RainSprite(context, 12, DownFallStorage.screenHeight/4);
                        invaders[4] = new AcceleratorSprite(context, 0, -8* DownFallStorage.screenHeight/4, DownFallStorage.screenWidth, false);
                    }
                },
                // Chapter 8, Gravity Sprite ------------------
                new Level(1, 375, "Chapter 8\nVery Attractive", 2) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new GravitySprite(context, playerShip, 1.0f, 0, 0);
                    }
                },
                new Level(3, 450, "Keep moving", 7) {
                    // One gravity and a bouncy
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new GravitySprite(context, playerShip, 1.0f, (float) (DownFallStorage.screenWidth * Math.random()), 0);
                        invaders[1] = new BouncySprite(context, 0, 0, DownFallStorage.screenWidth);
                        invaders[2] = new BouncySprite(context, 0, -DownFallStorage.screenHeight/2, DownFallStorage.screenWidth);
                    }
                },
                new Level(5, 500, "Many angles of attack", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new GravitySprite(context, playerShip, 1.0f, -GravitySprite.SIZE, 7* DownFallStorage.screenHeight/7);
                        invaders[1] = new GravitySprite(context, playerShip, 1.0f, 2 * DownFallStorage.screenWidth/4, 0* DownFallStorage.screenHeight/6);
                        invaders[2] = new GravitySprite(context, playerShip, 1.0f, 5 * DownFallStorage.screenWidth/5, 7* DownFallStorage.screenHeight/7);
                        invaders[3] = new BouncySprite(context, 0, 0, DownFallStorage.screenWidth);
                        invaders[4] = new BouncySprite(context, 0, -2*screenHeight, DownFallStorage.screenWidth);
                    }
                },
                new Level(7, 375, 7 + " followers", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = -6*screenHeight/10;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new GravitySprite(context, playerShip, 1.0f, 2* DownFallStorage.screenWidth/4-(GravitySprite.SIZE/2), i * diff);
                        }
                    }
                },
                new Level(27, 550, "Narrow Paths" ,5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {

                        int diff = 4 * screenHeight / 9 ;
                        int r;
                        for (int i = 0; i < numInvaders; i+=3) {
                            r = (i / 3) % 3;
                            invaders[i] = new Basic(context, 0*screenWidth/3, -i/3 * diff, screenWidth/3, diff);
                            invaders[i+1] = new Basic(context, 1*screenWidth/3, -i/3 * diff, screenWidth/3, diff);
                            invaders[i+2] = new Basic(context, 2*screenWidth/3, -i/3 * diff, screenWidth/3, diff);

                            invaders[i+r].x = -1000;
                            invaders[i+r].y = -1000;
                        }
                    }
                },
                new Level(10, 625, "Checkerboard", 5) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        double ratio = 2.2;

                        int diff = (int) (screenHeight / ratio) * 2;
                        for (int i = 0; i < numInvaders / 2; i++) {
                            invaders[i] = new Basic(context, 0, (int) -(diff * i), (int) (screenWidth / 2), (int) (screenHeight / ratio));
                        }
                        for (int i = numInvaders / 2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, screenWidth / 2, (int) (-(diff * (i - numInvaders / 2)) - diff / 2), (int) (screenWidth / 2), (int) (screenHeight / ratio));
                        }
                    }
                },
                // v hard levels
                new Level(20, 550, "EXTREME:\nBoth Sides", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = screenHeight/5;
                        for (int i = 0; i < numInvaders-3; i+=2) {
                            invaders[i] = new Basic(context, 0, -i*diff, screenWidth/2, screenHeight/10, true);
                            invaders[i+1] = new Basic(context, screenWidth/2, -i*diff, screenWidth/2, screenHeight/10);
                        }
                        invaders[numInvaders-1] = new GravitySprite(context, playerShip, 0.10f, 0, 0);
                        invaders[numInvaders-2] = new GravitySprite(context, playerShip, 0.10f, screenWidth, screenHeight);
                    }
                },
                new Level(7, 650, "EXTREME:\n-|-", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 65* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders-1; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[numInvaders-1] = new ClamperSprite(context, 1.0, 0, 0, diff * numInvaders);
                    }
                },
                new Level(8, 800, "EXTREME\n-|-|-", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 75* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[numInvaders-1] = new ClamperSprite(context, 0.6, 0, 0, diff * numInvaders);
                        invaders[numInvaders-2] = new ClamperSprite(context, 0.6, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, 0, diff * numInvaders);
                    }
                },

                // v hard levels
                new Level(6, 350, "EXTREME - More variety", 6) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 4 * screenHeight / 10;
                        for (int i = 0; i < numInvaders - 2; i++) {
                            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
                        }
                        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        invaders[numInvaders - 2] = new RainSprite(context, 12, DownFallStorage.screenHeight/4);
                    }
                },
                new Level(8, 725, "EXTREME:\n-|-|-\n-|-|-", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 75* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[6] = new ClamperSprite(context, 0.3, 0, 0, diff*numInvaders);
                        invaders[7] = new ClamperSprite(context, 0.3, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, 0, diff*numInvaders);
                    }
                },
                new Level(8, 450, "Slow & More Fast", 6) {

                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 8 * screenHeight / 10;
                        for (int i = 0; i < numInvaders - 3; i++) {
                            invaders[i] = new BouncySprite(context, 0, -(diff * i), screenWidth);
                        }
                        invaders[numInvaders - 1] = new AcceleratorSprite(context, 0, -DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        invaders[numInvaders - 2] = new AcceleratorSprite(context, 0, -3* DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        invaders[numInvaders - 3] = new AcceleratorSprite(context, 0, -4* DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        invaders[numInvaders - 3] = new AcceleratorSprite(context, 0, -5* DownFallStorage.screenHeight / 2, DownFallStorage.screenWidth, false);
                        //invaders[numInvaders - 3] = new RainSprite(context, 12, Levels.screenHeight/4);
                    }
                },
                new Level(9, 450, "EXTREME: The Crusher 2.0", 4) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        invaders[0] = new ClamperSprite(context, 0.6, 0, 0, 3 * DownFallStorage.screenHeight);
                        invaders[1] = new ClamperSprite(context, 0.6, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, 0, 3 * DownFallStorage.screenHeight);

                        int diff = (3* DownFallStorage.screenHeight)/(numInvaders-2);
                        for (int i = 2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, 0, -(i-2) * diff, DownFallStorage.screenWidth, Basic.basicHeight);
                        }
                    }
                },
                new Level(20, 500, "EXTREME:\nEndurance Test", 6) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new AcceleratorSprite(context, 0, -i*DownFallStorage.screenHeight / 5, DownFallStorage.screenWidth, false);
                        }
                    }
                },
                new Level(12, 525, "EXTREME:\nThey Bounce Too", 6) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new AcceleratorSprite(context, 0, -i*DownFallStorage.screenHeight / 3, DownFallStorage.screenWidth, true);
                        }
                    }
                },
                new Level(20, 575, "EXTREME:\nDon't throw your phone", 8) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        double ratio = 4.8;
                        int diff = (int) (screenHeight / ratio) * 2;
                        for (int i = 0; i < numInvaders / 2; i++) {
                            invaders[i] = new Basic(context, 0, -(diff * i), (int) (screenWidth / 2), (int) (screenHeight / ratio));
                        }
                        for (int i = numInvaders / 2; i < numInvaders; i++) {
                            invaders[i] = new Basic(context, screenWidth / 2, (int) (-(diff * (i - numInvaders / 2)) - diff / 2), (int) (screenWidth / 2), (int) (screenHeight / ratio));
                        }
                    }
                },
                new Level(6, 600, "EXTREME\nThe Crusher 3.0", 9) {
                    @Override
                    public void prepare(InvaderAbstract[] invaders, Context context) {
                        int diff = 75* DownFallStorage.screenHeight/100;
                        for (int i = 0; i < numInvaders; i++) {
                            invaders[i] = new BouncySprite(context, 0, i * -diff, DownFallStorage.screenWidth);
                        }
                        invaders[4] = new ClamperSprite(context, 1.39, 0, 0, diff * numInvaders);
                        invaders[5] = new ClamperSprite(context, 1.39, DownFallStorage.screenWidth-ClamperSprite.clamperWidth, 0, diff * numInvaders);
                    }
                },
        };
        DownFallStorage.numLevels = levels.length;
    }

    public void prepareLevel(InvaderAbstract[] invaderAbstracts, Context context) {
        if (DownFallStorage.currentLevel >= levels.length) {
            DownFallStorage.currentLevel = levels.length-1;
        }
        for (int i = 0; i < invaderAbstracts.length; i++) {
            invaderAbstracts[i] = null;
        }
        Level level = getCurrentLevel();
        level.prepare(invaderAbstracts, context);
    }
    public void updateCurrentLevel() {
        DownFallStorage.currentLevel++;
        DownFallStorage.numberAttempts = 0;
        if (DownFallStorage.currentLevel > DownFallStorage.highestLevel) {
            DownFallStorage.highestLevel = DownFallStorage.currentLevel;
        }
    }
    public Level getCurrentLevel() {
        return levels[DownFallStorage.currentLevel];
    }
    public void incrementCurrentLevel() {
        if (DownFallStorage.highestLevel > DownFallStorage.currentLevel &&  levels.length-1 > DownFallStorage.currentLevel) {
            DownFallStorage.currentLevel++;
        }
    }

}
