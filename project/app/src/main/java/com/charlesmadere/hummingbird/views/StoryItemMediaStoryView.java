package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.Story;
import com.charlesmadere.hummingbird.models.Substory;
import com.charlesmadere.hummingbird.models.User;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryItemMediaStoryView extends CardView implements AdapterView<Story>,
        View.OnClickListener {

    private Story mStory;

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


    public StoryItemMediaStoryView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryItemMediaStoryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Story getStory() {
        return mStory;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mStory.getMedia()));
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

        final AbsAnime media = mStory.getMedia();
        mTitle.setText(media.getTitle());
        mShowType.setText(media.getShowType().getTextResId());

        if (media.hasGenres()) {
            mGenres.setText(media.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        mPoster.setImageURI(Uri.parse(media.getCoverImage()));
        final ArrayList<Substory> substories = mStory.getSubstories();
        final User user = mStory.getUser();

        if (substories.size() >= 3) {
            mSubstoryTwo.setContent(user, substories.get(2));
            mSubstoryTwo.setVisibility(VISIBLE);
        } else {
            mSubstoryTwo.setVisibility(GONE);
        }

        if (substories.size() >= 2) {
            mSubstoryOne.setContent(user, substories.get(1));
            mSubstoryOne.setVisibility(VISIBLE);
        } else {
            mSubstoryOne.setVisibility(GONE);
        }

        mSubstoryZero.setContent(user, substories.get(0));
    }

}
