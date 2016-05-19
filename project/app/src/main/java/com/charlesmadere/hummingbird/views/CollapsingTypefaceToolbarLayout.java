package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class CollapsingTypefaceToolbarLayout extends CollapsingToolbarLayout {

    public CollapsingTypefaceToolbarLayout(final Context context,
            @Nullable final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public CollapsingTypefaceToolbarLayout(final Context context,
            @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(@Nullable final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        int typefaceEntryOrdinal = TypefaceEntry.OPEN_SANS_BOLD.ordinal();

        if (attrs != null) {
            final Context context = getContext();
            final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
            typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface, typefaceEntryOrdinal);
            ta.recycle();
        }

        final Typeface typeface = TypefaceStore.get(typefaceEntryOrdinal);
        setCollapsedTitleTypeface(typeface);
        setExpandedTitleTypeface(typeface);
    }

}
