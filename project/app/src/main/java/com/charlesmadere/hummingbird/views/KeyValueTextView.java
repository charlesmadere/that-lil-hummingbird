package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;

public class KeyValueTextView extends AppCompatTextView {

    private ForegroundColorSpan mSecondaryTextColorSpan;


    public KeyValueTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyValueTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSecondaryTextColorSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.secondaryText));
    }

    public void setText(final CharSequence key, final CharSequence value) {
        if (TextUtils.isEmpty(key) && TextUtils.isEmpty(value)) {
            setText("");
        } else if (TextUtils.isEmpty(value)) {
            setText(key);
        } else {
            final SpannableStringBuilder spannable = new SpannableStringBuilder(value);
            spannable.setSpan(mSecondaryTextColorSpan, 0, spannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.insert(0, " ");
            spannable.insert(0, key);
            setText(spannable);
        }
    }

}
