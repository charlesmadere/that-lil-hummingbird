package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.LikersAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Liker;
import com.charlesmadere.hummingbird.networking.ApiResponse;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

public class PostReactionsBottomSheetDialogFragment extends BaseBottomSheetDialogFragment
        implements View.OnClickListener {

    private static final String TAG = "PostReactionsBottomSheetDialog";
    private static final String KEY_LIKERS = "Likers";
    private static final String KEY_PAGE = "Page";
    private static final String KEY_STORY_ID = "StoryId";

    private ArrayList<Liker> mLikers;
    private int mPage;
    private LikersAdapter mAdapter;
    private String mStoryId;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static PostReactionsBottomSheetDialogFragment create(final String storyId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_STORY_ID, storyId);

        final PostReactionsBottomSheetDialogFragment fragment = new PostReactionsBottomSheetDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onClick(final View view) {
        dismissAllowingStateLoss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mStoryId = args.getString(KEY_STORY_ID);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_post_reactions, container, false);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mLikers != null && !mLikers.isEmpty()) {
            outState.putParcelableArrayList(KEY_LIKERS, mLikers);
            outState.putInt(KEY_PAGE, mPage);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar.setTitle(R.string.post_reactions);
        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        mToolbar.setNavigationOnClickListener(this);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void showError() {
        Toast.makeText(getContext(), R.string.error_loading_post_reactions, Toast.LENGTH_LONG).show();
        dismissAllowingStateLoss();
    }

    private void showLikers(final ArrayList<Liker> likers) {

    }


    private static class LikersListener implements ApiResponse<ArrayList<Liker>> {
        private final WeakReference<PostReactionsBottomSheetDialogFragment> mFragmentReference;

        private LikersListener(final PostReactionsBottomSheetDialogFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final PostReactionsBottomSheetDialogFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(@Nullable final ArrayList<Liker> likers) {
            final PostReactionsBottomSheetDialogFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (likers == null || likers.isEmpty()) {

                } else {

                }
            }
        }
    }

}
