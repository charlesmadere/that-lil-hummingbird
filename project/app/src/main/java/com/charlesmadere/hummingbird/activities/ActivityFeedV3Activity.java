package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.networking.ApiV3;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class ActivityFeedV3Activity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ActivityFeedV3Activity";

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context) {
        final Intent intent = createDrawerActivityIntent(context, ActivityFeedV3Activity.class);

        if (Preferences.General.DefaultLaunchScreen.get() == LaunchScreen.ACTIVITY_FEED) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        return intent;
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.ACTIVITY_FEED;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_feed_v3);

        ApiV3.getGlobalFeed(new ApiResponse<ArrayResponse<ActionGroup>>() {
            @Override
            public void failure(@Nullable final ErrorInfo error) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(@Nullable final ArrayResponse<ActionGroup> object) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {

    }

}
