package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AppNewsActivity;
import com.charlesmadere.hummingbird.activities.BaseDrawerActivity;
import com.charlesmadere.hummingbird.activities.CurrentUserActivity;
import com.charlesmadere.hummingbird.activities.SettingsActivity;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView.Entry;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationDrawerView extends ScrimInsetsFrameLayout implements
        AvatarView.OnClickListener {

    private NavigationDrawerItemView[] mNavigationDrawerItemViews;

    @BindView(R.id.avatarView)
    AvatarView mAvatar;

    @BindView(R.id.sdvCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.tvUsername)
    TextView mUsername;

    @BindView(R.id.proBadge)
    View mProBadge;


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

    private void closeDrawer() {
        final Activity activity = MiscUtils.optActivity(getContext());

        if (activity instanceof BaseDrawerActivity) {
            ((BaseDrawerActivity) activity).closeDrawer();
        }
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

    public NavigationDrawerItemView getNavigationDrawerItemView(final Entry entry) {
        for (final NavigationDrawerItemView view : mNavigationDrawerItemViews) {
            if (view.getEntry() == entry) {
                return view;
            }
        }

        throw new IllegalStateException(entry + " is missing from the navigation drawer");
    }

    @OnClick(R.id.appNewsDrawerTextView)
    void onAppNewsDrawerTextViewClick() {
        closeDrawer();

        final Context context = getContext();
        context.startActivity(AppNewsActivity.getLaunchIntent(context));
    }

    @Override
    public void onClick(final AvatarView v) {
        closeDrawer();

        final Context context = getContext();
        context.startActivity(CurrentUserActivity.getLaunchIntent(context));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
        findAllNavigationDrawerItemViewChildren();

        if (isInEditMode()) {
            return;
        }

        final User user = CurrentUser.get().getUser();
        mAvatar.setContent(user);
        mAvatar.setOnClickListener(this);
        mCoverImage.setImageURI(user.getCoverImage());
        mUsername.setText(user.getId());

        if (user.isPro()) {
            mProBadge.setVisibility(VISIBLE);
        }
    }

    @OnClick(R.id.ibSettings)
    void onSettingsClick() {
        closeDrawer();

        final Context context = getContext();
        context.startActivity(SettingsActivity.getLaunchIntent(context));
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return true;
    }

    public void setOnNavigationDrawerItemViewClickListener(
            @Nullable final NavigationDrawerItemView.OnClickListener l) {
        for (final NavigationDrawerItemView view : mNavigationDrawerItemViews) {
            view.setOnClickListener(l);
        }
    }

    public void setSelectedNavigationDrawerItemViewEntry(@Nullable final Entry e) {
        for (final NavigationDrawerItemView view : mNavigationDrawerItemViews) {
            view.setSelected(e == view.getEntry());
        }
    }

}
