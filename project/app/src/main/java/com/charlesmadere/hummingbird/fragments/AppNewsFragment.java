package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AppNews;

import butterknife.BindView;

public class AppNewsFragment extends BaseBottomSheetDialogFragment {

    public static final String TAG = "AppNewsFragment";
    private static final String KEY_APP_NEWS = "AppNews";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


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

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_app_news, container, false);
    }

}
