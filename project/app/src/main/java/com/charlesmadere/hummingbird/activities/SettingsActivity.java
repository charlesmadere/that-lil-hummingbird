package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends BaseDrawerActivity {

    private static final String TAG = "SettingsActivity";

    @Bind(R.id.tvAnimeTitleLanguage)
    TextView mAnimeTitleLanguage;

    @Bind(R.id.tvVersion)
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @OnClick(R.id.llAnimeTitleLanguage)
    void onAnimeTitleLanguageClick() {
        final AnimeV2.Titles.Type[] values = AnimeV2.Titles.Type.values();
        CharSequence items[] = new CharSequence[values.length];

        for (int i = 0; i < items.length; ++i) {
            items[i] = getText(values[i].getTitleResId());
        }

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        Preferences.General.TitleLanguage.set(values[which]);
                        mAnimeTitleLanguage.setText(values[which].getTitleResId());
                    }
                })
                .setTitle(R.string.anime_title_language)
                .show();
    }

    @OnClick(R.id.tvAuthor)
    void onAuthorClick() {
        openUrl("https://twitter.com/charlesmadere");
    }

    @OnClick(R.id.tvGitHub)
    void onGitHubClick() {
        openUrl("https://github.com/charlesmadere/that-lil-hummingbird");
    }

    @OnClick(R.id.tvLogViewer)
    void onLogViewerClick() {
        startActivity(LogViewerActivity.getLaunchIntent(this));
    }

    @OnClick(R.id.tvRateThisApp)
    void onRateThisAppClick() {
        // TODO
    }

    @OnClick(R.id.tvSignOut)
    void onSignOutClick() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.sign_out_confirm)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        CurrentUser.logout();
                        startActivity(LoginActivity.getLaunchIntent(SettingsActivity.this));
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mAnimeTitleLanguage.setText(Preferences.General.TitleLanguage.get().getTitleResId());
        mVersion.setText(getString(R.string.version_format, BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE));
    }

    private void openUrl(final String url) {
        startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(url)));
    }

}
