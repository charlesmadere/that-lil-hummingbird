package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsAnime;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimeItemView extends CardView implements AdapterView<AbsAnime>,
        View.OnClickListener {

    @Bind(R.id.tvTitle)
    TextView mTitle;


    public AnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        // TODO
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
        mTitle.setText(content.getTitle());
    }

}
