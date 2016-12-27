package org.trypticon.android.love39watchface.layers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import org.trypticon.android.love39watchface.R;
import org.trypticon.android.love39watchface.framework.PaintHolder;
import org.trypticon.android.love39watchface.framework.WatchModeHelper;
import org.trypticon.android.love39watchface.framework.Workarounds;

/**
 * Layer which draws the background.
 */
class BackgroundLayer extends Layer {
    private final PaintHolder backgroundPaint;

    private final Rect tempRect = new Rect();

    BackgroundLayer(final Context context) {
        backgroundPaint = new PaintHolder(true) {
            @Override
            protected void configure(Paint paint) {
                paint.setColor(Workarounds.getColor(context, R.color.analog_background));
            }
        };
    }

    @Override
    public void updateWatchMode(WatchModeHelper mode) {
        backgroundPaint.updateWatchMode(mode);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (canvas.getClipBounds(tempRect)) {
            canvas.drawRect(tempRect, backgroundPaint.getPaint());
        }
    }

}
