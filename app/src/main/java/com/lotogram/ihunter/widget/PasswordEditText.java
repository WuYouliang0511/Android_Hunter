package com.lotogram.ihunter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lotogram.ihunter.R;

public class PasswordEditText extends androidx.appcompat.widget.AppCompatEditText implements TextWatcher {

    private static final String TAG = "PasswordEditText";

    //密码框背景样式
    private static final int BACKGROUND_STYLE_UNDERLINE = 0x0000;
    private static final int BACKGROUND_STYLE_BORDER    = 0x0001;
    private static final int BACKGROUND_STYLE_SQUARE    = 0x0002;

    private int mPasswordNumber    = 6;
    private int mPasswordColor     = Color.BLACK;
    private int mPasswordRadius    = 10;
    private int mBackgroundStyle   = 0;
    private int mUnderlineWidth    = 30;
    private int mUnderlineHeight   = 10;
    private int mUnderlineColor    = Color.BLACK;
    private int mBorderStrokeWidth = 10;
    private int mBorderColor       = Color.BLACK;
    private int mSquareStrokeWidth = 10;
    private int mSquareColor       = Color.BLACK;
    private int mSquareInterval    = 30;

    private Paint mPaintPassword;
    private Paint mPaintUnderline;
    private Paint mPaintBorder;
    private Paint mPaintSquare;

    private boolean mCursorFlag = true;

    private OnPasswordChangeListener mOnPasswordChangeListener;

    public PasswordEditText(@NonNull Context context) {
        this(context, null);
    }

