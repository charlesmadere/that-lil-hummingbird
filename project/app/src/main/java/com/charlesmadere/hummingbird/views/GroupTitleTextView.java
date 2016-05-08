package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.Group;
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
                TypefaceEntry.TEKO_SEMIBOLD.ordinal());
        ta.recycle();

        mGroupTitleSpan = new CustomTypefaceSpan(TypefaceStore.get(typefaceEntryOrdinal));
    }

    public void setText(final AbsUser user) {
        final SpannableString spannable = new SpannableString(user.getName());
        spannable.setSpan(mGroupTitleSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

    public void setText(final Group group) {
        final SpannableString spannable = new SpannableString(group.getName());
        spannable.setSpan(mGroupTitleSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

    public void setText(final AbsUser user, final Group group) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(user.getName());
        spannable.setSpan(mGroupTitleSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannable.append(' ');
        spannable.append(getResources().getText(R.string.posted_to));
        spannable.append(' ');

        final int length = spannable.length();
        spannable.append(group.getName());
        spannable.setSpan(mGroupTitleSpan, length, spannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

}
