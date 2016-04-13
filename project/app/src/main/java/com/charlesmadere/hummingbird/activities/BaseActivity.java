package com.charlesmadere.hummingbird.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;


    public abstract String getActivityName();

    /**
     * This method's code came from the Android documentation:
     * https://developer.android.com/training/implementing-navigation/ancestral.html
     */
    protected void navigateUp() {
        final Intent upIntent = NavUtils.getParentActivityIntent(this);

        if (upIntent == null) {
            finish();
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
        super.onCreate(savedInstanceState);
        Timber.d(TAG, '"' + getActivityName() + "\" created");
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

}
