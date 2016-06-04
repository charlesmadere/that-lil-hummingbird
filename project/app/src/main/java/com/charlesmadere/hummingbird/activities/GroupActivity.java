package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

public class GroupActivity extends BaseDrawerActivity {

    private static final String TAG = "GroupActivity";
    private static final String CNAME = GroupActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";

    private String mGroupId;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


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
