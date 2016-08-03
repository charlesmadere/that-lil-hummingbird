package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.BaseUserFeedFragment;
import com.charlesmadere.hummingbird.fragments.BaseFragment;
import com.charlesmadere.hummingbird.fragments.UserProfileFragment;

import java.lang.ref.WeakReference;

public abstract class BaseUserFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_PROFILE = 0;
    public static final int POSITION_FEED = 1;

    private final Context mContext;
    private final SparseArrayCompat<WeakReference<BaseFragment>> mFragments;


    public BaseUserFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public BaseUserFragmentAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragments = new SparseArrayCompat<>(getCount());
    }

    protected abstract BaseUserFeedFragment createUserFeedFragment();

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        mFragments.removeAt(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    public BaseUserFeedFragment getFeedFragment() {
        final WeakReference<BaseFragment> fragmentReference = mFragments.get(POSITION_FEED);

        if (fragmentReference == null) {
            return null;
        } else {
            return (BaseUserFeedFragment) fragmentReference.get();
        }
    }

    @Override
    public BaseFragment getItem(final int position) {
        final BaseFragment fragment;

        switch (position) {
            case POSITION_PROFILE: fragment = UserProfileFragment.create(); break;
            case POSITION_FEED: fragment = createUserFeedFragment(); break;
            default: throw new IllegalArgumentException("invalid position: " + position);
        }

        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case POSITION_PROFILE: return mContext.getText(R.string.profile);
            case POSITION_FEED: return mContext.getText(R.string.feed);
            default: throw new IllegalArgumentException("invalid position: " + position);
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

}
