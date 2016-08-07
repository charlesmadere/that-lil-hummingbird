package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupMembersActivity;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupDetailsFragment extends BaseGroupFragment {

    private static final String TAG = "GroupDetailsFragment";

    private Listener mListener;

    @BindView(R.id.hbivGroupMembers)
    HeadBodyItemView mGroupMembers;

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
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(getFragmentName() + " must have a Listener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @OnClick(R.id.hbivGroupMembers)
    void onGroupMembersClick() {
        startActivity(GroupMembersActivity.getLaunchIntent(getContext(), getGroupDigest().getId(),
                getGroupDigest().getName(), mListener.getUiColorSet()));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }


    public interface Listener {
        @Nullable
        UiColorSet getUiColorSet();
    }

}
