package com.charlesmadere.hummingbird.views;

import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * A class that encapsulates all of the functionality necessary to add space between items in a
 * {@link RecyclerView}. You only need to worry about a single method:
 * {@link #apply(RecyclerView, boolean, int)}
 */
public final class SpaceItemDecoration {

    /**
     * The only method that you need to worry about in this class. Automatically applies a
     * {@link RecyclerView.ItemDecoration} class to the given {@link RecyclerView} based on
     * its {@link RecyclerView.LayoutManager} and orientation.
     *
     * @param view
     * The {@link RecyclerView} to have the {@link RecyclerView.ItemDecoration} applied to. Note
     * that a {@link RecyclerView.LayoutManager} MUST have already been set before you call this
     * method, otherwise a {@link NullPointerException} will be thrown.
     *
     * @param includeStartEndEdge
     * True if you want to add spacing before the first item and after the last item, in addition
     * to adding space between every item. False if you only want to add spacing between items.
     *
     * @param spacingResId
     * the Android R.dimen.* value for the size of the spacing
     *
     * @throws IllegalArgumentException
     * If the {@link RecyclerView}'s {@link RecyclerView.LayoutManager} is not a
     * {@link GridLayoutManager} or a {@link LinearLayoutManager}, then this Exception will be
     * thrown.
     *
     * @throws NullPointerException
     * If the {@link RecyclerView} does not have a {@link RecyclerView.LayoutManager} already set,
     * then this Exception will be thrown.
     */
    public static void apply(final RecyclerView view, final boolean includeStartEndEdge,
            @DimenRes final int spacingResId) throws IllegalArgumentException {
        final RecyclerView.LayoutManager lm = view.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        }

        final int spacing = view.getResources().getDimensionPixelSize(spacingResId);
        final RecyclerView.ItemDecoration itemDecoration;

        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            final int columns = glm.getSpanCount();

            if (glm.getOrientation() == GridLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalGridSpaceItemDecoration(includeStartEndEdge,
                        spacing, columns);
            } else {
                itemDecoration = new VerticalGridSpaceItemDecoration(includeStartEndEdge,
                        spacing, columns);
            }
        } else if (lm instanceof LinearLayoutManager) {
            final LinearLayoutManager llm = (LinearLayoutManager) lm;

            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalLinearSpaceItemDecoration(includeStartEndEdge,
                        spacing);
            } else {
                itemDecoration = new VerticalLinearSpaceItemDecoration(includeStartEndEdge,
                        spacing);
            }
        } else if (lm instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) lm;
            final int columns = sglm.getSpanCount();

            if (sglm.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalGridSpaceItemDecoration(includeStartEndEdge,
                        spacing, columns);
            } else {
                itemDecoration = new VerticalGridSpaceItemDecoration(includeStartEndEdge,
                        spacing, columns);
            }
        } else {
            // Maybe we shouldn't throw an exception here, and should instead just return a no-op
            // ItemDecoration implementation. However doing it this way currently works for all
            // of our current cases, and prevents us from attempting to apply this code for cases
            // that don't exactly match.
            throw new IllegalArgumentException("the given LayoutManager (" +
                    lm.getClass().getSimpleName() + ") isn't a supported type");
        }

        view.addItemDecoration(itemDecoration);
    }


    protected static abstract class BaseSpaceItemDecoration extends RecyclerView.ItemDecoration {
        protected final boolean includeStartAndEndEdges;
        protected final int spacing;

        protected BaseSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing) {
            this.includeStartAndEndEdges = includeStartAndEndEdges;
            this.spacing = spacing;
        }

        protected int getCount(final RecyclerView parent) {
            return parent.getAdapter().getItemCount();
        }

        @Override
        public final void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            final int position = parent.getChildAdapterPosition(view);
            if (position != RecyclerView.NO_POSITION) {
                getItemOffsets(outRect, view, parent, state, position);
            }
        }

        protected abstract void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position);
    }


    protected static abstract class GridSpaceItemDecoration extends BaseSpaceItemDecoration {
        protected final int columns;

        protected GridSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing, final int columns) {
            super(includeStartAndEndEdges, spacing);
            this.columns = columns;
        }

        @Override
        protected final void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position) {
            final int column = position % columns;
            getItemOffsets(outRect, view, parent, state, position, column);
        }

        protected abstract void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position,
                final int column);
    }


    private static class HorizontalGridSpaceItemDecoration extends GridSpaceItemDecoration {
        private HorizontalGridSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing, final int columns) {
            super(includeStartAndEndEdges, spacing, columns);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position,
                final int column) {
            if (includeStartAndEndEdges) {
                outRect.right = spacing;

                if (column != 0) {
                    outRect.top = spacing - column * spacing / columns;
                }

                if (column + 1 != columns) {
                    outRect.bottom = (column + 1) * spacing / columns;
                }

                if (position < columns) {
                    outRect.left = spacing;
                }
            } else {
                outRect.top = column * spacing / columns;
                outRect.bottom = spacing - (column + 1) * spacing / columns;

                if (position >= columns) {
                    outRect.left = spacing;
                }
            }
        }
    }


    private static class VerticalGridSpaceItemDecoration extends GridSpaceItemDecoration {
        private VerticalGridSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing, final int columns) {
            super(includeStartAndEndEdges, spacing, columns);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position,
                final int column) {
            if (includeStartAndEndEdges) {
                outRect.bottom = spacing;

                if (column != 0) {
                    outRect.left = spacing - column * spacing / columns;
                }

                if (column + 1 != columns) {
                    outRect.right = (column + 1) * spacing / columns;
                }

                if (position < columns) {
                    outRect.top = spacing;
                }
            } else {
                outRect.left = column * spacing / columns;
                outRect.right = spacing - (column + 1) * spacing / columns;

                if (position >= columns) {
                    outRect.top = spacing;
                }
            }
        }
    }


    protected static abstract class LinearSpaceItemDecoration extends BaseSpaceItemDecoration {
        protected LinearSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }
    }


    private static class HorizontalLinearSpaceItemDecoration extends LinearSpaceItemDecoration {
        private HorizontalLinearSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position) {
            if (includeStartAndEndEdges) {
                outRect.right = spacing;

                if (position == 0) {
                    outRect.left = spacing;
                }
            } else if (position + 1 != getCount(parent)) {
                outRect.right = spacing;
            }
        }
    }


    private static class VerticalLinearSpaceItemDecoration extends LinearSpaceItemDecoration {
        private VerticalLinearSpaceItemDecoration(final boolean includeStartAndEndEdges,
                final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final RecyclerView.State state, final int position) {
            if (includeStartAndEndEdges) {
                outRect.bottom = spacing;

                if (position == 0) {
                    outRect.top = spacing;
                }
            } else if (position + 1 != getCount(parent)) {
                outRect.bottom = spacing;
            }
        }
    }

}
