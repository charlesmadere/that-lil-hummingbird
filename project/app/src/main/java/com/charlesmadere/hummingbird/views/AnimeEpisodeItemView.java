package com.charlesmadere.hummingbird.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeEpisodeItemView extends CardView implements AdapterView<AnimeDigest.Episode>,
        View.OnClickListener {

    private AnimeDigest.Episode mEpisode;
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

    public AnimeDigest.Episode getEpisode() {
        return mEpisode;
    }

    @Override
    public void onClick(final View view) {
        final Activity activity = MiscUtils.getActivity(getContext());

        if (activity instanceof OnClickListener) {
            ((OnClickListener) activity).onClick(this);
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
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AnimeDigest.Episode content) {
        mEpisode = content;

        if (mEpisode.hasThumbnail()) {
            mThumbnail.setImageURI(Uri.parse(mEpisode.getThumbnail()));
        } else {
            mThumbnail.setImageURI(null);
        }

        mNumber.setText(mNumberFormat.format(mEpisode.getNumber()));
        mTitle.setText(mEpisode.getTitle());

        if (mEpisode.hasSynopsis()) {
            mSynopsis.setText(mEpisode.getSynopsis());
        } else {
            mSynopsis.setText(R.string.no_synopsis_available);
        }
    }


    public interface OnClickListener {
        void onClick(final AnimeEpisodeItemView v);
    }

}
