package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ShareUtils;
import com.charlesmadere.hummingbird.models.AbsStory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareFeedButton extends FrameLayout implements AdapterView<AbsStory>,
        View.OnClickListener {

    private AbsStory mAbsStory;

    @BindView(R.id.tvShareFeedButton)
    TextView mLabel;


    public ShareFeedButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ShareFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShareFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View view) {
        if (mAbsStory != null) {
            ShareUtils.shareStory(MiscUtils.getActivity(getContext()), mAbsStory);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final AbsStory content) {
        mAbsStory = content;
    }

}
