package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Season;

import java.text.NumberFormat;

public class SeasonItemView extends AppCompatTextView implements AdapterView<Season> {

    private NumberFormat mNumberFormat;


    public SeasonItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SeasonItemView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final Season content) {
        setText(getResources().getString(R.string.season_x,
                mNumberFormat.format(content.getSeason())));
    }

}
