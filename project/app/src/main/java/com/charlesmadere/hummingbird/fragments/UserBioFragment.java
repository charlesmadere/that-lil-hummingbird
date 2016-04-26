package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.User;

public class UserBioFragment extends BaseFragment {

    private static final String TAG = "UserBioFragment";
    private static final String KEY_USER = "User";

    private User mUser;


    public static UserBioFragment create(final User user) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_USER, user);

        final UserBioFragment fragment = new UserBioFragment();
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
        mUser = args.getParcelable(KEY_USER);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_bio, container, false);
    }

}
