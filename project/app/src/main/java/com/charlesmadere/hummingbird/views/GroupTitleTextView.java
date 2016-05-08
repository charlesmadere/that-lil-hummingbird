package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class GroupTitleTextView extends AppCompatTextView {

    private CustomTypefaceSpan mGroupTitleSpan;


    public GroupTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public GroupTitleTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.View);
        final int typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface,
                TypefaceEntry.OPEN_SANS_REGULAR.ordinal());
        ta.recycle();

        mGroupTitleSpan = new CustomTypefaceSpan(TypefaceStore.get(typefaceEntryOrdinal));
    }

    public void setText(@Nullable final CharSequence first, @Nullable final CharSequence second,
            @Nullable final CharSequence third) {
        if (TextUtils.isEmpty(first) && TextUtils.isEmpty(second) && TextUtils.isEmpty(third)) {
            setText("");
            return;
        }

        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        if (!TextUtils.isEmpty(first)) {
            spannable.append(first);
            spannable.setSpan(mGroupTitleSpan, 0, first.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (!TextUtils.isEmpty(second)) {
            if (!TextUtils.isEmpty(spannable)) {
                spannable.append(' ');
            }

            spannable.append(second);
        }

        if (!TextUtils.isEmpty(third)) {
            if (!TextUtils.isEmpty(spannable)) {
                spannable.append(' ');
            }

            final int length = spannable.length();
            spannable.append(third);
            spannable.setSpan(mGroupTitleSpan, length, spannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(spannable);
    }

}
