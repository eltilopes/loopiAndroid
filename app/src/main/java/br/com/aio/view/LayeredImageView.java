package br.com.aio.view;

/**
 * Created by elton on 01/09/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import java.util.ArrayList;

public class LayeredImageView extends android.support.v7.widget.AppCompatImageView {
    private final static String TAG = "LayeredImageView";

    ArrayList<Bitmap> mLayers;

    public LayeredImageView(Context context) {
        super(context);
        init();
    }

    public LayeredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLayers = new ArrayList<Bitmap>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = getImageMatrix();
        if (matrix != null) {
            int numLayers = mLayers.size();
            for (int i = 0; i < numLayers; i++) {
                Bitmap b = mLayers.get(i);
                canvas.drawBitmap(b, matrix, null);
            }
        }
    }

    public void addLayer(Bitmap b) {
        mLayers.add(b);
        invalidate();
    }

    public void addLayer(int idx, Bitmap b) {
        mLayers.add(idx, b);
        invalidate();
    }

    public void removeLayer(int idx) {
        mLayers.remove(idx);
    }

    public void removeAllLayers() {
        mLayers.clear();
        invalidate();
    }

    public int getLayersSize() {
        return mLayers.size();
    }
}