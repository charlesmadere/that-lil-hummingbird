package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;

import java.text.NumberFormat;

public class AbsSubstoryTextView extends KeyValueTextView {

    private NumberFormat mNumberFormat;


    public AbsSubstoryTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsSubstoryTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setContent(final AbsSubstory content, final User user) {
        switch (content.getType()) {
            case WATCHED_EPISODE:
                setContent((WatchedEpisodeSubstory) content, user);
                break;

            case WATCHLIST_STATUS_UPDATE:
                setContent((WatchlistStatusUpdateSubstory) content, user);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        AbsSubstory.Type.class.getName() + ": \"" + content.getType() + '"');
        }
    }

    private void setContent(final WatchedEpisodeSubstory content, final User user) {
        setText(user.getId(), getResources().getString(R.string.watched_episode_x,
                mNumberFormat.format(content.getEpisodeNumber())));
    }

    private void setContent(final WatchlistStatusUpdateSubstory content, final User user) {
        setText(user.getId(), getResources().getString(content.getNewStatus().getTextResId()));
    }

}
