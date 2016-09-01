package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupMembersActivity;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupDetailsFragment extends BaseGroupFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupDetailsFragment";

    @BindView(R.id.hbivGroupMembers)
    HeadBodyItemView mGroupMembers;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.tvAboutBody)
    TextView mAboutBody;

    @BindView(R.id.tvAboutTitle)
    TextView mAboutTitle;

    @BindView(R.id.tvBioBody)
    TextView mBioBody;

    @BindView(R.id.tvBioTitle)
    TextView mBioTitle;

    @BindView(R.id.tvNoBio)
    TextView mNoBio;


    public static GroupDetailsFragment create() {
        return new GroupDetailsFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        showGroupDigest();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @OnClick(R.id.hbivGroupMembers)
    void onGroupMembersClick() {
        final Activity activity = getActivity();
        final UiColorSet uiColorSet = activity instanceof PaletteUtils.Listener ?
                ((PaletteUtils.Listener) activity).getUiColorSet() : null;

        startActivity(GroupMembersActivity.getLaunchIntent(getContext(), getGroupDigest().getId(),
                getGroupDigest().getName(), uiColorSet));
    }

    @Override
    public void onRefresh() {
        refreshGroupDigest();
    }

    private void refreshGroupDigest() {
        mRefreshLayout.setRefreshing(true);
        Api.getGroupDigest(getGroupDigest().getId(), new GetGroupDigestListener(this));
    }

    @Override
    protected void setGroupDigest(final GroupDigest groupDigest) {
        super.setGroupDigest(groupDigest);
        showGroupDigest();
    }

    private void showGroupDigest() {
        final Group group = getGroupDigest().getGroup();
        mAboutTitle.setText(getString(R.string.about_x, group.getName()));

        if (group.hasAbout()) {
            mAboutBody.setText(group.getAbout());
            mAboutBody.setVisibility(View.VISIBLE);
        }

        final NumberFormat numberFormat = NumberFormat.getNumberInstance();
        mGroupMembers.setHead(numberFormat.format(group.getMemberCount()));
        mGroupMembers.setBody(getResources().getQuantityText(R.plurals.group_members,
                group.getMemberCount()));

        mBioTitle.setText(getString(R.string.bio_for_x, group.getName()));

        if (group.hasBio()) {
            mBioBody.setText(group.getBio());
            mBioBody.setVisibility(View.VISIBLE);
        } else {
            mNoBio.setVisibility(View.VISIBLE);
        }

        mRefreshLayout.setRefreshing(false);
    }

    private void showRefreshError() {
        Toast.makeText(getContext(), R.string.error_refreshing_group, Toast.LENGTH_LONG).show();
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetGroupDigestListener implements ApiResponse<GroupDigest> {
        private final WeakReference<GroupDetailsFragment> mFragmentReference;

        private GetGroupDigestListener(final GroupDetailsFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupDetailsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showRefreshError();
            }
        }

        @Override
        public void success(final GroupDigest groupDigest) {
            final GroupDetailsFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.setGroupDigest(groupDigest);
            }
        }
    }

}
