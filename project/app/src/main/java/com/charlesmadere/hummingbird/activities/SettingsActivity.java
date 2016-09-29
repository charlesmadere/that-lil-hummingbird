package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.SyncManager;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.models.PollFrequency;
import com.charlesmadere.hummingbird.models.TitleType;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.CheckablePreferenceView;
import com.charlesmadere.hummingbird.views.CheckablePreferenceView.OnPreferenceChangeListener;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.OnClick;

import static com.charlesmadere.hummingbird.misc.RequestCodes.GOOGLE_PLAY_SERVICES_RESOLUTION;

public class SettingsActivity extends BaseDrawerActivity {

    private static final String TAG = "SettingsActivity";

    private final OnPreferenceChangeListener mOnShowNsfwPreferenceChangeListener =
            new OnPreferenceChangeListener() {
        @Override
        public void onPreferenceChange(final CheckablePreferenceView v) {
            refresh();
        }
    };

    private final OnPreferenceChangeListener mOnSyncPreferenceChangeListener =
            new OnPreferenceChangeListener() {
        @Override
        public void onPreferenceChange(final CheckablePreferenceView v) {
            SyncManager.enableOrDisable();
            refresh();
        }
    };

    @BindView(R.id.cpvPowerRequired)
    CheckablePreferenceView mPowerRequired;

    @BindView(R.id.cpvShowNsfwContent)
    CheckablePreferenceView mShowNsfwContent;

    @BindView(R.id.cpvUseNotificationPolling)
    CheckablePreferenceView mUseNotificationPolling;

    @BindView(R.id.cpvWifiRequired)
    CheckablePreferenceView mWifiRequired;

    @BindView(R.id.hbivDefaultLaunchScreen)
    HeadBodyItemView mDefaultLaunchScreen;

    @BindView(R.id.hbivDefaultLibrarySort)
    HeadBodyItemView mDefaultLibrarySort;

    @BindView(R.id.hbivLastPoll)
    HeadBodyItemView mLastPoll;

    @BindView(R.id.hbivPollFrequency)
    HeadBodyItemView mPollFrequency;

    @BindView(R.id.hbivTheme)
    HeadBodyItemView mTheme;

    @BindView(R.id.hbivTitleLanguage)
    HeadBodyItemView mTitleLanguage;

    @BindView(R.id.tvGooglePlayServicesError)
    TextView mGooglePlayServicesError;

