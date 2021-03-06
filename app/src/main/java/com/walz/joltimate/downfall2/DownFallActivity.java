package com.walz.joltimate.downfall2;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.walz.joltimate.downfall2.data.DownFallStorage;
import com.walz.joltimate.downfall2.game.DownFallGame;
import com.walz.joltimate.downfall2.game.Levels;

import java.lang.reflect.Method;

public class DownFallActivity extends AppCompatActivity {

    // downFallView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    private DownFallView downFallView;
    private View secondLayerView;

    //AppCompatButton retryButton;
    //RelativeLayout retryLayer;

    private RelativeLayout winLayer;

    private LinearLayoutCompat supportMenu;
    private AppCompatImageView playButton;
    private AppCompatImageButton ratingButton;
    private AppCompatImageButton shareButton;
    private AppCompatTextView titleText;
    private AppCompatTextView successText;
    private AppCompatImageButton addButton;
    private AppCompatImageButton subtractButton;
    private AppCompatImageButton soundButton;

    private boolean levelManuallyChanged = false;

    private AppCompatTextView currentLevelTextView;

    private NumberPicker levelPicker;

    private FirebaseAnalytics mFirebaseAnalytics;
    private Bundle bundle;

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    private String playStoreUrl;

    private SoundPool sounds;
    private int[] soundIds;
    private MediaPlayer mPlayer;

    private AppCompatTextView helpTextView;

    private AppCompatButton dismissWarningButton;
    private Vibrator vibrator;

    private View window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        bundle = new Bundle();


        playStoreUrl = "http://play.google.com/store/apps/details?id=" + getPackageName();

        soundIds = new int[7];

        window = getWindow().getDecorView();
        // Initialize gameView and set it as the view
        Display display = getWindowManager().getDefaultDisplay();
        int realWidth;
        int realHeight;

