package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;

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
    public String getFeedPostText() {
        return mFeedPost == null ? null : mFeedPost.getText().toString().trim();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mListener = (Listener) MiscUtils.getActivity(context);
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismiss();
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
        dismiss();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pollField();
    }

    private void pollField() {
        final String text = getFeedPostText();
        mPost.setEnabled(!TextUtils.isEmpty(text) && TextUtils.getTrimmedLength(text) >= 1);
    }


    public interface Listener {
        void onFeedPostSubmit();
    }

}
