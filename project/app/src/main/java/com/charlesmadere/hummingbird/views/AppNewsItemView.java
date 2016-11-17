package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AppNews;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppNewsItemView extends FrameLayout implements AdapterView<AppNews> {

    private AppNews mAppNews;
    private NumberFormat mNumberFormat;

    @BindView(R.id.ivStar)
    ImageView mStar;

    @BindView(R.id.tvBody)
    TextView mBody;

    @BindView(R.id.tvDate)
    TextView mDate;

    @BindView(R.id.tvHead)
    TextView mHead;

    @BindView(R.id.tvLinks)
    TextView mLinks;


    public AppNewsItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AppNewsItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppNewsItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AppNews getAppNews() {
        return mAppNews;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final AppNews content) {
        mAppNews = content;

        mStar.setVisibility(mAppNews.isImportant() ? VISIBLE : GONE);
        mHead.setText(mAppNews.getHead());
        mDate.setText(mAppNews.getDate().getRelativeTimeText(getContext()));
        mBody.setText(mAppNews.getBody());

        if (mAppNews.hasLinks()) {
            // noinspection ConstantConditions
            mLinks.setText(getResources().getQuantityString(R.plurals.x_links,
                    mAppNews.getLinks().size(), mNumberFormat.format(mAppNews.getLinks().size())));
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
