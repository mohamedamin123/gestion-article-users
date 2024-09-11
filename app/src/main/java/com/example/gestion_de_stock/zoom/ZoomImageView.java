package com.example.gestion_de_stock.zoom;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ZoomImageView extends androidx.appcompat.widget.AppCompatImageView {

    private Matrix matrix;
    private float scale = 1f;
    private float scaleStep = 0.5f;

    public ZoomImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public ZoomImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                scale = scale == 1f ? 2f : 1f; // Toggle between scale factors
                applyScale();
            }
            return true;
        });
    }

    private void applyScale() {
        matrix.reset();
        matrix.setScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(matrix);
    }
}
