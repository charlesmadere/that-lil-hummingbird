package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaStoryItemView extends CardView implements AdapterView<MediaStory>,
        View.OnClickListener {

    private MediaStory mMediaStory;

    @BindView(R.id.msivZero)
    MediaSubstoryItemView mSubstoryZero;

    @BindView(R.id.msivOne)
    MediaSubstoryItemView mSubstoryOne;

    @BindView(R.id.msivTwo)
    MediaSubstoryItemView mSubstoryTwo;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

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
        final MediaStory.AbsMedia media = mMediaStory.getMedia();

        switch (media.getType()) {
            case ANIME:
                // TODO
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        MediaStory.AbsMedia.Type.class.getName() + ": \"" + media.getType() + '"');
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
    public void setContent(final MediaStory content) {
        mMediaStory = content;
        final MediaStory.AbsMedia media = mMediaStory.getMedia();

        switch (media.getType()) {
            case ANIME:
                setContent((MediaStory.AnimeMedia) media);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        MediaStory.AbsMedia.Type.class.getName() + ": \"" + media.getType() + '"');
        }
    }

    private void setContent(final MediaStory.AnimeMedia media) {
        final AbsAnime anime = media.getAnime();
        mTitle.setText(anime.getTitle());
        mShowType.setText(anime.getShowType().getTextResId());

        if (anime.hasGenres()) {
            mGenres.setText(anime.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        mPoster.setImageURI(Uri.parse(anime.getThumbnail()));
    }

}
