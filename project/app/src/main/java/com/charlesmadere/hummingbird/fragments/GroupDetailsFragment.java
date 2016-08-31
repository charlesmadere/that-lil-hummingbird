package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupMembersActivity;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupDetailsFragment extends BaseGroupFragment {

    private static final String TAG = "GroupDetailsFragment";

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
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

}
