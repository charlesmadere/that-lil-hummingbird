package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.DeepLinkUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.SimpleProgressView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private static final String CNAME = LoginActivity.class.getCanonicalName();
    private static final String EXTRA_LAUNCH_TO_NOTIFICATIONS = CNAME + ".LaunchToNotifications";

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

    public static Intent getNotificationsLaunchIntent(final Context context) {
        return getLaunchIntent(context).putExtra(EXTRA_LAUNCH_TO_NOTIFICATIONS, true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private void fetchCurrentUser() {
        Api.getCurrentUser(new GetCurrentUserListener(this));
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
            proceed();
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

    private void proceed() {
        final Intent intent = getIntent();
        final LaunchScreen launchScreen = Preferences.General.DefaultLaunchScreen.get();

        if (intent != null && intent.getBooleanExtra(EXTRA_LAUNCH_TO_NOTIFICATIONS, false)) {
            // noinspection ConstantConditions
            final Intent[] activityStack = new Intent[] {
                    launchScreen.getLaunchIntent(this),
                    NotificationsActivity.getLaunchIntent(this)
            };

            ContextCompat.startActivities(this, activityStack);
        } else {
            final Intent[] activityStack = DeepLinkUtils.buildIntentStack(this);

            if (activityStack == null || activityStack.length == 0) {
                // noinspection ConstantConditions
                startActivity(launchScreen.getLaunchIntent(this));
            } else {
                ContextCompat.startActivities(this, activityStack);
            }
        }

        supportFinishAfterTransition();
    }

    private void showError(@Nullable final String error) {
        showForm();
        mSimpleProgressView.fadeOut();
        updateLoginEnabledState();

        new AlertDialog.Builder(this)
                .setMessage(TextUtils.isEmpty(error) ? getText(R.string.error_logging_in_generic)
                        : error)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void showForm() {
        mSimpleProgressView.fadeOut();
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

            if (activity != null && activity.isAlive()) {
                activity.showError(null);
            }
        }

        @Override
        public void success(final UserDigest userDigest) {
            final LoginActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.proceed();
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

            if (activity != null && activity.isAlive()) {
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

            if (activity != null && activity.isAlive()) {
                activity.fetchCurrentUser();
            }
        }
    }

}
