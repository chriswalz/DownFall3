package com.walz.joltimate.downfall2.Invaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatDrawableManager;

import com.walz.joltimate.downfall2.Levels;
import com.walz.joltimate.downfall2.PlayerShip;
import com.walz.joltimate.downfall2.R;

/**
 * Created by chris on 7/30/16.
 */
public class BitmapSprite extends InvaderAbstract {
    private Bitmap bitmap;

    private boolean isRecycled = false;
    private int frames = 0;

    public BitmapSprite(Context context, int id, float x, float y, float width, float height) {
        this.bitmap = vectorToBitmap(context, id); //BitmapFactory.decodeResource(context.getResources(), id);
        this.bitmap = getResizedBitmap(this.bitmap, (int) width, (int) height);
        this.x = x - width/2;
        this.y = y - height/2;


    }
    @Override
    public void update(int fps) {
        y += baseSpeed;
        frames++;
        if (y >= Levels.screenHeight && !isRecycled) {
            recycle();
        }

    }
    @Override
    public void draw(Canvas c) {
        int mod = 120;
        int end = mod - 1;
        int ratio = 8;
        if (y <= Levels.screenHeight && !isRecycled) {
            if ( frames % mod < end) {
                y+=baseSpeed/ratio;
            } else {
                y-= end*baseSpeed/ratio;
            }
            c.drawBitmap(bitmap, x, y, invaderPaint);
        }

    }
    @Override
    public boolean isColliding(PlayerShip playerShip) {
        return false;
    }
    public void recycle() {
        bitmap.recycle();
        bitmap = null;
        isRecycled = true;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public static Bitmap vectorToBitmap(Context ctx, @DrawableRes int resVector) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(ctx, resVector);
        Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        return b;
    }
}
