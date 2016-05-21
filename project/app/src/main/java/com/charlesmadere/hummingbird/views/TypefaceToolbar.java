package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class TypefaceToolbar extends Toolbar {

    private CustomTypefaceSpan mSubtitleTypefaceSpan;
    private CustomTypefaceSpan mTitleTypefaceSpan;


    public TypefaceToolbar(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public TypefaceToolbar(final Context context, @Nullable final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(@Nullable final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        int titleTypefaceEntry = TypefaceEntry.OPEN_SANS_BOLD.ordinal();
        int subtitleTypefaceEntry = TypefaceEntry.OPEN_SANS_SEMIBOLD.ordinal();

        if (attrs != null) {
            final Context context = getContext();
            final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
            titleTypefaceEntry = ta.getInt(R.styleable.View_typeface, titleTypefaceEntry);
            subtitleTypefaceEntry = ta.getInt(R.styleable.View_secondary_typeface,
                    subtitleTypefaceEntry);
            ta.recycle();
        }

        mTitleTypefaceSpan = new CustomTypefaceSpan(titleTypefaceEntry);
        mSubtitleTypefaceSpan = new CustomTypefaceSpan(subtitleTypefaceEntry);
    }

    @Override
    public void setSubtitle(@Nullable final CharSequence subtitle) {
        super.setSubtitle(mSubtitleTypefaceSpan.apply(subtitle));
    }

    @Override
    public void setTitle(@Nullable final CharSequence title) {
        super.setTitle(mTitleTypefaceSpan.apply(title));
    }

}
