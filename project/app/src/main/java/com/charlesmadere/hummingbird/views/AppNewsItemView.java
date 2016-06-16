package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AppNews;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppNewsItemView extends CardView implements AdapterView<AppNews> {

    private NumberFormat mNumberFormat;

    @BindView(R.id.internalAppNewsItemView)
    InternalAppNewsItemView mInternalAppNewsItemView;

    @BindView(R.id.tvLinks)
    TextView mLinks;


    public AppNewsItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AppNewsItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AppNews getAppNews() {
        return mInternalAppNewsItemView.getAppNews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AppNews content) {
        mInternalAppNewsItemView.setContent(content);

        if (content.hasLinks()) {
            mLinks.setText(getResources().getQuantityString(R.plurals.x_links,
                    content.getLinks().size(), mNumberFormat.format(content.getLinks().size())));
            mLinks.setVisibility(VISIBLE);
        } else {
            mLinks.setVisibility(GONE);
        }
    }

    public void setOnClickListener(@Nullable final OnClickListener l) {
        if (l == null) {
            setClickable(false);
        } else {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    l.onClick(AppNewsItemView.this);
                }
            });
        }
    }


    public interface OnClickListener {
        void onClick(final AppNewsItemView v);
    }

}
