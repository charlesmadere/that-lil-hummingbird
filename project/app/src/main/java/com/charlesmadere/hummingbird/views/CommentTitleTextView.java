package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.TypefaceStore;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class CommentTitleTextView extends AppCompatTextView {

    private CustomTypefaceSpan mGroupNameSpan;
    private CustomTypefaceSpan mUserNameSpan;
    private ForegroundColorSpan mCenterColorSpan;
    private RelativeSizeSpan mCenterSizeSpan;


    public CommentTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public CommentTitleTextView(final Context context, final AttributeSet attrs,
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

        mGroupNameSpan = new CustomTypefaceSpan(TypefaceStore.get(typefaceEntryOrdinal));
        mUserNameSpan = new CustomTypefaceSpan(TypefaceStore.get(typefaceEntryOrdinal));
        mCenterColorSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(getContext(),
                android.R.attr.textColorSecondary));
        mCenterSizeSpan = new RelativeSizeSpan(0.75f);
    }

    public void setText(final AbsUser user) {
        final SpannableString spannable = new SpannableString(user.getName());
        spannable.setSpan(mUserNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

    public void setText(final Group group) {
        final SpannableString spannable = new SpannableString(group.getName());
        spannable.setSpan(mGroupNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

    public void setText(final AbsUser user, final Group group) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(user.getName());
        spannable.setSpan(mUserNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        int length = spannable.length();
        spannable.append(getResources().getText(R.string.posted_to));
        spannable.setSpan(mCenterColorSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mCenterSizeSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        length = spannable.length();
        spannable.append(group.getName());
        spannable.setSpan(mGroupNameSpan, length, spannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

}
