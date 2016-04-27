package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.views.KeyValueTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class UserBioFragment extends BaseFragment {

    private static final String TAG = "UserBioFragment";
    private static final String KEY_USER = "User";

    private User mUser;

    @BindView(R.id.cvWebsite)
    CardView mWebsiteCard;

    @BindView(R.id.kvtvLastUpdate)
    KeyValueTextView mLastUpdate;

    @BindView(R.id.kvtvLifeSpentOnAnime)
    KeyValueTextView mLifeSpentOnAnime;

    @BindView(R.id.kvtvWaifuOrHusbando)
    KeyValueTextView mWaifuOrHusbando;

    @BindView(R.id.tvBio)
    TextView mBio;

    @BindView(R.id.tvWebsite)
    TextView mWebsiteText;


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

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLastUpdate.setText(getText(R.string.last_update), mUser.getLastLibraryUpdate()
                .getRelativeTimeText(getContext()));

        mLifeSpentOnAnime.setText(getText(R.string.life_spent_on_anime),
                MiscUtils.getElapsedTime(getResources(), mUser.getLifeSpentOnAnimeSeconds()));

        if (mUser.hasWaifuOrHusbando()) {
            mWaifuOrHusbando.setText(getText(mUser.getWaifuOrHusbando().getTextResId()),
                    mUser.getWaifu());
            mWaifuOrHusbando.setVisibility(View.VISIBLE);
        }

        if (mUser.hasWebsite()) {
            mWebsiteText.setText(mUser.getWebsite());
            mWebsiteCard.setVisibility(View.VISIBLE);
        }

        if (mUser.hasBio()) {
            mBio.setText(mUser.getBio());
        } else {
            mBio.setText(R.string.no_bio_available);
        }
    }

    @OnClick(R.id.tvWebsite)
    void onWebsiteClick() {
        openUrl(mUser.getWebsite());
    }

}
