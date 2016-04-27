package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public abstract class BaseUserActivity extends BaseDrawerActivity {

    private static final String CNAME = BaseUserActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_STARTING_POSITION = "StartingPosition";
    private static final String KEY_USER = "User";

    private int mStartingPosition;
    private String mUsername;
    private User mUser;

    @BindView(R.id.appBarLayout)
    protected AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.sdvAvatar)
    protected SimpleDraweeView mAvatar;

    @BindView(R.id.parallaxCoverImage)
    protected SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    protected SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final User user) {
        final String username = user.getName();

        if (CurrentUser.get().getName().equalsIgnoreCase(username)) {
            return new Intent(context, CurrentUserActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            return new Intent(context, UserActivity.class)
                    .putExtra(EXTRA_USERNAME, username);
        }
    }

    private void fetchUser() {
        mSimpleProgressView.fadeIn();
        Api.getUser(mUsername, new GetUserListener(this));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);

        if (TextUtils.isEmpty(mUsername)) {
            mUser = CurrentUser.get();
            mUsername = mUser.getName();
            setTitle(R.string.home);
        } else {
            setTitle(mUsername);

            if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
                mUser = savedInstanceState.getParcelable(KEY_USER);
            }
        }

        mStartingPosition = UserFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStartingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION,
                    mStartingPosition);
        }

        if (mUser == null) {
            fetchUser();
        } else {
            showUser(mUser);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());

        if (mUser != null) {
            outState.putParcelable(KEY_USER, mUser);
        }
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_user)
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
                        fetchUser();
                    }
                })
                .show();
    }

    private void showUser(final User user) {
        mUser = user;
        PaletteUtils.applyParallaxColors(mUser.getCoverImage(), this, mAppBarLayout,
                mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        mAvatar.setImageURI(Uri.parse(user.getAvatar()));
        mViewPager.setAdapter(new UserFragmentAdapter(this, mUser));
        mViewPager.setCurrentItem(mStartingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mSimpleProgressView.fadeOut();
    }


    private static class GetUserListener implements ApiResponse<User> {
        private final WeakReference<BaseUserActivity> mActivityReference;

        private GetUserListener(final BaseUserActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseUserActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final User user) {
            final BaseUserActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showUser(user);
            }
        }
    }

}
