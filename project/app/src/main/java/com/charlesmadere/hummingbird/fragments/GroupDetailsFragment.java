package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GroupDigest;

public class GroupDetailsFragment extends BaseFragment {

    private static final String TAG = "GroupDetailsFragment";
    private static final String KEY_GROUP_DIGEST = "GroupDigest";

    private GroupDigest mGroupDigest;


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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
