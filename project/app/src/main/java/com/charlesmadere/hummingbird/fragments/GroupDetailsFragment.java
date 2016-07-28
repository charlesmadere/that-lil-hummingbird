package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupMembersActivity;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.views.HeadBodyItemView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupDetailsFragment extends BaseFragment {

    private static final String TAG = "GroupDetailsFragment";
    private static final String KEY_GROUP_DIGEST = "GroupDigest";

    private GroupDigest mGroupDigest;

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


    public static GroupDetailsFragment create(final GroupDigest digest) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_GROUP_DIGEST, digest);

        final GroupDetailsFragment fragment = new GroupDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mGroupDigest = args.getParcelable(KEY_GROUP_DIGEST);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @OnClick(R.id.hbivGroupMembers)
    void onGroupMembersClick() {
        startActivity(GroupMembersActivity.getLaunchIntent(getContext(), mGroupDigest.getId(),
                mGroupDigest.getName()));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Group group = mGroupDigest.getGroup();
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

}
