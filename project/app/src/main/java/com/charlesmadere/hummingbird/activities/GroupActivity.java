package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class GroupActivity extends BaseDrawerActivity {

    private static final String TAG = "GroupActivity";
    private static final String CNAME = GroupActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";

    private String mGroupId;


    public static Intent getLaunchIntent(final Context context, final String groupId,
            final String groupName) {
        return new Intent(context, GroupActivity.class)
                .putExtra(EXTRA_GROUP_ID, groupId)
                .putExtra(EXTRA_GROUP_NAME, groupName);
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
        setContentView(R.layout.activity_group);

        final Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_GROUP_NAME));
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);
    }

}
