package com.charlesmadere.hummingbird.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

public final class RecyclerViewPaginator {

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

        final RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        } else if (!(lm instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("the given LayoutManager (" +
                    lm.getClass().getSimpleName() + ") isn't a supported type");
        }

        isEnabled = startEnabled;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) lm;

        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isEnabled() || listeners.isLoading()) {
                    return;
                }

                final int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                final int totalItemCount = layoutManager.getItemCount();
                final int visibleItemCount = layoutManager.getChildCount();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    listeners.paginate();
                }
            }

            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // intentionally empty
            }
        });
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(final boolean enabled) {
        isEnabled = enabled;
    }


    public interface Listeners {
        boolean isLoading();
        void paginate();
    }

}
