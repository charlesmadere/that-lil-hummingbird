package com.charlesmadere.hummingbird.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

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

import butterknife.BindView;

public class DeepLinkActivity extends BaseActivity {

    private static final String TAG = "DeepLinkActivity";

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;


    private void fetchCurrentUser() {
        mSimpleProgressView.fadeIn();
        Api.getCurrentUser(new GetCurrentUserListener(this));
    }

    private void followDeepLink() {
        final Intent[] activityStack = DeepLinkUtils.buildActivityStack(this);

        if (activityStack == null || activityStack.length == 0) {
            Toast.makeText(this, R.string.deep_link_error, Toast.LENGTH_LONG).show();
            startActivity(HomeActivity.getLaunchIntent(this));
        } else {
            ContextCompat.startActivities(this, activityStack);
        }

        finish();
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
                fetchCurrentUser();
            }
        } else {
            startActivity(LoginActivity.getLaunchIntent(this));
            finish();
        }
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_your_profile_check_network_connection)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchCurrentUser();
                    }
                })
                .show();
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
