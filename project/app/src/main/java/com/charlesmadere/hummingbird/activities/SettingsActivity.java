package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.RequestCodes;
import com.charlesmadere.hummingbird.misc.SyncManager;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.models.PollFrequency;
import com.charlesmadere.hummingbird.models.TitleType;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.KeyValueTextView;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseDrawerActivity {

    private static final String TAG = "SettingsActivity";

    @BindView(R.id.cbPowerRequired)
    CheckBox mPowerRequired;

    @BindView(R.id.cbShowNsfwContent)
    CheckBox mShowNsfwContent;

    @BindView(R.id.cbWifiRequired)
    CheckBox mWifiRequired;

    @BindView(R.id.kvtvGetHummingbirdPro)
    KeyValueTextView mGetHummingbirdPro;

    @BindView(R.id.kvtvLastPoll)
    KeyValueTextView mLastPoll;

    @BindView(R.id.llPollFrequency)
    LinearLayout mPollFrequencyContainer;

    @BindView(R.id.llPowerRequired)
    LinearLayout mPowerRequiredContainer;

    @BindView(R.id.llUseNotificationPolling)
    LinearLayout mUseNotificationPollingContainer;

    @BindView(R.id.llWifiRequired)
    LinearLayout mWifiRequiredContainer;

    @BindView(R.id.scUseNotificationPolling)
    SwitchCompat mUseNotificationPolling;

    @BindView(R.id.tvAnimeTitleLanguage)
    TextView mAnimeTitleLanguage;

    @BindView(R.id.tvGooglePlayServicesError)
    TextView mGooglePlayServicesError;

    @BindView(R.id.tvPollFrequency)
    TextView mPollFrequency;

    @BindView(R.id.tvTheme)
    TextView mTheme;

    @BindView(R.id.tvVersion)
    TextView mVersion;


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, SettingsActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.SETTINGS;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCodes.GOOGLE_PLAY_SERVICES) {
            Timber.d(TAG, "Received result from Google Play Services: " + resultCode);
            refreshViews();
        }
    }

    @OnClick(R.id.llAnimeTitleLanguage)
    void onAnimeTitleLanguageClick() {
        final TitleType[] values = TitleType.values();
        CharSequence items[] = new CharSequence[values.length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(values[i].getTextResId());
        }

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        Preferences.General.TitleLanguage.set(values[which]);
                        refreshViews();
                    }
                })
                .setTitle(R.string.preferred_anime_title_language)
                .show();
    }

    @OnClick(R.id.kvtvAuthor)
    void onAuthorClick() {
        MiscUtils.openUrl(this, Constants.CHARLES_TWITTER_URL);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @OnClick(R.id.kvtvGetHummingbirdPro)
    void onGetHummingbirdProClick() {
        MiscUtils.openUrl(this, Constants.HUMMINGBIRD_PRO_URL);
    }

    @OnClick(R.id.tvGitHub)
    void onGitHubClick() {
        MiscUtils.openUrl(this, Constants.GITHUB_URL);
    }

    @OnClick(R.id.tvGooglePlayServicesError)
    void onGooglePlayServicesErrorClick() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int connectionStatus = googleApi.isGooglePlayServicesAvailable(
                ThatLilHummingbird.get());

        if (connectionStatus == ConnectionResult.SUCCESS) {
            Toast.makeText(this, R.string.google_play_services_error_has_been_resolved,
                    Toast.LENGTH_LONG).show();
            refreshViews();
            return;
        }

        if (googleApi.isUserResolvableError(connectionStatus)) {
            Timber.d(TAG, "User is attempting to resolve a Google Play Services error ("
                    + connectionStatus + ")");
            googleApi.getErrorDialog(this, connectionStatus, RequestCodes.GOOGLE_PLAY_SERVICES,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(final DialogInterface dialog) {
                            refreshViews();
                        }
                    }).show();
        } else {
            Timber.d(TAG, "User has encountered an unresolvable Google Play Services error ("
                    + connectionStatus + ")");
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.google_play_services_error_cant_be_resolved,
                            connectionStatus))
                    .setNeutralButton(R.string.ok, null)
                    .show();
        }
    }

    @OnClick(R.id.tvHummingbirdOnTheWeb)
    void onHummingbirdWebClick() {
        MiscUtils.openUrl(this, Constants.HUMMINGBIRD_URL);
    }

    @OnClick(R.id.tvLogViewer)
    void onLogViewerClick() {
        startActivity(LogViewerActivity.getLaunchIntent(this));
    }

    @OnClick(R.id.llPollFrequency)
    void onPollFrequencyClick() {
        final PollFrequency[] values = PollFrequency.values();
        CharSequence items[] = new CharSequence[values.length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(values[i].getTextResId());
        }

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final PollFrequency pollFrequency = Preferences.NotificationPolling.Frequency.get();
                        final PollFrequency newPollFrequency = values[which];

                        if (pollFrequency != null && pollFrequency.equals(newPollFrequency)) {
                            return;
                        }

                        if (pollFrequency == null) {
                            Timber.d(TAG, "Poll Frequency is now " + newPollFrequency.name());
                        } else {
                            Timber.d(TAG, "Poll Frequency was " + pollFrequency.name() +
                                    " and is now " + newPollFrequency.name());
                        }

                        Preferences.NotificationPolling.Frequency.set(newPollFrequency);
                        refreshViews();
                    }
                })
                .setTitle(R.string.poll_frequency)
                .show();
    }

    @OnClick(R.id.llPowerRequired)
    void onPowerRequiredClick() {
        Preferences.NotificationPolling.IsPowerRequired.toggle();
        refreshViews();
    }

    @OnClick(R.id.kvtvPriscilla)
    void onPriscillaClick() {
        MiscUtils.openUrl(this, Constants.PRISCILLA_URL);
    }

    @OnClick(R.id.tvRateThisApp)
    void onRateThisAppClick() {
        MiscUtils.openUrl(this, Constants.PLAY_STORE_BASE_URL + getPackageName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshViews();
    }

    @OnClick(R.id.tvRewatchIntroAnimation)
    void onRewatchIntroAnimationClick() {
        startActivity(SplashActivity.getLaunchIntent(this));
    }

    @OnClick(R.id.llShowNsfwContent)
    void onShowNsfwContentClick() {
        Preferences.General.ShowNsfwContent.toggle();
        refreshViews();
    }

    @OnClick(R.id.tvSignOut)
    void onSignOutClick() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.sign_out_confirm)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        ThatLilHummingbird.clearCaches();
                        CurrentUser.signOut();
                    }
                })
                .show();
    }

    @OnClick(R.id.llTheme)
    void onThemeClick() {
        final NightMode[] values = NightMode.values();
        CharSequence items[] = new CharSequence[values.length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(values[i].getTextResId());
        }

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final NightMode nightMode = Preferences.General.Theme.get();
                        final NightMode newNightMode = values[which];

                        if (nightMode != null && nightMode.equals(newNightMode)) {
                            return;
                        }

                        if (nightMode == null) {
                            Timber.d(TAG, "Theme is now " + newNightMode.name() + " (" +
                                    newNightMode.getThemeValue() + ')');
                        } else {
                            Timber.d(TAG, "Theme was " + nightMode.name() + " (" +
                                    nightMode.getThemeValue() + ") and is now " +
                                    newNightMode.name() + " (" + newNightMode.getThemeValue() +
                                    ')');
                        }

                        Preferences.General.Theme.set(newNightMode);
                        refreshViews();
                        showRestartDialog();
                    }
                })
                .setTitle(R.string.theme)
                .show();
    }

    @OnClick(R.id.llUseNotificationPolling)
    void onUseNotificationPollingClick() {
        Preferences.NotificationPolling.IsEnabled.toggle();
        SyncManager.enableOrDisable();
        refreshViews();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mVersion.setText(getString(R.string.version_format, BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE));
    }

    @OnClick(R.id.llWifiRequired)
    void onWifiRequired() {
        Preferences.NotificationPolling.IsWifiRequired.toggle();
        refreshViews();
    }

    private void refreshViews() {
        mAnimeTitleLanguage.setText(Preferences.General.TitleLanguage.get().getTextResId());
        mTheme.setText(Preferences.General.Theme.get().getTextResId());
        mShowNsfwContent.setChecked(Preferences.General.ShowNsfwContent.get());

        mUseNotificationPolling.setChecked(Preferences.NotificationPolling.IsEnabled.get());
        mPollFrequency.setText(Preferences.NotificationPolling.Frequency.get().getTextResId());
        mPowerRequired.setChecked(Preferences.NotificationPolling.IsPowerRequired.get());
        mWifiRequired.setChecked(Preferences.NotificationPolling.IsWifiRequired.get());

        final int connectionStatus = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(ThatLilHummingbird.get());

        if (connectionStatus == ConnectionResult.SUCCESS) {
            mGooglePlayServicesError.setVisibility(View.GONE);
            mUseNotificationPollingContainer.setEnabled(true);

            if (mUseNotificationPolling.isChecked()) {
                mPollFrequencyContainer.setEnabled(true);
                mPowerRequiredContainer.setEnabled(true);
                mWifiRequiredContainer.setEnabled(true);
            } else {
                mPollFrequencyContainer.setEnabled(false);
                mPowerRequiredContainer.setEnabled(false);
                mWifiRequiredContainer.setEnabled(false);
            }
        } else {
            mGooglePlayServicesError.setVisibility(View.VISIBLE);
            mUseNotificationPollingContainer.setEnabled(false);
            mPollFrequencyContainer.setEnabled(false);
            mPowerRequiredContainer.setEnabled(false);
            mWifiRequiredContainer.setEnabled(false);
        }

        if (Preferences.NotificationPolling.LastPoll.exists()) {
            mLastPoll.setText(getText(R.string.last_check), DateUtils.getRelativeDateTimeString(
                    this, Preferences.NotificationPolling.LastPoll.get(), DateUtils.DAY_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS, 0));
            mLastPoll.setVisibility(View.VISIBLE);
        } else {
            mLastPoll.setText("");
            mLastPoll.setVisibility(View.GONE);
        }

        if (CurrentUser.get().getUser().isPro()) {
            mGetHummingbirdPro.setVisibility(View.GONE);
        } else {
            mGetHummingbirdPro.setVisibility(View.VISIBLE);
        }
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.the_app_will_now_restart)
                .setNeutralButton(R.string.ok, null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(final DialogInterface dialog) {
                        ThatLilHummingbird.restart();
                    }
                })
                .show();
    }

}
