package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaStoryItemView extends CardView implements AdapterView<MediaStory>,
        View.OnClickListener {

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.simssvZero)
    StoryItemMediaStorySubstoryView mSubstoryZero;

    @BindView(R.id.simssvOne)
    StoryItemMediaStorySubstoryView mSubstoryOne;

    @BindView(R.id.simssvTwo)
    StoryItemMediaStorySubstoryView mSubstoryTwo;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvShowType)
    TextView mShowType;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public MediaStoryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaStoryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        // TODO
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
    public void setContent(final MediaStory content) {

    }

}