    @BindView(R.id.tvVersion)
    TextView mVersion;


    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_PLAY_SERVICES_RESOLUTION) {
            Timber.d(TAG, "Received result from Google Play Services: " + resultCode);
            refresh();
        }
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

    @OnClick(R.id.hbivDefaultLaunchScreen)
    void onDefaultLaunchScreenClick() {
        CharSequence items[] = new CharSequence[LaunchScreen.values().length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(LaunchScreen.values()[i].getTextResId());
        }

        int checkedItem = -1;
        if (Preferences.General.DefaultLaunchScreen.exists()) {
            checkedItem = Preferences.General.DefaultLaunchScreen.get().ordinal();
        }

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                        Preferences.General.DefaultLaunchScreen.set(LaunchScreen.values()[which]);
                        refresh();
                    }
                })
                .setTitle(R.string.default_launch_screen)
                .show();
    }

    @OnClick(R.id.hbivDefaultLibrarySort)
    void onDefaultLibrarySortClick() {
        CharSequence items[] = new CharSequence[LibrarySort.values().length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(LibrarySort.values()[i].getTextResId());
        }

        int checkedItem = -1;
        if (Preferences.General.DefaultLibrarySort.exists()) {
            checkedItem = Preferences.General.DefaultLibrarySort.get().ordinal();
        }

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                        Preferences.General.DefaultLibrarySort.set(LibrarySort.values()[which]);
                        refresh();
                    }
                })
                .setTitle(R.string.default_library_sorting)
                .show();
    }

    @OnClick(R.id.tvGitHub)
    void onGitHubClick() {
        MiscUtils.openUrl(this, Constants.GITHUB_URL);
    }

    @OnClick(R.id.tvGooglePlayServicesError)
    void onGooglePlayServicesErrorClick() {
        GoogleApiAvailability google = GoogleApiAvailability.getInstance();
        int connectionStatus = google.isGooglePlayServicesAvailable(ThatLilHummingbird.get());

        if (connectionStatus == ConnectionResult.SUCCESS) {
            Toast.makeText(this, R.string.google_play_services_error_has_been_resolved,
                    Toast.LENGTH_LONG).show();
            refresh();
            return;
        }

        if (google.isUserResolvableError(connectionStatus)) {
            Timber.d(TAG, "User is attempting to resolve a Google Play Services error ("
                    + connectionStatus + ")");
            google.showErrorDialogFragment(this, connectionStatus, GOOGLE_PLAY_SERVICES_RESOLUTION,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(final DialogInterface dialog) {
                            refresh();
                        }
                    });
        } else {
            Timber.w(TAG, "User has encountered an unresolvable Google Play Services error ("
                    + connectionStatus + ")");
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.google_play_services_error_cant_be_resolved,
                            connectionStatus))
                    .setNeutralButton(R.string.ok, null)
                    .show();
        }
    }

    @OnClick(R.id.tvLogViewer)
    void onLogViewerClick() {
        startActivity(LogViewerActivity.getLaunchIntent(this));
    }

    @OnClick(R.id.hbivPollFrequency)
    void onPollFrequencyClick() {
        CharSequence[] items = new CharSequence[PollFrequency.values().length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(PollFrequency.values()[i].getTextResId());
        }

        int checkedItem = -1;
        if (Preferences.NotificationPolling.Frequency.exists()) {
            checkedItem = Preferences.NotificationPolling.Frequency.get().ordinal();
        }

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();

                        final PollFrequency pollFrequency = Preferences.NotificationPolling.Frequency.get();
                        final PollFrequency newPollFrequency = PollFrequency.values()[which];

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
                        SyncManager.enableOrDisable();
                        refresh();
                    }
                })
                .setTitle(R.string.poll_frequency)
                .show();
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
        refresh();
    }

    @OnClick(R.id.tvRewatchIntroAnimation)
    void onRewatchIntroAnimationClick() {
        startActivity(SplashActivity.getRewatchLaunchIntent(this));
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

    @OnClick(R.id.hbivTheme)
    void onThemeClick() {
        CharSequence[] items = new CharSequence[NightMode.values().length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(NightMode.values()[i].getTextResId());
        }

        int checkedItem = -1;
        if (Preferences.General.Theme.exists()) {
            checkedItem = Preferences.General.Theme.get().ordinal();
        }

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();

                        final NightMode nightMode = Preferences.General.Theme.get();
                        final NightMode newNightMode = NightMode.values()[which];

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
                        refresh();
                        showRestartDialog();
                    }
                })
                .setTitle(R.string.theme)
                .show();
    }

    @OnClick(R.id.hbivTitleLanguage)
    void onTitleLanguageClick() {
        CharSequence[] items = new CharSequence[TitleType.values().length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(TitleType.values()[i].getTextResId());
        }

        int checkedItem = -1;
        if (Preferences.General.TitleLanguage.exists()) {
            checkedItem = Preferences.General.TitleLanguage.get().ordinal();
        }

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                        Preferences.General.TitleLanguage.set(TitleType.values()[which]);
                        refresh();
                    }
                })
                .setTitle(R.string.preferred_title_language)
                .show();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();

        mShowNsfwContent.setBooleanPreference(Preferences.General.ShowNsfwContent);
        mShowNsfwContent.setOnPreferenceChangeListener(mOnShowNsfwPreferenceChangeListener);

        mUseNotificationPolling.setBooleanPreference(Preferences.NotificationPolling.IsEnabled);
        mUseNotificationPolling.setOnPreferenceChangeListener(mOnSyncPreferenceChangeListener);
        mPowerRequired.setBooleanPreference(Preferences.NotificationPolling.IsPowerRequired);
        mPowerRequired.setOnPreferenceChangeListener(mOnSyncPreferenceChangeListener);
        mWifiRequired.setBooleanPreference(Preferences.NotificationPolling.IsWifiRequired);
        mWifiRequired.setOnPreferenceChangeListener(mOnSyncPreferenceChangeListener);

        mVersion.setText(getString(R.string.version_format, BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE));
    }

    private void refresh() {
        mTitleLanguage.setBody(Preferences.General.TitleLanguage.get().getTextResId());
        mTheme.setBody(Preferences.General.Theme.get().getTextResId());
        mDefaultLibrarySort.setBody(Preferences.General.DefaultLibrarySort.get().getTextResId());
        mDefaultLaunchScreen.setBody(Preferences.General.DefaultLaunchScreen.get().getTextResId());
        mShowNsfwContent.refresh();

        mUseNotificationPolling.refresh();
        mPollFrequency.setBody(Preferences.NotificationPolling.Frequency.get().getTextResId());
        mPowerRequired.refresh();
        mWifiRequired.refresh();

        final int connectionStatus = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(ThatLilHummingbird.get());

        if (connectionStatus == ConnectionResult.SUCCESS) {
            mGooglePlayServicesError.setVisibility(View.GONE);
            mUseNotificationPolling.setEnabled(true);

            if (mUseNotificationPolling.isChecked()) {
                mPollFrequency.setEnabled(true);
                mPowerRequired.setEnabled(true);
                mWifiRequired.setEnabled(true);
            } else {
                mPollFrequency.setEnabled(false);
                mPowerRequired.setEnabled(false);
                mWifiRequired.setEnabled(false);
            }

            if (Preferences.NotificationPolling.LastPoll.exists()) {
                mLastPoll.setBody(DateUtils.getRelativeDateTimeString(this,
                        Preferences.NotificationPolling.LastPoll.get(), DateUtils.DAY_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS, 0));
                mLastPoll.setVisibility(View.VISIBLE);
            } else {
                mLastPoll.setVisibility(View.GONE);
            }
        } else {
            Timber.w(TAG, "User has a Google Play Services error: " + connectionStatus);
            mGooglePlayServicesError.setVisibility(View.VISIBLE);
            mUseNotificationPolling.setEnabled(false);
            mPollFrequency.setEnabled(false);
            mPowerRequired.setEnabled(false);
            mWifiRequired.setEnabled(false);
            mLastPoll.setVisibility(View.GONE);
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

    @Override
    protected boolean showSearchIcon() {
        return false;
    }

}
