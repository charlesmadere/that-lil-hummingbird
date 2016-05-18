package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class TypefaceToolbar extends Toolbar {

    private CustomTypefaceSpan mCustomTypefaceSpan;


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

        int typefaceEntryOrdinal = TypefaceEntry.OPEN_SANS_EXTRA_BOLD.ordinal();

        if (attrs != null) {
            final Context context = getContext();
            final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
            typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface, typefaceEntryOrdinal);
            ta.recycle();
        }

        mCustomTypefaceSpan = new CustomTypefaceSpan(typefaceEntryOrdinal);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            final SpannableString spannable = new SpannableString(title);
            spannable.setSpan(mCustomTypefaceSpan, 0, spannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            title = spannable;
        }

        super.setTitle(title);
    }

}
