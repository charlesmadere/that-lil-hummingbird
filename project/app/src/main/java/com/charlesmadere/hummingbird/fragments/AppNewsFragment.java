package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;

import butterknife.BindView;
import butterknife.OnClick;

public class AppNewsFragment extends BaseBottomSheetDialogFragment {

    public static final String TAG = "AppNewsFragment";
    private static final String KEY_APP_NEWS = "AppNews";

    private AppNews mAppNews;

    @BindView(R.id.ivStar)
    ImageView mStar;

    @BindView(R.id.llLinks)
    LinearLayout mLinks;

    @BindView(R.id.tvBody)
    TextView mBody;

    @BindView(R.id.tvDate)
    TextView mDate;

    @BindView(R.id.tvHead)
    TextView mHead;


    public static AppNewsFragment create(final AppNews appNews) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_APP_NEWS, appNews);

        final AppNewsFragment fragment = new AppNewsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismiss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mAppNews = args.getParcelable(KEY_APP_NEWS);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_app_news, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mAppNews.isImportant()) {
            mStar.setVisibility(View.VISIBLE);
        } else {
            mStar.setVisibility(View.GONE);
        }

        mHead.setText(mAppNews.getHead());
        mDate.setText(mAppNews.getDate().getRelativeTimeText(getContext()));
        mBody.setText(mAppNews.getBody());

        if (mAppNews.hasLinks()) {
            for (final AppNews.Link link : mAppNews.getLinks()) {
                final HeadBodyItemView hbiv = HeadBodyItemView.inflate(mLinks);
                hbiv.setHead(link.getTitle());
                hbiv.setBody(link.getUrl());
                mLinks.addView(hbiv);
            }

            mLinks.setVisibility(View.VISIBLE);
        } else {
            mLinks.setVisibility(View.GONE);
        }
    }

}
