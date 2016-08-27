package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AppNewsAdapter;
import com.charlesmadere.hummingbird.fragments.AppNewsFragment;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.models.AppNewsStatus;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.AppNewsItemView;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

public class AppNewsActivity extends BaseDrawerActivity implements AppNewsItemView.OnClickListener,
        ObjectCache.KeyProvider, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AppNewsActivity";

    private AppNewsAdapter mAdapter;
    private ArrayList<AppNews> mAppNews;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, AppNewsActivity.class);
    }

    private void fetchAppNews() {
        mRefreshLayout.setRefreshing(true);
        Api.getAppNews(new GetAppNewsListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName() };
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.APP_NEWS;
    }

    @Override
    public void onClick(final AppNewsItemView v) {
        AppNewsFragment.create(v.getAppNews()).show(getSupportFragmentManager(),
                AppNewsFragment.TAG);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_news);

        mAppNews = ObjectCache.get(this);

        if (mAppNews == null || mAppNews.isEmpty()) {
            fetchAppNews();
        } else {
            showAppNews(mAppNews);
        }
    }

    @Override
    public void onRefresh() {
        fetchAppNews();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAppNews != null) {
            ObjectCache.put(mAppNews, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        DividerItemDecoration.apply(mRecyclerView);
        mAdapter = new AppNewsAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showAppNews(final ArrayList<AppNews> appNews) {
        mAppNews = appNews;
        mAdapter.set(appNews);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetAppNewsListener implements ApiResponse<ArrayList<AppNews>> {
        private final WeakReference<AppNewsActivity> mActivityReference;

        private GetAppNewsListener(final AppNewsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AppNewsActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(@Nullable final ArrayList<AppNews> appNews) {
            final AppNewsActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                final AppNewsStatus appNewsStatus = Preferences.Misc.AppNewsAvailability.get();

                if (appNewsStatus != null) {
                    appNewsStatus.setImportantNewsAvailable(false);
                    appNewsStatus.updatePollTime();
                    Preferences.Misc.AppNewsAvailability.set(appNewsStatus);
                }

                if (appNews == null || appNews.isEmpty()) {
                    activity.showEmpty();
                } else {
                    activity.showAppNews(appNews);
                }
            }
        }
    }

}