    public PasswordEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaints();
//        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        addTextChangedListener(this);
    }

    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        setPasswordNumber(ta.getInteger(R.styleable.PasswordEditText_passwordNumber, 6));
        setPasswordColor(ta.getColor(R.styleable.PasswordEditText_passwordColor, Color.BLACK));
        setPasswordRadius(ta.getDimensionPixelSize(R.styleable.PasswordEditText_passwordRadius, 10));

        int style = ta.getInteger(R.styleable.PasswordEditText_backgroundStyle, 0);
        setBackgroundStyle(style);
        switch (style) {
            case BACKGROUND_STYLE_UNDERLINE:
                setUnderlineWidth(ta.getDimensionPixelSize(R.styleable.PasswordEditText_underlineWidth, 30));
                setUnderlineHeight(ta.getDimensionPixelSize(R.styleable.PasswordEditText_underlineHeight, 10));
                setUnderlineColor(ta.getColor(R.styleable.PasswordEditText_underlineColor, Color.BLACK));
                break;
            case BACKGROUND_STYLE_BORDER:
                setBorderStrokeWidth(ta.getDimensionPixelSize(R.styleable.PasswordEditText_borderStrokeWidth, 10));
                setBorderColor(ta.getColor(R.styleable.PasswordEditText_borderColor, Color.BLACK));
                break;
            case BACKGROUND_STYLE_SQUARE:
                setSquareColor(ta.getColor(R.styleable.PasswordEditText_squareColor, Color.BLACK));
                setSquareStrokeWidth(ta.getDimensionPixelSize(R.styleable.PasswordEditText_squareStrokeWidth, 10));
                setSquareInterval(ta.getDimensionPixelSize(R.styleable.PasswordEditText_squareInterval, 30));
        }
        ta.recycle();
    }

    private void initPaints() {
        mPaintPassword = new Paint();
        mPaintPassword.setAntiAlias(true);
        mPaintPassword.setDither(true);
        mPaintPassword.setColor(mPasswordColor);

        switch (mBackgroundStyle) {
            case BACKGROUND_STYLE_UNDERLINE:
                mPaintUnderline = new Paint();
                mPaintUnderline.setAntiAlias(true);
                mPaintUnderline.setDither(true);
                mPaintUnderline.setColor(mUnderlineColor);
                break;
            case BACKGROUND_STYLE_BORDER:
                mPaintBorder = new Paint();
                mPaintBorder.setAntiAlias(true);
                mPaintBorder.setDither(true);
                mPaintBorder.setColor(mBorderColor);
                mPaintBorder.setStyle(Paint.Style.STROKE);
                mPaintBorder.setStrokeWidth(mBorderStrokeWidth);
                break;
            case BACKGROUND_STYLE_SQUARE:
                mPaintSquare = new Paint();
                mPaintSquare.setAntiAlias(true);
                mPaintSquare.setDither(true);
                mPaintSquare.setColor(mSquareColor);
                mPaintSquare.setStyle(Paint.Style.STROKE);
                mPaintSquare.setStrokeWidth(mSquareStrokeWidth);
                break;
        }
    }

    public void setPasswordNumber(int number) {
        number = Math.max(4, number);
        Log.d(TAG, "密码位数: " + number);
        this.mPasswordNumber = number;
    }

    public void setPasswordColor(int color) {
        Log.d(TAG, "密码原点颜色: " + color);
        this.mPasswordColor = color;
    }

    public void setPasswordRadius(int radius) {
        Log.d(TAG, "密码原点大小: " + radius);
        this.mPasswordRadius = radius;
    }

    public void setBackgroundStyle(int style) {
        Log.d(TAG, "背景样式: " + style);
        this.mBackgroundStyle = style;
    }

    public void setUnderlineWidth(int width) {
        Log.d(TAG, "下划线宽度: " + width);
        this.mUnderlineWidth = width;
    }

    public void setUnderlineHeight(int height) {
        Log.d(TAG, "下划线高度: " + height);
        this.mUnderlineHeight = height;
    }

    public void setUnderlineColor(int color) {
        Log.d(TAG, "下划线颜色: " + color);
        this.mUnderlineColor = color;
    }

    public void setBorderStrokeWidth(int width) {
        Log.d(TAG, "边框粗细: " + width);
        this.mBorderStrokeWidth = width;
    }

    public void setBorderColor(int color) {
        Log.d(TAG, "边框颜色: " + color);
        this.mBorderColor = color;
    }

    public void setSquareStrokeWidth(int width) {
        Log.d(TAG, "方块粗细: " + width);
        this.mSquareStrokeWidth = width;
    }

    public void setSquareColor(int color) {
        Log.d(TAG, "方块颜色: " + color);
        this.mSquareColor = color;
    }

    public void setSquareInterval(int interval) {
        Log.d(TAG, "方块间隙: " + interval);
        this.mSquareInterval = interval;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setPadding(0, 0, 0, 0);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mBackgroundStyle) {
            case BACKGROUND_STYLE_BORDER:
                drawBorder(canvas);
                drawBorderPoint(canvas);
                drawBorderCursor(canvas);
                break;
            case BACKGROUND_STYLE_SQUARE:
                break;
            default:
                break;
        }
    }

    private void drawBorder(@NonNull Canvas canvas) {
        int left = mBorderStrokeWidth >> 1;
        int top = mBorderStrokeWidth >> 1;
        int right = getWidth() - (mBorderStrokeWidth >> 1);
        int bottom = getHeight() - (mBorderStrokeWidth >> 1);
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, 10, 10, mPaintBorder);

        int blankWidth = (getWidth() - mBorderStrokeWidth * (mPasswordNumber + 1)) / mPasswordNumber;
        for (int i = 1; i < mPasswordNumber; i++) {
            int startX = (mBorderStrokeWidth >> 1) + i * (blankWidth + mBorderStrokeWidth);
            int startY = 0;
            int endX = (mBorderStrokeWidth >> 1) + i * (blankWidth + mBorderStrokeWidth);
            int endY = getHeight();
            canvas.drawLine(startX, startY, endX, endY, mPaintBorder);
        }
    }

    private void drawBorderPoint(Canvas canvas) {
        if (getText() == null) return;
        String password = getText().toString().trim();
        int length = password.length();

        int blankWidth = (getWidth() - mBorderStrokeWidth * (mPasswordNumber + 1)) / mPasswordNumber;
        // 不断的绘制密码
        for (int i = 0; i < length; i++) {
            int cy = getHeight() >> 1;
            int cx = mBorderStrokeWidth + (blankWidth >> 1) + i * (mBorderStrokeWidth + blankWidth);
            canvas.drawCircle(cx, cy, mPasswordRadius, mPaintPassword);
        }
    }

    private void drawBorderCursor(Canvas canvas) {
        if (getText() == null) return;
        Log.d(TAG, "绘制游标: " + System.currentTimeMillis());
        String password = getText().toString().trim();
        int length = password.length();

        int blankWidth = (getWidth() - mBorderStrokeWidth * (mPasswordNumber + 1)) / mPasswordNumber;

        if (length < mPasswordNumber && mCursorFlag) {
            int x = mBorderStrokeWidth + (blankWidth >> 1) + length * (mBorderStrokeWidth + blankWidth);
            canvas.drawLine(x, getHeight() * 0.3f, x, getHeight() * 0.7f, mPaintBorder);
        }
        mCursorFlag = !mCursorFlag;

    }

//    @Nullable
//    @Override
//    public Editable getText() {
//        super.getText();
//        Log.d(TAG, "getting password by method getText is forbidden !");
//        return null;
//    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(@NonNull Editable s) {
        String password = s.toString().trim();
        if (mOnPasswordChangeListener == null) return;
        if (password.length() < mPasswordNumber) {
            mOnPasswordChangeListener.onChange(password);
        } else {
            password = password.substring(0, mPasswordNumber);
            mOnPasswordChangeListener.onComplete(password);
        }
    }

    public interface OnPasswordChangeListener {

        void onChange(String password);

        void onComplete(String password);
    }

    public void setOnPasswordChangeListener(OnPasswordChangeListener listener) {
        this.mOnPasswordChangeListener = listener;
    }
}