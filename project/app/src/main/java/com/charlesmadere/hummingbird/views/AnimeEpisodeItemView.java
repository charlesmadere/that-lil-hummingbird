package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeEpisode;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeEpisodeItemView extends CardView implements AdapterView<AnimeEpisode> {

    private NumberFormat mNumberFormat;

    @BindView(R.id.tvEpisodeNumber)
    TextView mEpisodeNumber;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

    @BindView(R.id.tvAirDate)
    TextView mAirDate;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public AnimeEpisodeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeEpisodeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AnimeEpisode content) {
        mEpisodeNumber.setText(mNumberFormat.format(content.getNumber()));
        mTitle.setText(content.getTitle());

        if (content.hasAirDate()) {
            mAirDate.setText(content.getAirDate().getRelativeTimeText(getContext()));
            mAirDate.setVisibility(VISIBLE);
        } else {
            mAirDate.setVisibility(GONE);
        }

        if (content.hasSynopsis()) {
            mSynopsis.setText(content.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
