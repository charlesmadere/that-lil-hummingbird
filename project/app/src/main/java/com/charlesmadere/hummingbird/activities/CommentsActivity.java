package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class CommentsActivity extends BaseDrawerActivity {

    private static final String TAG = "CommentsActivity";
    private static final String CNAME = CommentsActivity.class.getCanonicalName();
    private static final String EXTRA_ID = CNAME + ".Id";

    private String mId;


    public static Intent getLaunchIntent(final Context context, final String id) {
        return new Intent(context, CommentsActivity.class)
                .putExtra(EXTRA_ID, id);
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Intent intent = getIntent();
        mId = intent.getStringExtra(EXTRA_ID);
    }

}
