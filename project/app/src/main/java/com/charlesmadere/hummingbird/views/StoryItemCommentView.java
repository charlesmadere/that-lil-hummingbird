package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.BaseUserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Story;
import com.charlesmadere.hummingbird.models.Substory;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryItemCommentView extends CardView implements AdapterView<Story>,
        View.OnClickListener {

    private Story mStory;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.tvComment)
    TextView mComment;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public StoryItemCommentView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryItemCommentView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Story getStory() {
        return mStory;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(BaseUserActivity.getLaunchIntent(context, mStory.getPoster()));
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
    public void setContent(final Story content) {
        mStory = content;

        final User poster = mStory.getPoster();
        mAvatar.setImageURI(Uri.parse(poster.getAvatar()));
        mTitle.setText(poster.getName());

        final Substory substory = mStory.getCommentSubstory();
        mTimeAgo.setText(substory.getCreatedAt().getRelativeTimeText(getContext()));
        mComment.setText(substory.getComment());
    }

}
