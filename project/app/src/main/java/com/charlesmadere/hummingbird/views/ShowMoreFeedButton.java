package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.MediaStoryActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.MediaStory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowMoreFeedButton extends FrameLayout implements AdapterView<MediaStory>,
        View.OnClickListener {

    private MediaStory mMediaStory;

    @BindView(R.id.tvShowMoreFeedButton)
    TextView mLabel;


    public ShowMoreFeedButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowMoreFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShowMoreFeedButton(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View view) {
        if (mMediaStory != null) {
            final Context context = getContext();
            context.startActivity(MediaStoryActivity.getLaunchIntent(context, mMediaStory));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final MediaStory content) {
        mMediaStory = content;
    }

}
