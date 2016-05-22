package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.models.PollFrequency;
import com.charlesmadere.hummingbird.models.TitleType;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.KeyValueTextView;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseDrawerActivity {

    private static final String TAG = "SettingsActivity";

    @BindView(R.id.kvtvGetHummingbirdPro)
    KeyValueTextView mGetHummingbirdPro;

    @BindView(R.id.scUseChromeCustomTabs)
    SwitchCompat mUseChromeCustomTabs;

    @BindView(R.id.scUseNotificationPolling)
    SwitchCompat mUseNotificationPolling;

    @BindView(R.id.tvAnimeTitleLanguage)
    TextView mAnimeTitleLanguage;

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

    @OnClick(R.id.llUseChromeCustomTabs)
    void onUseChromeCustomTabsClick() {
        Preferences.General.UseChromeCustomTabs.toggle();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mVersion.setText(getString(R.string.version_format, BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE));
    }

    private void refreshViews() {
        mAnimeTitleLanguage.setText(Preferences.General.TitleLanguage.get().getTextResId());
        mTheme.setText(Preferences.General.Theme.get().getTextResId());
        mUseChromeCustomTabs.setChecked(Preferences.General.UseChromeCustomTabs.get());

        mUseNotificationPolling.setChecked(Preferences.NotificationPolling.IsEnabled.get());
        mPollFrequency.setText(Preferences.NotificationPolling.Frequency.get().getTextResId());

        if (CurrentUser.get().isPro()) {
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
