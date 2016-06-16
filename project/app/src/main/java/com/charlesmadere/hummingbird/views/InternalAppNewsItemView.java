package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AppNews;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InternalAppNewsItemView extends LinearLayout {

    private AppNews mAppNews;

    @BindView(R.id.ivStar)
    ImageView mStar;

    @BindView(R.id.tvBody)
    TextView mBody;

    @BindView(R.id.tvDate)
    TextView mDate;

    @BindView(R.id.tvHead)
    TextView mHead;


    public InternalAppNewsItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public InternalAppNewsItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InternalAppNewsItemView(final Context context, final AttributeSet attrs,
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
    }

    public void setContent(final AppNews content) {
        mAppNews = content;

        mStar.setVisibility(content.isImportant() ? VISIBLE : GONE);
        mHead.setText(content.getHead());
        mDate.setText(String.valueOf(content.getEpoch()));
        mBody.setText(content.getBody());
    }

}
