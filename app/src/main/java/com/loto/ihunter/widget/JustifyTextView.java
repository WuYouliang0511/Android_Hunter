package com.loto.ihunter.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class JustifyTextView extends androidx.appcompat.widget.AppCompatTextView {
    public static final String TAG = JustifyTextView.class.getSimpleName();

    private Paint paint    = new Paint();
    private Rect  rect_0   = new Rect();
    private Rect  rect_l   = new Rect();
    private Rect  rect_tmp = new Rect();

    public JustifyTextView(Context context) {
        super(context);
    }

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JustifyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence cs = getText();
        String txt = null;
        if (cs == null)
            txt = "";
        else
            txt = cs.toString();
        if (txt.length() == 0)
            super.onDraw(canvas);
        else {
            float textSize = getTextSize();
            Typeface typeface = getTypeface();
            int color = getCurrentTextColor();

            int width = getWidth();
            int height = getHeight();
            int left = getPaddingLeft();
            int right = width - getPaddingRight();
            int top = getPaddingTop();
            int bottom = height - getPaddingBottom();
            paint.setAntiAlias(true);
            paint.setTypeface(typeface);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(color);
            int centerY = (bottom + top) / 2;
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float y = centerY + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent; // 垂直方向也居中;

            canvas.drawLine(0,fontMetrics.bottom,getWidth(),fontMetrics.bottom,paint);
            canvas.drawLine(0,fontMetrics.descent,getWidth(),fontMetrics.descent,paint);

            paint.getTextBounds(txt, 0, 1, rect_0);   // 获取第一个字符的大小
            paint.getTextBounds(txt, 0, 1, rect_l);   // 获取最后一个字符的大小
            float space = (right - left - (rect_0.width() * 0.5f) - (rect_l.width() * 0.5f)) / (txt.length() - 1);
            for (int i = 0, iMax = txt.length(); i < iMax; ++i) {
                float x = 0;
                if (i == 0)
                    x = left;
                else if (i == iMax - 1)
                    x = right - rect_l.width();
                else {
                    paint.getTextBounds(txt, i, i + 1, rect_tmp);   // 获取每一个字符的大小
                    x = left + rect_0.width() * 0.5f + i * space - rect_tmp.width() * 0.5f;
                }
                canvas.drawText(txt, i, i + 1, x, y, paint);
            }
        }
    }
}

