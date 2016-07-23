package com.charlesmadere.hummingbird.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.DeepLinkUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.SimpleProgressView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

public class DeepLinkActivity extends BaseActivity {

    private static final String TAG = "DeepLinkActivity";

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;


    private void followDeepLink() {
        final ArrayList<Intent> activityStack = DeepLinkUtils.buildActivityStack(this,
                getIntent());

        // TODO
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Preferences.Account.Username.exists()) {
            setContentView(R.layout.activity_deep_link);

            if (CurrentUser.exists()) {
                followDeepLink();
            } else {
                mSimpleProgressView.show();
                Api.getCurrentUser(new GetCurrentUserListener(this));
            }
        } else {
            startActivity(LoginActivity.getLaunchIntent(this));
            finish();
        }
    }

    private void showError() {
        // TODO
    }


    private static class GetCurrentUserListener implements ApiResponse<UserDigest> {
        private final WeakReference<DeepLinkActivity> mActivityReference;

        private GetCurrentUserListener(final DeepLinkActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final DeepLinkActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final DeepLinkActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.followDeepLink();
            }
        }
    }

}
