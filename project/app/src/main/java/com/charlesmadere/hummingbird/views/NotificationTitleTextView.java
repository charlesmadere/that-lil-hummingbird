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
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class NotificationTitleTextView extends AppCompatTextView {

    private CustomTypefaceSpan mSecondaryTypefaceSpan;
    private CustomTypefaceSpan mUserNameSpan;
    private ForegroundColorSpan mSecondaryColorSpan;
    private RelativeSizeSpan mSecondarySizeSpan;


    public NotificationTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public NotificationTitleTextView(final Context context, final AttributeSet attrs,
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
                TypefaceEntry.OSWALD_BOLD.ordinal());
        final int secondaryTypefaceEntryOrdinal = ta.getInt(R.styleable.View_secondary_typeface,
                TypefaceEntry.OSWALD_BOLD.ordinal());
        ta.recycle();

        mUserNameSpan = new CustomTypefaceSpan(typefaceEntryOrdinal);
        mSecondaryColorSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(getContext(),
                android.R.attr.textColorSecondary));
        mSecondarySizeSpan = new RelativeSizeSpan(0.75f);
        mSecondaryTypefaceSpan = new CustomTypefaceSpan(secondaryTypefaceEntryOrdinal);
    }

    public void setText(final AbsNotification notification) {
        final AbsNotification.AbsSource source = notification.getSource();

        switch (source.getType()) {
            case STORY:
                setText((AbsNotification.StorySource) source);
                break;

            case SUBSTORY:
                setText((AbsNotification.SubstorySource) source);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" +
                        source.getType() + '"');
        }
    }

    private void setText(final AbsNotification.StorySource source) {
        final AbsStory story = source.getStory();

        switch (story.getType()) {
            case COMMENT:
                setText((CommentStory) story);
                break;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + story.getType() + '"');
        }
    }

    private void setText(final AbsNotification.SubstorySource source) {
        final AbsSubstory substory = source.getSubstory();

        switch (substory.getType()) {
            case REPLY:
                setText((ReplySubstory) substory);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + substory.getType() + '"');
        }
    }

    private void setText(final CommentStory story) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(story.getPosterId());
        spannable.setSpan(mUserNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        final int length = spannable.length();
        spannable.append(getResources().getText(R.string.wrote_a_comment_on_your_profile));
        spannable.setSpan(mSecondaryColorSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondarySizeSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondaryTypefaceSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

    private void setText(final ReplySubstory substory) {
        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append(substory.getUserId());
        spannable.setSpan(mUserNameSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(' ');

        final int length = spannable.length();
        spannable.append(getResources().getText(R.string.wrote_a_reply_to_your_comment));
        spannable.setSpan(mSecondaryColorSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondarySizeSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(mSecondaryTypefaceSpan, length, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(spannable);
    }

}
