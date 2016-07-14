package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GroupFragmentAdapter;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupActivity extends BaseDrawerActivity {

    private static final String TAG = "GroupActivity";
    private static final String CNAME = GroupActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";
    private static final String KEY_GROUP_DIGEST = "GroupDigest";
    private static final String KEY_STARTING_POSITION = "StartingPosition";

    private GroupDigest mGroupDigest;
    private int mStartingPosition;
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


    public static Intent getLaunchIntent(final Context context, final String groupId) {
        return getLaunchIntent(context, groupId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String groupId,
            @Nullable final String groupName) {
        final Intent intent = new Intent(context, GroupActivity.class)
                .putExtra(EXTRA_GROUP_ID, groupId);

        if (!TextUtils.isEmpty(groupName)) {
            intent.putExtra(EXTRA_GROUP_NAME, groupName);
        }

        return intent;
    }

    private void fetchFeed() {
        mSimpleProgressView.fadeIn();
        Api.getGroup(mGroupId, new GetGroupDigestListener(this));
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
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);

        if (!intent.hasExtra(EXTRA_GROUP_NAME)) {
            setTitle(intent.getStringExtra(EXTRA_GROUP_NAME));
        }

        mStartingPosition = GroupFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mGroupDigest = savedInstanceState.getParcelable(KEY_GROUP_DIGEST);
            mStartingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION, mStartingPosition);
        }

        if (mGroupDigest == null) {
            fetchFeed();
        } else {
            showGroupDigest(mGroupDigest);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());

        if (mGroupDigest != null) {
            outState.putParcelable(KEY_GROUP_DIGEST, mGroupDigest);
        }
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_anime)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchFeed();
                    }
                })
                .show();
    }

    private void showGroupDigest(final GroupDigest groupDigest) {
        mGroupDigest = groupDigest;

        if (TextUtils.isEmpty(getTitle())) {
            setTitle(mGroupDigest.getName());
        }

        if (groupDigest.getGroup().hasCoverImage()) {
            PaletteUtils.applyParallaxColors(groupDigest.getGroup().getCoverImageUrl(), this,
                    mAppBarLayout, mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        }

        mViewPager.setAdapter(new GroupFragmentAdapter(this, mGroupDigest));
        mViewPager.setCurrentItem(mStartingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }


    private static class GetGroupDigestListener implements ApiResponse<GroupDigest> {
        private final WeakReference<GroupActivity> mActivityReference;

        private GetGroupDigestListener(final GroupActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final GroupDigest groupDigest) {
            final GroupActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showGroupDigest(groupDigest);
            }
        }
    }

}
