package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class FeedPostFragment extends BaseBottomSheetDialogFragment implements
        Toolbar.OnMenuItemClickListener, View.OnClickListener {

    public static final String TAG = "FeedPostFragment";

    private Listener mListener;
    private MenuItem mPost;

    @BindView(R.id.etFeedPost)
    EditText mFeedPost;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


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

    @Override
    public void onClick(final View v) {
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
        final String text = getFeedPostText();
        mPost.setEnabled(!TextUtils.isEmpty(text) && TextUtils.getTrimmedLength(text) >= 1);
    }

    @Override
    public boolean onMenuItemClick(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miPost:
                mListener.onFeedPostSubmit();
                dismiss();
                return true;
        }

        return false;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(this);

        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.inflateMenu(R.menu.fragment_feed_post);

        final Menu menu = mToolbar.getMenu();
        mPost = menu.findItem(R.id.miPost);

        MiscUtils.openKeyboard(getContext(), mFeedPost);
    }


    public interface Listener {
        void onFeedPostSubmit();
    }

}
