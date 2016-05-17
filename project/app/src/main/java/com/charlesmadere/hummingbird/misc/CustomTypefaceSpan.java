package com.charlesmadere.hummingbird.misc;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import com.charlesmadere.hummingbird.models.TypefaceEntry;

public class CustomTypefaceSpan extends MetricAffectingSpan {

    private final Typeface mTypeface;


    public CustomTypefaceSpan(final int typefaceEntryOrdinal) {
        mTypeface = TypefaceStore.get(typefaceEntryOrdinal);
    }

    public CustomTypefaceSpan(final TypefaceEntry typefaceEntry) {
        mTypeface = TypefaceStore.get(typefaceEntry);
    }

    private void apply(final Paint paint) {
        final Typeface oldTypeface = paint.getTypeface();
        final int oldStyle = oldTypeface == null ? 0 : oldTypeface.getStyle();
        final int fakeStyle = oldStyle & ~mTypeface.getStyle();

        if ((fakeStyle & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fakeStyle & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(mTypeface);
    }

    @Override
    public void updateDrawState(final TextPaint tp) {
        apply(tp);
    }

    @Override
    public void updateMeasureState(final TextPaint p) {
        apply(p);
    }

}
