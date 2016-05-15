package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.UserV2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUserView extends CardView implements AdapterView<UserDigest> {

    @BindView(R.id.tvAbout)
    TextView mAbout;

    @BindView(R.id.tvTitle)
    TextView mTitle;


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

    @Override
    public void setContent(final UserDigest content) {
        final UserV2 user = content.getUser();
        mTitle.setText(getResources().getString(R.string.about_x, user.getName()));

        if (user.hasAbout()) {
            mAbout.setText(user.getAbout());
            mAbout.setVisibility(VISIBLE);
        } else {
            mAbout.setVisibility(GONE);
        }


    }

}
