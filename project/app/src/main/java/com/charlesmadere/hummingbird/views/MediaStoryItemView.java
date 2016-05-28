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
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaStoryItemView extends CardView implements AdapterView<MediaStory>,
        View.OnClickListener {

    private MediaStory mMediaStory;

    @BindView(R.id.msivZero)
    MediaSubstoryItemView mMediaZero;

    @BindView(R.id.msivOne)
    MediaSubstoryItemView mMediaOne;

    @BindView(R.id.msivTwo)
    MediaSubstoryItemView mMediaTwo;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvAnimeType)
    TextView mAnimeType;

    @BindView(R.id.tvGenres)
    TextView mGenres;

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
        final Context context = getContext();

        switch (media.getType()) {
            case ANIME:
                context.startActivity(AnimeActivity.getLaunchIntent(context,
                        ((MediaStory.AnimeMedia) media).getAnime()));
                break;

            case MANGA:
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

        final ArrayList<AbsSubstory> substories = mMediaStory.getSubstories();
        mMediaZero.setContent(substories.get(0), mMediaStory.getUser());

        if (substories.size() >= 2) {
            mMediaOne.setContent(substories.get(1), mMediaStory.getUser());
            mMediaOne.setVisibility(VISIBLE);
        } else {
            mMediaOne.setVisibility(GONE);
        }

        if (substories.size() >= 3) {
            mMediaTwo.setContent(substories.get(2), mMediaStory.getUser());
            mMediaTwo.setVisibility(VISIBLE);
        } else {
            mMediaTwo.setVisibility(GONE);
        }
    }

    private void setContent(final MediaStory.AnimeMedia media) {
        final AbsAnime anime = media.getAnime();
        mPoster.setImageURI(Uri.parse(anime.getImage()));
        mTitle.setText(anime.getTitle());

        if (anime.hasType()) {
            mAnimeType.setText(anime.getType().getTextResId());
            mAnimeType.setVisibility(VISIBLE);
        } else {
            mAnimeType.setVisibility(GONE);
        }

        if (anime.hasGenres()) {
            mGenres.setText(anime.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }
    }

}
