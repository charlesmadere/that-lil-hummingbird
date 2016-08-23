package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.CommentsAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.CommentPost;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.Group;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.CommentField;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class CommentStoryActivity extends BaseDrawerActivity implements CommentField.Listener,
        ObjectCache.KeyProvider, RecyclerViewPaginator.Listeners,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommentStoryActivity";
    private static final String CNAME = CommentStoryActivity.class.getCanonicalName();
    private static final String EXTRA_COMMENT_STORY = CNAME + ".CommentStory";

    private CommentsAdapter mAdapter;
    private CommentStory mCommentStory;
    private Feed mFeed;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.commentField)
    CommentField mCommentField;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final CommentStory commentStory) {
        return getLaunchIntent(context, commentStory, null);
    }

    public static Intent getLaunchIntent(final Context context, final CommentStory commentStory,
            @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, CommentStoryActivity.class)
                .putExtra(EXTRA_COMMENT_STORY, commentStory);

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    public void fetchSubstories() {
        mRefreshLayout.setRefreshing(true);
        Api.getSubstories(mCommentStory.getId(), new GetSubstoriesListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mCommentStory.getId() };
    }

    @Override
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    public void onCommentPosted() {
        MiscUtils.closeKeyboard(this);
        mRefreshLayout.setRefreshing(true);
        mCommentField.setEnabled(false);

        final String comment = mCommentField.getComment();
        final CommentPost commentPost = new CommentPost(comment, mCommentStory.getId());
        Api.postComment(commentPost, new PostCommentListener(this));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_story);

        final Intent intent = getIntent();
        mCommentStory = intent.getParcelableExtra(EXTRA_COMMENT_STORY);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchSubstories();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_comments, menu);

        final MenuItem poster = menu.findItem(R.id.miViewPoster);
        poster.setTitle(getString(R.string.view_x, mCommentStory.getPosterId()));
        poster.setVisible(true);

        if (!mCommentStory.getPosterId().equalsIgnoreCase(mCommentStory.getUserId())) {
            final MenuItem user = menu.findItem(R.id.miViewUser);
            user.setTitle(getString(R.string.view_x, mCommentStory.getUserId()));
            user.setVisible(true);
        }

        if (mCommentStory.hasGroupId()) {
            final MenuItem group = menu.findItem(R.id.miViewGroup);
            group.setTitle(getString(R.string.view_x, mCommentStory.getGroup().getName()));
            group.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDrawerOpened() {
        super.onDrawerOpened();
        MiscUtils.closeKeyboard(this);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miViewGroup:
                final Group group = mCommentStory.getGroup();
                startActivity(GroupActivity.getLaunchIntent(this, group.getId(), group.getName()));
                return true;

            case R.id.miViewPoster:
                final User poster = mCommentStory.getPoster();
                startActivity(UserActivity.getLaunchIntent(this, poster));
                return true;

            case R.id.miViewUser:
                final User user = mCommentStory.getUser();
                startActivity(UserActivity.getLaunchIntent(this, user));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        fetchSubstories();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CommentsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
        mCommentField.setListener(this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getSubstories(mCommentStory.getId(), mFeed, new PaginateSubstoriesListener(this));
    }

    protected void paginationComplete() {
        mAdapter.set(mCommentStory, mFeed);
        mAdapter.setPaginating(false);
    }

    protected void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    private void showCommentError() {
        mCommentField.setEnabled(true);
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(this, R.string.error_posting_comment, Toast.LENGTH_LONG).show();
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mCommentField.setEnabled(false);
        mRefreshLayout.setRefreshing(false);
    }

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mCommentStory, feed);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mCommentField.setEnabled(true);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected boolean showSearchIcon() {
        return false;
    }


    private static class GetSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<CommentStoryActivity> mActivityReference;

        private GetSubstoriesListener(final CommentStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showFeed(feed);
            }
        }
    }

    private static class PaginateSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<CommentStoryActivity> mActivityReference;
        private final int mSubstoriesSize;

        private PaginateSubstoriesListener(final CommentStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mSubstoriesSize = activity.mCommentStory.getSubstories().size();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasCursor() && feed.getSubstoriesSize() > mSubstoriesSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

    private static class PostCommentListener implements ApiResponse<Void> {
        private final WeakReference<CommentStoryActivity> mActivityReference;

        private PostCommentListener(final CommentStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showCommentError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final CommentStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.mCommentField.clear();
                activity.fetchSubstories();
            }
        }
    }

}
