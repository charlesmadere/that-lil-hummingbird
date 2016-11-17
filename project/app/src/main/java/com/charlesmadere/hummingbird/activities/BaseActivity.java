package com.charlesmadere.hummingbird.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.ActivityRegister;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.preferences.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private static final String CNAME = BaseActivity.class.getCanonicalName();
    protected static final String EXTRA_UI_COLOR_SET = CNAME + ".UiColorSet";

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;


    protected void applyUiColorSet(final UiColorSet uiColorSet) {
        if (mToolbar != null) {
            mToolbar.setBackgroundColor(uiColorSet.getDarkVibrantColor());
        }
    }

    public abstract String getActivityName();

    @Nullable
    public CharSequence getSubtitle() {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return null;
        } else {
            return actionBar.getSubtitle();
        }
    }

    public boolean isAlive() {
        return !isFinishing() && !isDestroyed();
    }

    /**
     * This method's code came from the Android documentation:
     * https://developer.android.com/training/implementing-navigation/ancestral.html
     */
    protected void navigateUp() {
        final Intent upIntent = NavUtils.getParentActivityIntent(this);

        if (upIntent == null) {
            supportFinishAfterTransition();
        } else if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // noinspection ConstantConditions, WrongConstant
        getDelegate().setLocalNightMode(Preferences.General.Theme.get().getThemeValue());

        super.onCreate(savedInstanceState);
        Timber.d(TAG, '"' + getActivityName() + "\" created");
        ActivityRegister.attach(this);
    }

    @Override
    protected void onDestroy() {
        ActivityRegister.detach(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onViewsBound() {
        ButterKnife.bind(this);

        if (mToolbar != null) {
            prepareToolbar();
        }

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_UI_COLOR_SET)) {
            final UiColorSet uiColorSet = intent.getParcelableExtra(EXTRA_UI_COLOR_SET);
            applyUiColorSet(uiColorSet);
        }
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setContentView(@LayoutRes final int layoutResID) {
        super.setContentView(layoutResID);
        onViewsBound();
    }

    public void setSubtitle(@StringRes final int resId) {
        setSubtitle(getText(resId));
    }

    public void setSubtitle(@Nullable final CharSequence subtitle) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    @Override
    public String toString() {
        return getActivityName();
    }

}
