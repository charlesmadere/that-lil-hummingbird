package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.TypefaceEntry;
import com.charlesmadere.hummingbird.models.User;

public class CommentTitleTextView extends AppCompatTextView implements AdapterView<CommentStory> {

    private CustomTypefaceSpan mPrimaryNameSpan;
    private CustomTypefaceSpan mSecondaryNameSpan;
    private CustomTypefaceSpan mSecondaryTypefaceSpan;
    private ForegroundColorSpan mSecondaryColorSpan;
    private ImageSpan mIconSpan;
    private RelativeSizeSpan mSecondarySizeSpan;


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

        final Context context = getContext();
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.View);
        final int typefaceEntryOrdinal = ta.getInt(R.styleable.View_typeface,
                TypefaceEntry.OSWALD_BOLD.ordinal());
        final int secondaryTypefaceEntryOrdinal = ta.getInt(R.styleable.View_secondary_typeface,
                TypefaceEntry.OPEN_SANS_REGULAR.ordinal());
        ta.recycle();

        mPrimaryNameSpan = new CustomTypefaceSpan(typefaceEntryOrdinal);
        mSecondaryNameSpan = new CustomTypefaceSpan(typefaceEntryOrdinal);
        mSecondaryColorSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(context,
                android.R.attr.textColorSecondary));
        mSecondarySizeSpan = new RelativeSizeSpan(0.75f);
        mSecondaryTypefaceSpan = new CustomTypefaceSpan(secondaryTypefaceEntryOrdinal);

        final Drawable iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_arrow_end_18dp);
        iconDrawable.setBounds(0, 0, iconDrawable.getIntrinsicWidth(), iconDrawable.getIntrinsicHeight());
        mIconSpan = new ImageSpan(iconDrawable, ImageSpan.ALIGN_BASELINE);
    }

    @Override
    public void setContent(final CommentStory content) {
        if (content.hasGroupId()) {
            setText(content.getPoster(), content.getGroup());
        } else if (content.isPosterAndUserIdentical()) {
            setText(content.getPoster());
        } else {
            setText(content.getPoster(), content.getUser());
        }
    }

    private void setText(final User user) {
        final SpannableString spannable = new SpannableString(user.getId());
        spannable.setSpan(mPrimaryNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

    private void setText(final User poster, final User receiver) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder(poster.getId());
        spannable.setSpan(mPrimaryNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        int length = spannable.length();
        spannable.append(' ');
        spannable.setSpan(mIconSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        length = spannable.length();
        spannable.append(receiver.getId());
        spannable.setSpan(mSecondaryNameSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

    private void setText(final User user, final Group group) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(user.getId());
        spannable.setSpan(mPrimaryNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        int length = spannable.length();
        spannable.append(getResources().getText(R.string.posted_to));
        spannable.setSpan(mSecondaryColorSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondarySizeSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondaryTypefaceSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        length = spannable.length();
        spannable.append(group.getName());
        spannable.setSpan(mSecondaryNameSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

    private void setText(final Group group) {
        final SpannableString spannable = new SpannableString(group.getName());
        spannable.setSpan(mPrimaryNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannable);
    }

}
