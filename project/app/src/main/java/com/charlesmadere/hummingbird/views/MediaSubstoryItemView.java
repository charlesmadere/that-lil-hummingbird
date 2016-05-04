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
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaSubstoryItemView extends FrameLayout implements AdapterView<AbsSubstory>,
        View.OnClickListener {

    private AbsSubstory mAbsSubstory;

    @BindView(R.id.kvtvAction)
    KeyValueTextView mAction;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;


    public MediaSubstoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaSubstoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View v) {
        switch (mAbsSubstory.getType()) {
            case WATCHED_EPISODE:
                // TODO
                break;

            case WATCHLIST_STATUS_UPDATE:
                // TODO
                break;

            default:
                throw new IllegalStateException("can't handle click for " +
                        AbsSubstory.Type.class.getName() + ": \"" + mAbsSubstory.getType() + '"');
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final AbsSubstory content) {
        mAbsSubstory = content;

        switch (mAbsSubstory.getType()) {
            case WATCHED_EPISODE:
                setContent((WatchedEpisodeSubstory) mAbsSubstory);
                break;

            case WATCHLIST_STATUS_UPDATE:
                setContent((WatchlistStatusUpdateSubstory) mAbsSubstory);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        AbsSubstory.Type.class.getName() + ": \"" + mAbsSubstory.getType() + '"');
        }
    }

    private void setContent(final WatchedEpisodeSubstory content) {
        // TODO
    }

    private void setContent(final WatchlistStatusUpdateSubstory content) {
        // TODO
    }

}
