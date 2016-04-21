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

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimeItemView extends CardView implements AdapterView<AbsAnime>,
        View.OnClickListener {

    private AbsAnime mAnime;

    @Bind(R.id.tvTitle)
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
    }

    @Override
    public void setContent(final AbsAnime content) {
        mAnime = content;
        mTitle.setText(mAnime.getTitle());
    }

}
