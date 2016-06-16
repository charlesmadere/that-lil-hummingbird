package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AppNews;

import butterknife.ButterKnife;

public class AppNewsItemView extends CardView implements AdapterView<AppNews> {

    // TODO


    public AppNewsItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AppNewsItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final AppNews content) {
        // TODO
    }

}
