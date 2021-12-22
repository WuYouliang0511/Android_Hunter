package com.lotogram.ihunter.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.lotogram.ihunter.R;

public class ScaleImageButton extends LinearLayoutCompat {

    private final String TAG = this.getClass().getSimpleName();

    private final float DEFAULT_SCALE_RATIO         = 0.9f;
    private final int   DEFAULT_SCALE_PERIOD        = 200;
    private final int   DEFAULT_FAST_CLICK_INTERVAL = 500;

    private boolean mFastClickEnabled;
    private float   mScaleRatio;
    private int     mScalePeriod;
    private int     mFastClickInterval;
    private int     mImageRes;
    private String  mTextRes;
    private float   mTextSize;
    private int     mTextColor;
    private int     mInterval;

    private long mTimeOfLastClick;

    public ScaleImageButton(@NonNull Context context) {
        this(context, null);
    }

    public ScaleImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initViews();
        setFocusable(true);
        setClickable(true);
        mTimeOfLastClick = System.currentTimeMillis();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageButton);
        setScaleRatio(ta.getFloat(R.styleable.ScaleImageButton_scaleRatio, DEFAULT_SCALE_RATIO));
        setScalePeriod(ta.getInteger(R.styleable.ScaleImageButton_scalePeriod, DEFAULT_SCALE_PERIOD));
        setFastClickEnabled(ta.getBoolean(R.styleable.ScaleImageButton_fastClickEnabled, true));
        setFastClickInterval(ta.getInteger(R.styleable.ScaleImageButton_fastClickInterval, DEFAULT_FAST_CLICK_INTERVAL));
        setImageRes(ta.getResourceId(R.styleable.ScaleImageButton_image, 0));
        setTextRes(ta.getString(R.styleable.ScaleImageButton_text));
        setTextSize(ta.getDimensionPixelSize(R.styleable.ScaleImageButton_textSize, 14));
        setTextColor(ta.getColor(R.styleable.ScaleImageButton_textColor, Color.WHITE));
        setInterval(ta.getDimensionPixelSize(R.styleable.ScaleImageButton_interval, 0));
        ta.recycle();
    }

    private void initViews() {
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayoutCompat.HORIZONTAL);

        if (mImageRes > 0) {
            ImageView image = new ImageView(getContext());
            image.setImageResource(mImageRes);
            LayoutParams lp = new LayoutParams(-2, -2);
            addView(image, lp);
        }

        TextView text = new TextView(getContext());
        text.setText(mTextRes);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        text.setTextColor(mTextColor);
        LayoutParams lp1 = new LayoutParams(-2, -2);
        lp1.leftMargin = mInterval;
        addView(text, lp1);
    }

    public void setImageRes(@DrawableRes int res) {
        this.mImageRes = res;
        Log.d(TAG, "ImageRes: " + res);
    }

    public void setTextRes(String text) {
        this.mTextRes = text;
        Log.d(TAG, "TextRes: " + text);
    }

    public void setTextSize(float size) {
        this.mTextSize = size;
        Log.d(TAG, "TextSize: " + size);
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        Log.d(TAG, "TextColor: " + color);
    }

    public void setInterval(int interval) {
        this.mInterval = interval;
        Log.d(TAG, "interval: " + interval);
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