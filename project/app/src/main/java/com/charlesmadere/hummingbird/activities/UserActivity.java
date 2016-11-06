package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.ShareUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Liker;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;

import java.lang.ref.WeakReference;

public class UserActivity extends BaseUserActivity implements ObjectCache.KeyProvider {

    private static final String TAG = "UserActivity";
    private static final String CNAME = UserActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".UserId";

    private String mUsername;
    private UserDigest mUserDigest;


    public static Intent getLaunchIntent(final Context context, final Liker liker) {
        return getLaunchIntent(context, liker.getUsername());
    }

    public static Intent getLaunchIntent(final Context context, final User user) {
        return getLaunchIntent(context, user.getId());
    }

    public static Intent getLaunchIntent(final Context context, final String username) {
        return getLaunchIntent(context, username, null);
    }

    public static Intent getLaunchIntent(final Context context, final String username,
            @Nullable final Integer initialTab) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return CurrentUserActivity.getLaunchIntent(context, initialTab);
        } else {
            final Intent intent = new Intent(context, UserActivity.class)
                    .putExtra(EXTRA_USERNAME, username);

            if (initialTab != null) {
                intent.putExtra(EXTRA_INITIAL_TAB, initialTab);
            }

            return intent;
        }
    }

    private void fetchUserDigest() {
        mSimpleProgressView.fadeIn();
        Api.getUserDigest(mUsername, new GetUserDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mUsername };
    }

    @Override
    public UserDigest getUserDigest() {
        return mUserDigest;
    }

    @Override
    public String getUsername() {
        return mUsername;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setTitle(mUsername);

        mUserDigest = ObjectCache.get(this);

        if (mUserDigest == null) {
            fetchUserDigest();
        } else {
            setUserDigest(mUserDigest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAnimeLibrary:
                startActivity(AnimeLibraryActivity.getLaunchIntent(this, mUsername, mUiColorSet));
                return true;

            case R.id.miMangaLibrary:
                startActivity(MangaLibraryActivity.getLaunchIntent(this, mUsername, mUiColorSet));
                return true;

            case R.id.miFollow:
            case R.id.miUnfollow:
                toggleFollowingOfUser();
                return true;

            case R.id.miShare:
                ShareUtils.shareUser(this, mUserDigest);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mUserDigest != null) {
            menu.findItem(R.id.miAnimeLibrary).setVisible(true);
            menu.findItem(R.id.miMangaLibrary).setVisible(true);
            menu.findItem(R.id.miShare).setVisible(true);

            if (mUserDigest.getUser().isFollowed()) {
                menu.findItem(R.id.miUnfollow).setVisible(true);
            } else {
                menu.findItem(R.id.miFollow).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mUserDigest != null) {
            ObjectCache.put(mUserDigest, this);
        }
    }

    @Override
    public void setUserDigest(final UserDigest userDigest) {
        mUserDigest = userDigest;
        super.setUserDigest(userDigest);
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_user_check_network_connection)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchUserDigest();
                    }
                })
                .show();
    }

    private void toggleFollowingOfUser() {
        mUserDigest.getUser().toggleFollowed();
        Api.toggleFollowingOfUser(mUsername);
        supportInvalidateOptionsMenu();
    }


    private static class GetUserDigestListener implements ApiResponse<UserDigest> {
        private final WeakReference<UserActivity> mActivityReference;

        private GetUserDigestListener(final UserActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final UserActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.setUserDigest(userDigest);
            }
        }
    }

}
