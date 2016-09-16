package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.FollowedStoryActivity;
import com.charlesmadere.hummingbird.activities.MediaStoryActivity;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.UiColorSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowMoreFeedButton extends FrameLayout implements View.OnClickListener {

    private FollowedStory mFollowedStory;
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
        final Context context = getContext();
        final Activity activity = MiscUtils.optActivity(context);
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        if (mFollowedStory != null) {
            context.startActivity(FollowedStoryActivity.getLaunchIntent(context, mFollowedStory,
                    uiColorSet));
        } else if (mMediaStory != null) {
            context.startActivity(MediaStoryActivity.getLaunchIntent(context, mMediaStory,
                    uiColorSet));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    public void setContent(@Nullable final FollowedStory content) {
        mFollowedStory = content;
        mMediaStory = null;
    }

    public void setContent(@Nullable final MediaStory content) {
        mMediaStory = content;
        mFollowedStory = null;
    }

}
