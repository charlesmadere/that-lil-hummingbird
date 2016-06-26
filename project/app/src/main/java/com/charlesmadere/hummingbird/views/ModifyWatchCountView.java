package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyWatchCountView extends LinearLayout {

    @BindView(R.id.ibDecreaseCount)
    ImageButton mDecrease;

    @BindView(R.id.ibIncreaseCount)
    ImageButton mIncrease;

    @BindView(R.id.tvWatchCount)
    TextView mWatchCount;


    public ModifyWatchCountView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyWatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ModifyWatchCountView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @OnClick(R.id.ibDecreaseCount)
    void onDecreaseClick() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ibIncreaseCount)
    void onIncreaseClick() {

    }

}
