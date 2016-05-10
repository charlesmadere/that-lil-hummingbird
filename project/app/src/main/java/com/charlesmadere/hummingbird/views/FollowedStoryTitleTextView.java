package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

import java.text.NumberFormat;

public class FollowedStoryTitleTextView extends AppCompatTextView {

    private CustomTypefaceSpan mUserNameSpan;
    private ForegroundColorSpan mSuffixColorSpan;
    private NumberFormat mNumberFormat;
    private RelativeSizeSpan mSuffixSizeSpan;


    public FollowedStoryTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public FollowedStoryTitleTextView(final Context context, final AttributeSet attrs,
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

        mUserNameSpan = new CustomTypefaceSpan(TypefaceStore.get(typefaceEntryOrdinal));
        mSuffixColorSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(getContext(),
                android.R.attr.textColorSecondary));
        mSuffixSizeSpan = new RelativeSizeSpan(0.75f);
        mNumberFormat = NumberFormat.getInstance();
    }

    public void setText(final String username, final int followedCount) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(username);
        spannable.setSpan(mUserNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        final int length = spannable.length();
        spannable.append(getResources().getQuantityString(R.plurals.followed_x_users, followedCount,
                mNumberFormat.format(followedCount)));
        spannable.setSpan(mSuffixColorSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSuffixSizeSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

}
