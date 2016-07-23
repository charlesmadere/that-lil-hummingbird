package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

import static com.charlesmadere.hummingbird.misc.Constants.ANIME;
import static com.charlesmadere.hummingbird.misc.Constants.FOLLOWERS;
import static com.charlesmadere.hummingbird.misc.Constants.FOLLOWING;
import static com.charlesmadere.hummingbird.misc.Constants.GROUPS;
import static com.charlesmadere.hummingbird.misc.Constants.HUMMINGBIRD_URL;
import static com.charlesmadere.hummingbird.misc.Constants.LIBRARY;
import static com.charlesmadere.hummingbird.misc.Constants.MANGA;
import static com.charlesmadere.hummingbird.misc.Constants.MEMBERS;
import static com.charlesmadere.hummingbird.misc.Constants.NOTIFICATIONS;
import static com.charlesmadere.hummingbird.misc.Constants.QUOTES;
import static com.charlesmadere.hummingbird.misc.Constants.REVIEWS;
import static com.charlesmadere.hummingbird.misc.Constants.USERS;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.bLogin)
    Button mLogin;

    @BindView(R.id.etPassword)
    EditText mPasswordField;

    @BindView(R.id.etUsername)
    EditText mUsernameField;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tilPassword)
    TextInputLayout mPasswordContainer;

    @BindView(R.id.tilUsername)
    TextInputLayout mUsernameContainer;


    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public static Intent getNewTaskLaunchIntent(final Context context) {
        return getLaunchIntent(context).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void buildAnimeTaskStack(final TaskStackBuilder taskStack, final String[] paths) {
        taskStack.addNextIntent(AnimeActivity.getLaunchIntent(this, paths[1]));

        if (paths.length == 2) {
            return;
        }

        // https://hummingbird.me/anime/rwby-ii/quotes
        // https://hummingbird.me/anime/rwby-ii/reviews
        // https://hummingbird.me/anime/rwby-ii/reviews/10090

        if (QUOTES.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(AnimeQuotesActivity.getLaunchIntent(this, paths[1]));
        } else if (REVIEWS.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(AnimeReviewsActivity.getLaunchIntent(this, paths[1]));

            if (paths.length >= 4) {
                // TODO AnimeReviewActivity
            }
        }
    }

    private void buildGroupsTaskStack(final TaskStackBuilder taskStack, final String[] paths) {
        taskStack.addNextIntent(GroupActivity.getLaunchIntent(this, paths[1]));

        if (paths.length == 2) {
            return;
        }

        // https://hummingbird.me/groups/sos-brigade/members

        if (MEMBERS.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(GroupMembersActivity.getLaunchIntent(this, paths[1]));
        }
    }

    private void buildMangaTaskStack(final TaskStackBuilder taskStack, final String[] paths) {
        taskStack.addNextIntent(MangaActivity.getLaunchIntent(this, paths[1]));
    }

    private void buildNotificationsTaskStack(final TaskStackBuilder taskStack) {
        taskStack.addNextIntent(NotificationsActivity.getLaunchIntent(this));
    }

    private void buildUserTaskStack(final TaskStackBuilder taskStack, final String[] paths) {
        taskStack.addNextIntent(UserActivity.getLaunchIntent(this, paths[1]));

        if (paths.length == 2) {
            return;
        }

        // https://hummingbird.me/users/ThatLilChestnut/followers
        // https://hummingbird.me/users/ThatLilChestnut/following
        // https://hummingbird.me/users/ThatLilChestnut/groups
        // https://hummingbird.me/users/ThatLilChestnut/library
        // https://hummingbird.me/users/ThatLilChestnut/reviews

        if (FOLLOWERS.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(FollowersActivity.getLaunchIntent(this, paths[1]));
        } else if (FOLLOWING.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(FollowingActivity.getLaunchIntent(this, paths[1]));
        } else if (GROUPS.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(UserGroupsActivity.getLaunchIntent(this, paths[1]));
        } else if (LIBRARY.equalsIgnoreCase(paths[2])) {
            // https://hummingbird.me/users/ThatLilChestnut/library/manga
            if (paths.length >= 4 && MANGA.equalsIgnoreCase(paths[3])) {
                taskStack.addNextIntent(MangaLibraryActivity.getLaunchIntent(this, paths[1]));
            }
        } else if (REVIEWS.equalsIgnoreCase(paths[2])) {
            taskStack.addNextIntent(UserAnimeReviewsActivity.getLaunchIntent(this, paths[1]));
        }
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private void fetchCurrentUser() {
        Api.getCurrentUser(new GetCurrentUserListener(this));
    }

    private boolean followDeepLink() {
        final Intent intent = getIntent();
        if (intent == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent is null");
            return false;
        }

        final Uri data = intent.getData();
        if (data == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent's data is null");
            return false;
        }

        final String uri = data.toString();
        if (TextUtils.isEmpty(uri) || TextUtils.getTrimmedLength(uri) == 0) {
            Timber.d(TAG, "Nothing to deep link to - Intent's URI is null, empty, or whitespace");
            return false;
        }

        Timber.d(TAG, "Attempting to deep link to URI: \"" + uri + '"');

        if (!uri.startsWith(HUMMINGBIRD_URL)) {
            Timber.w(TAG, "Deep link URI isn't for Hummingbird");
            return false;
        }

        final String path = uri.substring(HUMMINGBIRD_URL.length(), uri.length());

        if (TextUtils.isEmpty(path)) {
            Timber.d(TAG, "Deep link URI's path is null or empty");
            return false;
        }

        final String[] paths = path.split("/");

        if (paths.length == 0) {
            Timber.d(TAG, "Deep link URI's path split is empty");
            return false;
        }

        if (paths[0].equalsIgnoreCase("dashboard")) {
            Timber.d(TAG, "Deep link URI is to the user's own dashboard");
            return false;
        }

        final TaskStackBuilder taskStack = TaskStackBuilder.create(this);

        // https://hummingbird.me/anime/rwby-ii
        if (ANIME.equalsIgnoreCase(paths[0])) {
            buildAnimeTaskStack(taskStack, paths);
        }

        // https://hummingbird.me/groups/sos-brigade
        else if (GROUPS.equalsIgnoreCase(paths[0])) {
            buildGroupsTaskStack(taskStack, paths);
        }

        // https://hummingbird.me/manga/rwby
        else if (MANGA.equalsIgnoreCase(paths[0])) {
            buildMangaTaskStack(taskStack, paths);
        }

        // https://hummingbird.me/notifications
        else if (NOTIFICATIONS.equalsIgnoreCase(paths[0])) {
            buildNotificationsTaskStack(taskStack);
        }

        // https://hummingbird.me/users/ThatLilChestnut
        else if (USERS.equalsIgnoreCase(paths[0])) {
            buildUserTaskStack(taskStack, paths);
        }

        // TODO comments

        if (taskStack.getIntentCount() >= 1) {
            taskStack.startActivities();
            return true;
        } else {
            return false;
        }
    }

    private void followDeepLinkOrGoToHomeActivity() {
        if (!followDeepLink()) {
            startActivity(HomeActivity.getLaunchIntent(this));
        }

        finish();
    }

    private boolean isLoginFormValid() {
        final CharSequence username = mUsernameField.getText();

        if (!TextUtils.isEmpty(username) && TextUtils.getTrimmedLength(username) >= 1) {
            if (Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                mUsernameContainer.setError(getText(R.string.enter_your_username_not_your_email));
                return false;
            } else {
                mUsernameContainer.setError(null);
                mUsernameContainer.setErrorEnabled(false);
            }

            return !TextUtils.isEmpty(mPasswordField.getText());
        } else {
            mUsernameContainer.setError(null);
            mUsernameContainer.setErrorEnabled(false);
            return false;
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CurrentUser.exists()) {
            followDeepLinkOrGoToHomeActivity();
        } else {
            setContentView(R.layout.activity_login);

            if (CurrentUser.shouldBeFetched()) {
                mSimpleProgressView.show();
                fetchCurrentUser();
            } else {
                showForm();
            }
        }
    }

    @OnClick(R.id.bLogin)
    void onLoginClick() {
        performLogin();
    }

    @OnTextChanged({R.id.etPassword, R.id.etUsername})
    void onLoginFormTextChanged() {
        updateLoginEnabledState();
    }

    @OnEditorAction(R.id.etPassword)
    boolean onPasswordEditorAction(final int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            performLogin();
        }

        return false;
    }

    private void performLogin() {
        if (!isLoginFormValid()) {
            return;
        }

        MiscUtils.closeKeyboard(this);
        mSimpleProgressView.fadeIn();
        mLogin.setEnabled(false);

        final String username = mUsernameField.getText().toString().trim();
        final String password = mPasswordField.getText().toString();

        Api.signIn(username, password, new SignInListener(this));
    }

    private void showError(@Nullable final String error) {
        showForm();
        mSimpleProgressView.fadeOut();
        updateLoginEnabledState();

        new AlertDialog.Builder(this)
                .setMessage(TextUtils.isEmpty(error) ? getText(R.string.error_logging_in) : error)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void showForm() {
        mUsernameContainer.setVisibility(View.VISIBLE);
        mPasswordContainer.setVisibility(View.VISIBLE);
        mLogin.setVisibility(View.VISIBLE);
    }

    private void updateLoginEnabledState() {
        mLogin.setEnabled(isLoginFormValid());
    }


    private static class GetCurrentUserListener implements ApiResponse<UserDigest> {
        private final WeakReference<LoginActivity> mActivityReference;

        private GetCurrentUserListener(final LoginActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final LoginActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError(null);
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final LoginActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.followDeepLinkOrGoToHomeActivity();
            }
        }
    }

    private static class SignInListener implements ApiResponse<Void> {
        private final WeakReference<LoginActivity> mActivityReference;

        private SignInListener(final LoginActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final LoginActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (error == null) {
                    activity.showError(null);
                } else {
                    activity.showError(error.getError());
                }
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final LoginActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.fetchCurrentUser();
            }
        }
    }

}
