package com.charlesmadere.hummingbird.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;

public final class RecyclerViewPaginator {

    private static final int PAGINATION_THRESHOLD = 2;

    private boolean isEnabled;


    public RecyclerViewPaginator(final RecyclerView recyclerView, final Listeners listeners) {
        this(recyclerView, listeners, false);
    }

    public RecyclerViewPaginator(final RecyclerView recyclerView, final Listeners listeners,
            final boolean startEnabled) {
        if (recyclerView == null) {
            throw new NullPointerException("recyclerView parameter can't be null");
        }

        if (listeners == null) {
            throw new NullPointerException("listeners parameter can't be null");
        }

        isEnabled = startEnabled;
        final RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        } else if (lm instanceof LinearLayoutManager) {
            recyclerView.addOnScrollListener(new LinearLayoutManagerOnScrollListener(
                    (LinearLayoutManager) lm, listeners));
        } else if (lm instanceof StaggeredGridLayoutManager) {
            recyclerView.addOnScrollListener(new StaggeredGridLayoutManagerOnScrollListener(
                    (StaggeredGridLayoutManager) lm, listeners));
        } else {
            throw new IllegalArgumentException("the given LayoutManager (" +
                    lm.getClass().getSimpleName() + ") isn't a supported type");
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(final boolean enabled) {
        isEnabled = enabled;
    }


    private class LinearLayoutManagerOnScrollListener extends OnScrollListener {
        private final LinearLayoutManager mLayoutManager;
        private final Listeners mListeners;

        private LinearLayoutManagerOnScrollListener(final LinearLayoutManager lm,
                final Listeners listeners) {
            mLayoutManager = lm;
            mListeners = listeners;
        }

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (!isEnabled() || mListeners.isLoading()) {
                return;
            }

            final int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            final int itemCount = mLayoutManager.getItemCount();

            if (lastVisibleItemPosition + PAGINATION_THRESHOLD >= itemCount) {
                mListeners.paginate();
            }
        }
    }

    private class StaggeredGridLayoutManagerOnScrollListener extends OnScrollListener {
        private final StaggeredGridLayoutManager mLayoutManager;
        private final Listeners mListeners;

        private int[] mPositions;

        private StaggeredGridLayoutManagerOnScrollListener(final StaggeredGridLayoutManager sglm,
                final Listeners listeners) {
            mLayoutManager = sglm;
            mListeners = listeners;
        }

        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (!isEnabled() || mListeners.isLoading()) {
                return;
            }

            if (mPositions == null || mPositions.length != mLayoutManager.getSpanCount()) {
                mPositions = new int[mLayoutManager.getSpanCount()];
            }

            mLayoutManager.findLastVisibleItemPositions(mPositions);
            final int itemCount = mLayoutManager.getItemCount();

            for (final int position : mPositions) {
                if (position + PAGINATION_THRESHOLD >= itemCount) {
                    mListeners.paginate();
                    return;
                }
            }
        }
    }

    public interface Listeners {
        boolean isLoading();
        void paginate();
    }

}
