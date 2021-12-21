package com.lotogram.ihunter.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lotogram.ihunter.R;

public class ScaleImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final String TAG = this.getClass().getSimpleName();

    private final float DEFAULT_SCALE_RATIO         = 0.9f;
    private final int   DEFAULT_SCALE_PERIOD        = 200;
    private final int   DEFAULT_FAST_CLICK_INTERVAL = 500;

    private boolean mFastClickEnabled;
    private float   mScaleRatio;
    private int     mScalePeriod;
    private int     mFastClickInterval;

    private long mTimeOfLastClick;

    public ScaleImageView(@NonNull Context context) {
        this(context, null);
    }

    public ScaleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        setFocusable(true);
        setClickable(true);
        mTimeOfLastClick = System.currentTimeMillis();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        setScaleRatio(ta.getFloat(R.styleable.ScaleImageView_scaleRatio, DEFAULT_SCALE_RATIO));
        setScalePeriod(ta.getInteger(R.styleable.ScaleImageView_scalePeriod, DEFAULT_SCALE_PERIOD));
        setFastClickEnabled(ta.getBoolean(R.styleable.ScaleImageView_fastClickEnabled, true));
        setFastClickInterval(ta.getInteger(R.styleable.ScaleImageView_fastClickInterval, DEFAULT_FAST_CLICK_INTERVAL));
        ta.recycle();
    }

    public void setScalePeriod(int period) {
        if (period > 500) {
            period = DEFAULT_SCALE_PERIOD;
        }
        this.mScalePeriod = period;
        Log.d(TAG, "ScalePeriod :" + period);
    }

    public void setScaleRatio(float ratio) {
        if (ratio > 1 || ratio < 0.5f) {
            ratio = DEFAULT_SCALE_RATIO;
        }
        this.mScaleRatio = ratio;
        Log.d(TAG, "ScaleRatio :" + ratio);
    }

    public void setFastClickEnabled(boolean enabled) {
        this.mFastClickEnabled = enabled;
        Log.d(TAG, "FastClickEnabled :" + enabled);
    }

    public void setFastClickInterval(int interval) {
        if (interval < 200 || interval > 1500) {
            interval = DEFAULT_FAST_CLICK_INTERVAL;
        }
        this.mFastClickInterval = interval;
        Log.d(TAG, "FastClickInterval: " + mFastClickInterval);
    }

    /**
     * public void reset() {
     * setScaleRatio(DEFAULT_SCALE_RATIO);
     * setScalePeriod(DEFAULT_SCALE_PERIOD);
     * setFastClickEnabled(DEFAULT_FAST_CLICK_ENABLED);
     * }
     */

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + isEnabled());
        boolean flag = false;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mScaleRatio == 1) break;
                ScaleAnimation zoomOut = new ScaleAnimation(
                        1, mScaleRatio,
                        1, mScaleRatio,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                zoomOut.setDuration(mScalePeriod);
                zoomOut.setFillAfter(true);
                startAnimation(zoomOut);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mScaleRatio == 1) break;
                ScaleAnimation zoomIn = new ScaleAnimation(
                        mScaleRatio, 1,
                        mScaleRatio, 1,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                zoomIn.setDuration(mScalePeriod);
                zoomIn.setFillAfter(true);
                startAnimation(zoomIn);

                if (!mFastClickEnabled) {
                    int interval = (int) (System.currentTimeMillis() - mTimeOfLastClick);
                    flag = interval < mFastClickInterval;
                    if (!flag) mTimeOfLastClick = System.currentTimeMillis();
                }
                break;
        }
        if (flag) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }
}