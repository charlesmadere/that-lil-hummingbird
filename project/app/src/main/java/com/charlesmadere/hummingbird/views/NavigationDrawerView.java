package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationDrawerView extends ScrimInsetsFrameLayout {

    private NavigationDrawerItemView[] mNavigationDrawerItemViews;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.sdvCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.tvUsername)
    TextView mUsername;


    public NavigationDrawerView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationDrawerView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawerView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void findAllNavigationDrawerItemViewChildren() {
        final List<NavigationDrawerItemView> navigationDrawerItemViews = new ArrayList<>();
        findNavigationDrawerItemViewChildren(this, navigationDrawerItemViews);

        if (navigationDrawerItemViews.isEmpty()) {
            throw new IllegalStateException("couldn't find a single NavigationDrawerItemView"
                    + " within the NavigationDrawerView");
        }

        mNavigationDrawerItemViews = new NavigationDrawerItemView[navigationDrawerItemViews.size()];
        navigationDrawerItemViews.toArray(mNavigationDrawerItemViews);
    }

    private void findNavigationDrawerItemViewChildren(final View view,
            final List<NavigationDrawerItemView> navigationDrawerItemViews) {
        if (view instanceof NavigationDrawerItemView) {
            navigationDrawerItemViews.add((NavigationDrawerItemView) view);
        } else if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                findNavigationDrawerItemViewChildren(viewGroup.getChildAt(i),
                        navigationDrawerItemViews);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        findAllNavigationDrawerItemViewChildren();

        final AbsUser user = CurrentUser.get();
        mAvatar.setImageURI(Uri.parse(user.getAvatar()));
        mCoverImage.setImageURI(Uri.parse(user.getCoverImage()));
        mUsername.setText(user.getName());
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return true;
    }

    public void setOnNavigationDrawerItemViewClickListener(
            @Nullable final NavigationDrawerItemView.OnNavigationDrawerItemViewClickListener l) {
        for (final NavigationDrawerItemView view : mNavigationDrawerItemViews) {
            view.setOnNavigationDrawerItemViewClickListener(l);
        }
    }

    public void setSelectedNavigationDrawerItemViewEntry(
            @Nullable final NavigationDrawerItemView.Entry e) {
        for (final NavigationDrawerItemView view : mNavigationDrawerItemViews) {
            view.setSelected(e == view.getEntry());
        }
    }

}
