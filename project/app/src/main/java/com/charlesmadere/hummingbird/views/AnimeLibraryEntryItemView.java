package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeLibraryEntryItemView extends CardView implements AdapterView<AnimeLibraryEntry>,
        View.OnClickListener {

    private AnimeLibraryEntry mLibraryEntry;

    @BindView(R.id.animeView)
    InternalAnimeItemView mAnimeView;


    public AnimeLibraryEntryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeLibraryEntryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mLibraryEntry.getAnime()));
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
    public void setContent(final AnimeLibraryEntry content) {
        mLibraryEntry = content;
        mAnimeView.setContent(content);
    }

    public void setOnEditClickListener(@Nullable final InternalAnimeItemView.OnEditClickListener l) {
        mAnimeView.setOnEditClickListener(l);
    }

}
