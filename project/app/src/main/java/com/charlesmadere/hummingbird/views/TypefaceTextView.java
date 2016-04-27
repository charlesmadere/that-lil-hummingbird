package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class TypefaceTextView extends AppCompatTextView {

    public TypefaceTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public TypefaceTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.View);
        final int typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface,
                TypefaceEntry.OPEN_SANS_REGULAR.ordinal());
        ta.recycle();

        setTypeface(typefaceEntryOrdinal);
    }

    public void setTypeface(final int typefaceEntryOrdinal) {
        setTypeface(TypefaceStore.get(typefaceEntryOrdinal));
    }

    public void setTypeface(final TypefaceEntry typefaceEntry) {
        setTypeface(TypefaceStore.get(typefaceEntry));
    }

}
