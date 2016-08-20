package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GroupFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.BaseGroupFragment;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.fragments.GroupFeedFragment;
import com.charlesmadere.hummingbird.fragments.GroupFeedPostFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.FeedListeners;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.misc.ShareUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.GroupFeedPost;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class GroupActivity extends BaseDrawerActivity implements BaseGroupFragment.Listener,
        FeedListeners, FeedPostFragment.Listener, ObjectCache.KeyProvider, PaletteUtils.Listener {

    private static final String TAG = "GroupActivity";
    private static final String CNAME = GroupActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";

    private GroupDigest mGroupDigest;
    private String mGroupId;
    private UiColorSet mUiColorSet;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton mPostToFeed;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final Group group) {
        return getLaunchIntent(context, group.getId(), group.getName());
    }

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

    private void feedPostFailure() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_posting_to_feed)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void fetchFeed() {
        mSimpleProgressView.fadeIn();
        Api.getGroupDigest(mGroupId, new GetGroupDigestListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public GroupDigest getGroupDigest() {
        return mGroupDigest;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mGroupId };
    }

    @Nullable
    @Override
    public UiColorSet getUiColorSet() {
        return mUiColorSet;
    }

    private boolean isInGroup() {
        return mGroupDigest.getGroup().hasCurrentMemberId();
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    private void joinGroup() {
        Api.joinGroup(mGroupId);
        mGroupDigest.getGroup().setCurrentMemberId(CurrentUser.get().getUserId());
        supportInvalidateOptionsMenu();
    }

    private void leaveGroup() {
        Api.leaveGroup(mGroupDigest.getGroup().getCurrentMemberId());
        mGroupDigest.getGroup().setCurrentMemberId(null);
        supportInvalidateOptionsMenu();
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

        mGroupDigest = ObjectCache.get(this);

        if (mGroupDigest == null) {
            fetchFeed();
        } else {
            showGroupDigest(mGroupDigest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFeedBeganLoading() {
        updatePostToFeedVisibility();
    }

    @Override
    public void onFeedFinishedLoading() {
        updatePostToFeedVisibility();
    }

    @Override
    public void onFeedPostSubmit() {
        final GroupFeedPostFragment fragment = (GroupFeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(GroupFeedPostFragment.TAG);
        final GroupFeedPost post = fragment.getGroupFeedPost(CurrentUser.get().getUserId(), mGroupId);

        if (post == null) {
            return;
        }

        Api.postToFeed(post, new FeedPostListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miJoinGroup:
                joinGroup();
                return true;

            case R.id.miLeaveGroup:
                leaveGroup();
                return true;

            case R.id.miShare:
                ShareUtils.shareGroup(this, mGroupDigest);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floatingActionButton)
    void onPostToFeedClick() {
        GroupFeedPostFragment.create().show(getSupportFragmentManager(), GroupFeedPostFragment.TAG);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mGroupDigest != null) {
            menu.findItem(R.id.miShare).setVisible(true);

            if (isInGroup()) {
                menu.findItem(R.id.miLeaveGroup).setVisible(true);
            } else {
                menu.findItem(R.id.miJoinGroup).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mGroupDigest != null) {
            ObjectCache.put(mGroupDigest, this);
        }
    }

    @Override
    public void onUiColorsBuilt(final UiColorSet uiColorSet) {
        mUiColorSet = uiColorSet;
    }

    @OnPageChange(R.id.viewPager)
    void onViewPagerPageChange() {
        updatePostToFeedVisibility();
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
                    this, mCoverImage, mAppBarLayout, mCollapsingToolbarLayout, mTabLayout);
        }

        mViewPager.setAdapter(new GroupFragmentAdapter(this));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        supportInvalidateOptionsMenu();
        mSimpleProgressView.fadeOut();
    }

    protected void updatePostToFeedVisibility() {
        if (isInGroup()) {
            if (mViewPager.getCurrentItem() == GroupFragmentAdapter.POSITION_FEED) {
                final GroupFeedFragment fragment = ((GroupFragmentAdapter) mViewPager.getAdapter())
                        .getFeedFragment();

                if (fragment == null || fragment.isFetchingGroupStories()) {
                    mPostToFeed.hide();
                } else {
                    mPostToFeed.show();
                }
            } else {
                mPostToFeed.hide();
            }
        } else {
            mPostToFeed.hide();
        }
    }


    private static class FeedPostListener implements ApiResponse<Void> {
        private final WeakReference<GroupActivity> mActivityReference;

        protected FeedPostListener(final GroupActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.feedPostFailure();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final GroupActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.fetchFeed();
            }
        }
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
