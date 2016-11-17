package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Anime;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeItemView extends CardView implements AdapterView<Anime>, View.OnClickListener {

    private Anime mAnime;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvAnimeType)
    TextView mType;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public AnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeItemView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Anime getAnime() {
        return mAnime;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mAnime));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final Anime content) {
        mAnime = content;

        mPoster.setImageURI(mAnime.getPosterImage());
        mTitle.setText(mAnime.getTitle());

        if (mAnime.hasType()) {
            // noinspection ConstantConditions
            mType.setText(mAnime.getType().getTextResId());
            mType.setVisibility(VISIBLE);
        } else {
            mType.setVisibility(GONE);
        }

        final Resources res = getResources();

        if (mAnime.hasGenres()) {
            mGenres.setText(mAnime.getGenresString(res));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (mAnime.hasSynopsis()) {
            mSynopsis.setText(mAnime.getSynopsis());
            mSynopsis.setVisibility(VISIBLE);
        } else {
            mSynopsis.setVisibility(GONE);
        }
    }

}
