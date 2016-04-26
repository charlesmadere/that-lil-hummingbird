package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsAnime;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeItemView extends CardView implements AdapterView<AbsAnime>,
        View.OnClickListener {

    private AbsAnime mAnime;
    private NumberFormat mNumberFormat;

    @BindView(R.id.tvEpisodeCount)
    TextView mEpisodeCount;

    @BindView(R.id.tvGenres)
    TextView mGenres;

    @BindView(R.id.tvShowType)
    TextView mShowType;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public AnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsAnime getAnime() {
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

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AbsAnime content) {
        mAnime = content;
        mTitle.setText(mAnime.getTitle());
        mShowType.setText(mAnime.getShowType().getTextResId());

        if (mAnime.hasGenres()) {
            mGenres.setText(mAnime.getGenresString(getResources()));
            mGenres.setVisibility(VISIBLE);
        } else {
            mGenres.setVisibility(GONE);
        }

        if (mAnime.hasEpisodeCount()) {
            mEpisodeCount.setText(getResources().getQuantityString(R.plurals.x_episodes,
                    mAnime.getEpisodeCount(), mNumberFormat.format(mAnime.getEpisodeCount())));
            mEpisodeCount.setVisibility(VISIBLE);
        } else {
            mEpisodeCount.setVisibility(GONE);
        }
    }

}
