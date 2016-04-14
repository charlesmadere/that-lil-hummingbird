package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.UserFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.Bind;

public abstract class BaseUserActivity extends BaseDrawerActivity {

    private static final String CNAME = BaseUserActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_USER = "User";

    private String mUsername;
    private User mUser;

    @Bind(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;


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
        // TODO
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
            setTitle(R.string.home);
        } else {
            setTitle(mUsername);

            if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
                mUser = savedInstanceState.getParcelable(KEY_USER);
            }
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

        if (mUser != null) {
            outState.putParcelable(KEY_USER, mUser);
        }
    }

    private void showError() {
        // TODO
    }

    private void showUser(final User user) {
        mUser = user;
        PaletteUtils.applyParallaxColors(mUser.getCoverImage(), this, mAppBarLayout,
                mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        mViewPager.setAdapter(new UserFragmentAdapter(this, mUser));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
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
        public void success(@Nullable final User user) {
            final BaseUserActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (user == null) {
                    activity.showError();
                } else {
                    activity.showUser(user);
                }
            }
        }
    }

}
