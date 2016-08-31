package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeQuoteItemView extends FrameLayout implements AdapterView<AnimeDigest.Quote> {

    @BindView(R.id.likeTextView)
    LikeTextView mLikeTextView;

    @BindView(R.id.tvName)
    TextView mName;

    @BindView(R.id.tvQuote)
    TextView mQuote;


    public AnimeQuoteItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeQuoteItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimeQuoteItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AnimeDigest.Quote content) {
        mLikeTextView.setContent(content);
        mQuote.setText(content.getContent());

        if (content.hasCharacterName()) {
            mName.setText(content.getCharacterName());
            mName.setVisibility(VISIBLE);
        } else {
            mName.setVisibility(GONE);
        }
    }

}
