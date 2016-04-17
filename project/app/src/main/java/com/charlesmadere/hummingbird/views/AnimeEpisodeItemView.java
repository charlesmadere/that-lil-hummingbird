package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeEpisode;
import com.charlesmadere.hummingbird.models.SimpleDate;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimeEpisodeItemView extends CardView implements AdapterView<AnimeEpisode> {

    private NumberFormat mNumberFormat;

    @Bind(R.id.tvEpisodeNumber)
    TextView mEpisodeNumber;

    @Bind(R.id.tvSynopsis)
    TextView mSynopsis;

    @Bind(R.id.tvAirDate)
    TextView mAirDate;

    @Bind(R.id.tvTitle)
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

        final SimpleDate airDate = content.getAirDate();
        if (airDate == null) {
            mAirDate.setVisibility(GONE);
        } else {
            mAirDate.setText(airDate.getRelativeDateTimeText(getContext()));
            mAirDate.setVisibility(VISIBLE);
        }

        if (content.hasSynopsis()) {
            mSynopsis.setText(content.getSynopsis());
            mSynopsis.setVisibility(VISIBLE);
        } else {
            mSynopsis.setVisibility(GONE);
        }
    }

}
