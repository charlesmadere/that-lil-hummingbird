package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class SearchActivity extends BaseDrawerActivity {

    private static final String TAG = "SearchActivity";


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, SearchActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.SEARCH;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

}
