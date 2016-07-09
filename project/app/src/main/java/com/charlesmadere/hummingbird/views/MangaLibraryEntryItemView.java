package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;

import butterknife.ButterKnife;

public class MangaLibraryEntryItemView extends CardView implements AdapterView<MangaLibraryEntry>,
        View.OnClickListener {

    private OnEditClickListener mListener;


    public MangaLibraryEntryItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaLibraryEntryItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View view) {
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
    public void setContent(final MangaLibraryEntry content) {
        // TODO
    }

    public void setOnEditClickListener(@Nullable final OnEditClickListener l) {
        mListener = l;
        // TODO
    }


    public interface OnEditClickListener {
        void onEditClick(final MangaLibraryEntryItemView v);
    }

}
