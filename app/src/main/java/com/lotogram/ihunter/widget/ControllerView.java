package com.lotogram.ihunter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lotogram.ihunter.R;

public class ControllerView extends ConstraintLayout {

    private static final String TAG = "ControllerView";

    private ImageButton mImageCenter;
    private ImageButton mImageUp;
    private ImageButton mImageDown;
    private ImageButton mImageLeft;
    private ImageButton mImageRight;

    public ControllerView(@NonNull Context context) {
        this(context, null);
    }

    public ControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        initAttrs(context, attrs);
    }

    private void initViews() {
        mImageCenter = new ImageButton(getContext());
        mImageCenter.setId(View.generateViewId());
        mImageCenter.setPadding(0, 0, 0, 0);
        mImageCenter.setBackground(null);
        LayoutParams lp0 = new LayoutParams(1, 1);
        lp0.startToStart = 0;
        lp0.topToTop = 0;
        lp0.endToEnd = 0;
        lp0.bottomToBottom = 0;
        mImageCenter.setLayoutParams(lp0);
        addView(mImageCenter, 0);

        mImageLeft = new ImageButton(getContext());
        mImageLeft.setBackground(null);
        mImageLeft.setPadding(0, 0, 0, 0);
        LayoutParams lp1 = new LayoutParams(-2, -2);
        lp1.topToTop = mImageCenter.getId();
        lp1.endToStart = mImageCenter.getId();
        lp1.bottomToBottom = mImageCenter.getId();
        mImageLeft.setLayoutParams(lp1);
        addView(mImageLeft, 1);

        mImageUp = new ImageButton(getContext());
        mImageUp.setBackground(null);
        mImageUp.setPadding(0, 0, 0, 0);
        LayoutParams lp2 = new LayoutParams(-2, -2);
        lp2.startToStart = mImageCenter.getId();
        lp2.endToEnd = mImageCenter.getId();
        lp2.bottomToTop = mImageCenter.getId();
        mImageUp.setLayoutParams(lp2);
        addView(mImageUp, 2);

        mImageRight = new ImageButton(getContext());
        mImageRight.setBackground(null);
        mImageRight.setPadding(0, 0, 0, 0);
        LayoutParams lp3 = new LayoutParams(-2, -2);
        lp3.topToTop = mImageCenter.getId();
        lp3.startToEnd = mImageCenter.getId();
        lp3.bottomToBottom = mImageCenter.getId();
        mImageRight.setLayoutParams(lp3);
        addView(mImageRight, 3);

        mImageDown = new ImageButton(getContext());
        mImageDown.setBackground(null);
        mImageDown.setPadding(0, 0, 0, 0);
        LayoutParams lp4 = new LayoutParams(-2, -2);
        lp4.topToBottom = mImageCenter.getId();
        lp4.startToStart = mImageCenter.getId();
        lp4.endToEnd = mImageCenter.getId();
        mImageDown.setLayoutParams(lp4);
        addView(mImageDown, 4);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ControllerView);
        setCenterImage(ta.getResourceId(R.styleable.ControllerView_center, 0));
        setLeftImage(ta.getResourceId(R.styleable.ControllerView_left, 0));
        setUpImage(ta.getResourceId(R.styleable.ControllerView_up, 0));
        setRightImage(ta.getResourceId(R.styleable.ControllerView_right, 0));
        setDownImage(ta.getResourceId(R.styleable.ControllerView_down, 0));
        setInterval(ta.getDimensionPixelSize(R.styleable.ControllerView_interval, 0));
        ta.recycle();
    }

    public void setCenterImage(@DrawableRes int res) {
        mImageCenter.setImageResource(res);
    }

    public void setLeftImage(@DrawableRes int res) {
        mImageLeft.setImageResource(res);
    }

    public void setUpImage(@DrawableRes int res) {
        mImageUp.setImageResource(res);
    }

    public void setRightImage(@DrawableRes int res) {
        mImageRight.setImageResource(res);
    }

    public void setDownImage(@DrawableRes int res) {
        mImageDown.setImageResource(res);
    }

    public void setInterval(int interval) {
        LayoutParams left = (LayoutParams) mImageLeft.getLayoutParams();
        left.rightMargin = interval;
        LayoutParams up = (LayoutParams) mImageUp.getLayoutParams();
        up.bottomMargin = interval;
        LayoutParams right = (LayoutParams) mImageRight.getLayoutParams();
        right.leftMargin = interval;
        LayoutParams down = (LayoutParams) mImageDown.getLayoutParams();
        down.topMargin = interval;
    }
}