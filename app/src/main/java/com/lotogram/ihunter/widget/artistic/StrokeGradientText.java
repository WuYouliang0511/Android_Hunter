package com.lotogram.ihunter.widget.artistic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lotogram.ihunter.R;

public class StrokeGradientText extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = "StrokeGradientText";

    public static final int LINEAR = 0x0000;

    public static final int VERTICAL   = 0x0000;
    public static final int HORIZONTAL = 0x0001;
    public static final int DIAGONAL   = 0x0002;

    public static final int SINGLE_LINE   = 0x0000;
    public static final int MULTIPLE_LINE = 0x0001;

    private static final boolean DEFAULT_SHADOW_ENABLED = true;
    private static final int     DEFAULT_SHADOW_TYPE    = LINEAR;
    private static final int     DEFAULT_ORIENTATION    = VERTICAL;
    private static final int     DEFAULT_LINEAR_TYPE    = SINGLE_LINE;
    private static final String  DEFAULT_SHADOW_COLORS  = "#FF0000,#0000FF";
    private static final boolean DEFAULT_STROKE_ENABLED = true;
    private static final int     DEFAULT_STROKE_COLOR   = Color.BLACK;
    private static final int     DEFAULT_STROKE_WIDTH   = 10;

    private boolean mShadowEnabled;
    private int     mShadowType;
    private int     mOrientation;
    private int     mLinearType;
    private int[]   mShadowColors;
    private boolean mStrokeEnabled;
    private int     mStrokeColor;
    private int     mStrokeWidth;

    private Shader mShader;

    public StrokeGradientText(Context context) {
        this(context, null);
    }

    public StrokeGradientText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeGradientText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StrokeGradientText);
        setShadowEnabled(ta.getBoolean(R.styleable.StrokeGradientText_shadowEnabled, DEFAULT_SHADOW_ENABLED));
        setShadowType(ta.getInteger(R.styleable.StrokeGradientText_shadowType, DEFAULT_SHADOW_TYPE));
        setOrientation(ta.getInteger(R.styleable.StrokeGradientText_orientation, DEFAULT_ORIENTATION));
        setLinearType(ta.getInteger(R.styleable.StrokeGradientText_linearType, DEFAULT_LINEAR_TYPE));
        setShadowColors(ta.getString(R.styleable.StrokeGradientText_shadowColors));
        setStrokeEnabled(ta.getBoolean(R.styleable.StrokeGradientText_strokeEnabled, DEFAULT_STROKE_ENABLED));
        setStrokeColor(ta.getColor(R.styleable.StrokeGradientText_strokeColor, DEFAULT_STROKE_COLOR));
        setStrokeWidth(ta.getDimensionPixelSize(R.styleable.StrokeGradientText_strokeWidth, DEFAULT_STROKE_WIDTH));
        ta.recycle();
    }

    @Nullable
    private Shader getShader() {
        if (!mShadowEnabled) return null;
        if (mShader != null) return mShader;
        switch (mShadowType) {
            case LINEAR:
                setLinearShader();
                break;
        }
        return mShader;
    }

    private void setShadowEnabled(boolean enabled) {
        this.mShadowEnabled = enabled;
    }

    private void setShadowType(@ShadowType int type) {
        this.mShadowType = type;
    }

    public void setOrientation(@Orientation int orientation) {
        this.mOrientation = orientation;
    }

    public void setLinearType(@LineType int type) {
        this.mLinearType = type;
    }

    public void setShadowColors(String colors) {
        if (TextUtils.isEmpty(colors)) colors = DEFAULT_SHADOW_COLORS;
        String[] colorsSplit = colors.split(",");
        mShadowColors = new int[colorsSplit.length];
        for (int i = 0; i < colorsSplit.length; i++) {
            Log.d(TAG, "color: " + colorsSplit[i]);
            mShadowColors[i] = Color.parseColor(colorsSplit[i]);
        }
    }

    public void setShadowColors(int[] colors) {
        this.mShadowColors = colors;
    }

    public int[] getShadowColors() {
        return mShadowColors;
    }

    public void setStrokeEnabled(boolean enabled) {
        this.mStrokeEnabled = enabled;
    }

    public void setStrokeColor(int color) {
        this.mStrokeColor = color;
    }

    public void setStrokeWidth(int width) {
        float maxWidth = getTextSize() / 10;
        this.mStrokeWidth = (int) Math.min(width, maxWidth);
    }

    private void setLinearShader() {
        int x1 = 0;
        int y1 = getLineHeight();
        TileMode mode = TileMode.REPEAT;
        switch (mOrientation) {
            case VERTICAL:
                x1 = 0;
                y1 = mLinearType == SINGLE_LINE ? getLineHeight() : getHeight();
                mode = mLinearType == SINGLE_LINE ? TileMode.REPEAT : TileMode.CLAMP;
                break;
            case HORIZONTAL:
                x1 = getWidth();
                y1 = 0;
                mode = TileMode.CLAMP;
                break;
            case DIAGONAL:
                x1 = getWidth();
                y1 = getHeight();
                mode = TileMode.CLAMP;
                break;
        }
        mShader = new LinearGradient(0, 0, x1, y1, getShadowColors(), null, mode);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ColorStateList list = getTextColors();
        TextPaint paint = getPaint();
        if (mStrokeEnabled) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(mStrokeWidth);
            paint.setAntiAlias(true);
            paint.setShader(null);
            setTextColor(mStrokeColor);
            super.onDraw(canvas);
        }
        if (mShadowEnabled) {
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(getShader());
            super.onDraw(canvas);
        } else {
            paint.setStyle(Paint.Style.FILL);
            setTextColor(list);
            super.onDraw(canvas);
        }
    }
}