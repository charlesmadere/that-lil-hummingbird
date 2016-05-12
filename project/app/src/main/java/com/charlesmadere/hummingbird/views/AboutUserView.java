package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUserView extends CardView {

    @BindView(R.id.tvAbout)
    TextView mAbout;

    @BindView(R.id.tvBio)
    TextView mBio;


    public AboutUserView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutUserView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

}