        if (Build.VERSION.SDK_INT >= 17){
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                //this may not be 100% accurate, but it's all we've got
                realWidth = display.getWidth();
                realHeight = display.getHeight();
                Log.e("Display Info", "Couldn't use reflection to get the real display metrics.");
            }

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            realWidth = display.getWidth();
            realHeight = display.getHeight();
        }
        downFallView = new DownFallView(this, realWidth, realHeight);
        setContentView(downFallView);

        // Use this to put a view over the surfaceview
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_game_screen_overlay, null, false);
        addContentView(secondLayerView, lp);

        helpTextView = (AppCompatTextView) secondLayerView.findViewById(R.id.help_textview);

        /* retryLayer = (RelativeLayout) secondLayerView.findViewById(R.id.retryLayer);

        retryButton = (AppCompatButton) secondLayerView.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryLayer.setVisibility(View.GONE);
                downFallView.prepareCurrentLevel();
            }
        }); */
        successText = (AppCompatTextView) secondLayerView.findViewById(R.id.success);

        levelPicker = (NumberPicker) secondLayerView.findViewById(R.id.level_picker);
        levelPicker.setMinValue(0);
        levelPicker.setMaxValue(DownFallStorage.highestLevel);
        levelPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {

            }
        });

        playButton = (AppCompatImageView) secondLayerView.findViewById(R.id.play_button);
        playButton.setEnabled(false);
        playButton.setClickable(false);

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
                    fireRatingClickedEvent();
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(playStoreUrl)));
                }

            }
        });
        shareButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireShareClickedEvent();
                onInviteClicked();
            }
        });

        supportMenu = (LinearLayoutCompat) secondLayerView.findViewById(R.id.support_menu);

        addButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downFallView.getDownFallGame().levels.incrementCurrentLevel();
                setHelpTextView(downFallView.getDownFallGame().getCurrentLevel().startText);
                levelManuallyChanged = true;
            }
        });
        subtractButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.subtract_button);
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DownFallStorage.currentLevel > 0) {
                    levelManuallyChanged = true;
                    DownFallStorage.currentLevel--;
                    setHelpTextView(downFallView.getDownFallGame().getCurrentLevel().startText);
                }
            }
        });


        winLayer = (RelativeLayout) secondLayerView.findViewById(R.id.winLayer);

        dismissWarningButton = (AppCompatButton) secondLayerView.findViewById(R.id.warning_button);
        if (!DownFallStorage.viewedWarning) {
            dismissWarningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    secondLayerView.findViewById(R.id.warning_layout).setVisibility(View.GONE);
                    DownFallStorage.viewedWarning = true;
                }
            });
        } else {
            secondLayerView.findViewById(R.id.warning_layout).setVisibility(View.GONE);
        }

        setToStartScreen();

        if (!DownFallStorage.debug) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
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
        } else {
            secondLayerView.findViewById(R.id.warning_layout).setVisibility(View.GONE);
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        soundIds[0] = sounds.load(getApplicationContext(), R.raw.kick_marker, 1);
        soundIds[1] = sounds.load(getApplicationContext(), R.raw.object_pass_sound, 1);
        soundIds[2] = sounds.load(getApplicationContext(), R.raw.touch_release, 1);
        soundIds[3] = sounds.load(getApplicationContext(), R.raw.win_ping, 1);
        soundIds[4] = sounds.load(getApplicationContext(), R.raw.cabled_mess, 1);
        soundIds[5] = sounds.load(getApplicationContext(), R.raw.mouth_pop, 1);
        /*sounds.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundPool.play(i, 1, 1, 0, 0, 1);
            }
        }); */
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.faster_music_two); // in 2nd param u have to pass your desire ringtone
        mPlayer.setLooping(true);
        mPlayer.setVolume(0.40f, 0.40f);

        if (!DownFallStorage.debug) {
            FirebaseMessaging.getInstance().subscribeToTopic("downfall");
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic("downfalldebug");
        }

        soundButton = (AppCompatImageButton) secondLayerView.findViewById(R.id.sound_button);
        setSoundSetting();
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownFallStorage.isSoundOn = !DownFallStorage.isSoundOn;
                setSoundSetting();
            }
        });
    }
    private void setSoundSetting() {
        if (DownFallStorage.isSoundOn) {
            if (!mPlayer.isPlaying()) {
                mPlayer.start();
            }
            soundButton.setImageResource(R.drawable.ic_sound_on);
        } else {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
            soundButton.setImageResource(R.drawable.ic_sound_off);
        }
    }
    public void playExplosionSound() {
        vibrator.vibrate(1);
        sounds.play(soundIds[5], 0.9f, 0.9f, 0, 0, 1);
    }
    public void playBounceSound() {
        //vibrator.vibrate(20);
        sounds.play(soundIds[4], 0.5f, 0.5f, 0, 0, 1);
    }
    public void playLoseSound() {
        vibrator.vibrate(20);
        sounds.play(soundIds[0], 1.0f, 1.0f, 0, 0, 1);
    }
    public void playWinSound() {
        sounds.play(soundIds[3], 0.60f, 0.60f, 0, 0, 1);
    }
    public void playTeleportSound() {
        sounds.play(soundIds[1], 0.10f, 0.10f, 0, 0, 1);
    }
    public void requestNewInterstitial() {
        if (!DownFallStorage.debug) {
            adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    public boolean shouldPrepareLevelAgain() {
        if (levelManuallyChanged) {
            levelManuallyChanged = false;
            return true;
        }
        return false;

    }
    public void showInterstitialIfReady() {

        if (!DownFallStorage.debug && DownFallStorage.requestAdAmount % 12 == 0 && DownFallStorage.highestLevel >= 8) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
        }
        DownFallStorage.requestAdAmount++;
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
        levelPicker.setMaxValue(DownFallStorage.highestLevel);
        levelPicker.setValue(DownFallStorage.currentLevel);
        //titleText.setVisibility(View.VISIBLE);
        titleText.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).y(DownFallStorage.screenHeight/13);
        //playButton.setVisibility(View.VISIBLE);
        playButton.animate().alpha(1.0f).setDuration(175);

        //winLayer.setVisibility(View.GONE);
        //winLayer.setY(-Levels.screenHeight/2);
        winLayer.animate().alpha(0.0f);

        addButton.setVisibility(View.VISIBLE);
        subtractButton.setVisibility(View.VISIBLE);
        supportMenu.setVisibility(View.VISIBLE);
        supportMenu.animate().scaleX(1.0f);
        supportMenu.animate().scaleY(1.0f);
        helpTextView.animate().alpha(1.0f).setDuration(300);

        downFallView.getDownFallGame().prepareCurrentLevel();
    }
    public void setToPlayingScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //retryLayer.setVisibility(View.VISIBLE);
                //titleText.setVisibility(View.GONE);
                titleText.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).y(DownFallStorage.screenHeight/8);
                //playButton.setVisibility(View.GONE);
                playButton.animate().alpha(0.0f);
                //winLayer.setVisibility(View.GONE);
                winLayer.animate().alpha(0.0f).setDuration(200);
                successText.setY(-DownFallStorage.screenHeight/4);
                addButton.setVisibility(View.GONE);
                subtractButton.setVisibility(View.GONE);
                supportMenu.setVisibility(View.GONE);
                helpTextView.animate().alpha(0.0f).setDuration(900);

            }
        });
    }
    public void setToWinScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                supportMenu.setVisibility(View.VISIBLE);
                //winLayer.setVisibility(View.VISIBLE);
                //winLayer.setScaleX(0.5f);
                //winLayer.setScaleY(0.5f);
                winLayer.animate().alpha(1.0f); //.scaleX(1.0f).scaleY(1.0f); //.y(0);

                successText.animate().y(DownFallStorage.screenHeight/5).setDuration(600);

                addButton.setVisibility(View.GONE);
                subtractButton.setVisibility(View.GONE);
                helpTextView.setAlpha(0);
                //android.content.res.Resources res = getResources();
                //String.format(res.getString(R.string.level_textview), Levels.currentLevel+1, Levels.levels.length)
                currentLevelTextView.setText((DownFallStorage.currentLevel+1) + "/" + DownFallStorage.numLevels); //"Level " + (Levels.currentLevel+1) + " of " + (Levels.levels.length));
                if (DownFallStorage.currentLevel >= 8) {
                    supportMenu.animate().scaleY(1.2f).scaleX(1.2f).setDuration(400); //.y(3*Levels.screenHeight/4); //.scaleX(1.0f).scaleY(1.0f);
                }
            }
        });
    }

    public void fireLoseEvent(int completionPercentage) {
        if (!DownFallStorage.debug) {
            bundle.clear();
            //bundle.putLong(FirebaseAnalytics.Param.LEVEL, Levels.currentLevel);
            bundle.putInt("completion_percentage", completionPercentage);
            //bundle.putInt(FirebaseAnalytics.Param.VALUE, 1);
            mFirebaseAnalytics.logEvent("level_" + DownFallStorage.currentLevel+"_lose", bundle);
        }
    }
    public void fireWinEvent() {
        if (!DownFallStorage.debug) {
            bundle.clear();
            bundle.putInt(numberAttempts, DownFallStorage.numberAttempts);
            //bundle.putLong(FirebaseAnalytics.Param.LEVEL, Levels.currentLevel);
            //bundle.putInt(FirebaseAnalytics.Param.VALUE, 1);
            mFirebaseAnalytics.logEvent("level_" + DownFallStorage.currentLevel+"_win", bundle);
        }
    }
    public void firePauseEvent() {
        if (!DownFallStorage.debug) {
            bundle.clear();
            bundle.putInt(numberAttempts, DownFallStorage.numberAttempts);
            bundle.putLong(FirebaseAnalytics.Param.LEVEL, DownFallStorage.currentLevel);
            mFirebaseAnalytics.logEvent("pause_game_level_"+ DownFallStorage.currentLevel, bundle);
        }
    }
    private String numberAttempts = "number_attempts";
    public void fireShareClickedEvent() {
        if (!DownFallStorage.debug) {
            bundle.clear();
            bundle.putInt(FirebaseAnalytics.Param.LEVEL, DownFallStorage.currentLevel);
            bundle.putInt(numberAttempts, DownFallStorage.numberAttempts);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);
        }
    }
    private String ratingButtonClicked = "rating_button_clicked";
    public void fireRatingClickedEvent() {
        if (!DownFallStorage.debug) {
            bundle.clear();
            bundle.putInt(FirebaseAnalytics.Param.LEVEL, DownFallStorage.currentLevel);
            mFirebaseAnalytics.logEvent(ratingButtonClicked, bundle);
        }
    }
    private String interstitialImpression = "interstitial_impression";
    public void fireAdImpression() {
        if (!DownFallStorage.debug) {
            bundle.clear();
            bundle.putInt(FirebaseAnalytics.Param.LEVEL, DownFallStorage.currentLevel);
            mFirebaseAnalytics.logEvent(interstitialImpression, bundle);
        }
    }
    public void setUserExperiment() {

    }
    public void onInviteClicked() {
        /*Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE); */
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.invitation_title));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.invitation_message) + " " + playStoreUrl);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        window.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        if (DownFallStorage.isSoundOn) {
            mPlayer.start();
        }
        downFallView.resume();

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        sounds = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {

        firePauseEvent();
        if (DownFallStorage.isSoundOn) {
            mPlayer.pause();
        }
        downFallView.pause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        sounds.release();
        sounds = null;
        mPlayer.release();
        mPlayer = null;

        super.onDestroy();
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d("TAG", "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    } */
    public void setHelpTextView(final String text) {
        if (helpTextView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    helpTextView.setText(text);
                }
            });
        }
    }
}