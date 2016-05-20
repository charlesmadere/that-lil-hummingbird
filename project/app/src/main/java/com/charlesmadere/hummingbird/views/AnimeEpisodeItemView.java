package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeEpisodeItemView extends CardView implements AdapterView<AnimeDigest.Episode> {

    private NumberFormat mNumberFormat;

    @BindView(R.id.sdvThumbnail)
    SimpleDraweeView mThumbnail;

    @BindView(R.id.tvNumber)
    TextView mNumber;

    @BindView(R.id.tvSynopsis)
    TextView mSynopsis;

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
    public void setContent(final AnimeDigest.Episode content) {
        if (content.hasThumbnail()) {
            mThumbnail.setImageURI(Uri.parse(content.getThumbnail()));
        } else {
            // TODO
        }

        mNumber.setText(mNumberFormat.format(content.getNumber()));
        mTitle.setText(content.getTitle());

        if (content.hasSynopsis()) {
            mSynopsis.setText(content.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }

}
