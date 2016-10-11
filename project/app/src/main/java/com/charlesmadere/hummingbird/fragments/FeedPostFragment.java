package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.FeedPost;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class FeedPostFragment extends BaseBottomSheetDialogFragment {

    public static final String TAG = "FeedPostFragment";

    private Listener mListener;

    @BindView(R.id.cbNsfw)
    CheckBox mNsfw;

    @BindView(R.id.etFeedPost)
    EditText mFeedPost;

    @BindView(R.id.ibPost)
    ImageButton mPost;


    public static FeedPostFragment create() {
        return new FeedPostFragment();
    }

    @Nullable
    public FeedPost getFeedPost(final String userId) {
        final String text = mFeedPost.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {
            return null;
        }

        return new FeedPost(mNsfw.isChecked(), text, userId);
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pollField();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(TAG + " must attach to Listener");
        }
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismissAllowingStateLoss();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_feed_post, container, false);
    }

    @OnTextChanged(R.id.etFeedPost)
    void onFeedPostTextChanged() {
        pollField();
    }

    @OnClick(R.id.llNsfw)
    void onNsfwClick() {
        mNsfw.setChecked(!mNsfw.isChecked());
    }

    @OnClick(R.id.ibPost)
    void onPostClick() {
        mListener.onFeedPostSubmit();
        dismissAllowingStateLoss();
    }

    private void pollField() {
        final CharSequence text = mFeedPost.getText();
        mPost.setEnabled(!TextUtils.isEmpty(text) && TextUtils.getTrimmedLength(text) >= 1);
    }


    public interface Listener {
        void onFeedPostSubmit();
    }

}
