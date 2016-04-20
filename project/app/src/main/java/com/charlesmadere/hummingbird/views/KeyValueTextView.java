package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;

public class KeyValueTextView extends AppCompatTextView {

    private ForegroundColorSpan mKeyTextSpan;
    private ForegroundColorSpan mValueTextSpan;


    public KeyValueTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public KeyValueTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.KeyValueTextView);
        final int keyTextColor = ta.getColor(R.styleable.KeyValueTextView_keyTextColor,
                MiscUtils.getAttrColor(getContext(), R.attr.textColorSecondary));
        final int valueTextColor = ta.getColor(R.styleable.KeyValueTextView_valueTextColor,
                MiscUtils.getAttrColor(getContext(), R.attr.textColorTertiary));
        ta.recycle();

        mKeyTextSpan = new ForegroundColorSpan(keyTextColor);
        mValueTextSpan = new ForegroundColorSpan(valueTextColor);
    }

    public void setText(@Nullable final CharSequence key, @Nullable final CharSequence value) {
        if (TextUtils.isEmpty(key) && TextUtils.isEmpty(value)) {
            setText("");
            return;
        }

        final SpannableStringBuilder spannable = new SpannableStringBuilder();

        if (!TextUtils.isEmpty(key)) {
            spannable.append(key);
            spannable.setSpan(mKeyTextSpan, 0, key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (!TextUtils.isEmpty(value)) {
            if (!TextUtils.isEmpty(spannable)) {
                spannable.append(' ');
            }

            final int length = spannable.length();
            spannable.append(value);
            spannable.setSpan(mValueTextSpan, length, spannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(spannable);
    }

}
