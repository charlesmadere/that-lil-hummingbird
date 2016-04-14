package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.charlesmadere.hummingbird.views.NavigationDrawerView;

import butterknife.Bind;

public abstract class BaseDrawerActivity extends BaseActivity implements
        NavigationDrawerItemView.OnNavigationDrawerItemViewClickListener {

    @Bind(R.id.drawerLayout)
    protected DrawerLayout mDrawerLayout;

    @Bind(R.id.navigationDrawerView)
    protected NavigationDrawerView mNavigationDrawerView;

    protected ActionBarDrawerToggle mDrawerToggle;


    protected static Intent createDrawerActivityIntent(final Context context, final Class c) {
        return new Intent(context, c).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mNavigationDrawerView);
    }

    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return null;
    }

    public boolean isDrawerVisible() {
        return mDrawerLayout.isDrawerVisible(mNavigationDrawerView);
    }

    protected boolean isUpNavigationEnabled() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isDrawerVisible()) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected void onDrawerClosed() {
        // method intentionally blank, children can override
    }

    protected void onDrawerOpened() {
        // method intentionally blank, children can override
    }

    @Override
    public final void onNavigationDrawerItemViewClick(final NavigationDrawerItemView v) {
        closeDrawer();

        if (v.isSelected()) {
            return;
        }

        Intent intent = null;

        switch (v.getEntry()) {
            case HOME:
                intent = CurrentUserActivity.getLaunchIntent(this);
                break;

            case HUMMINGBIRD_ON_THE_WEB:
                intent = new Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://www.hummingbird.me/"));
                break;

            case SEARCH:
                intent = SearchActivity.getLaunchIntent(this);
                break;

            case SETTINGS:
                intent = SettingsActivity.getLaunchIntent(this);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        prepareDrawerLayout();
        prepareNavigationView();
    }

    private void prepareDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                BaseDrawerActivity.this.onDrawerClosed();
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                BaseDrawerActivity.this.onDrawerOpened();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(!isUpNavigationEnabled());
    }

    private void prepareNavigationView() {
        mNavigationDrawerView.setOnNavigationDrawerItemViewClickListener(this);
        mNavigationDrawerView.setSelectedNavigationDrawerItemViewEntry(
                getSelectedNavigationDrawerItemViewEntry());
    }

}
