package com.charlesmadere.hummingbird.views;

import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Use this class to add space between items in a {@link RecyclerView}.
 */
public final class SpaceItemDecoration {

    /**
     * The only method that you need to worry about in this class. Automatically applies a
     * {@link ItemDecoration} class to the given {@link RecyclerView} based on its
     * {@link LayoutManager} and orientation.
     *
     * @param view
     * The {@link RecyclerView} to have the {@link ItemDecoration} applied to. Note that a
     * {@link LayoutManager} MUST have already been set before you call this method, otherwise a
     * {@link NullPointerException} will be thrown.
     *
     * @param includeStartAndEndEdges
     * True if you want to add spacing before the first item and after the last item, in addition
     * to adding space between every item. False if you only want to add spacing between items.
     *
     * @param spacingResId
     * the Android R.dimen.* value for the size of the spacing
     *
     * @throws IllegalArgumentException
     * If the {@link RecyclerView}'s {@link LayoutManager} is not a {@link GridLayoutManager},
     * a {@link LinearLayoutManager}, or a {@link StaggeredGridLayoutManager}, then this
     * Exception will be thrown.
     *
     * @throws NullPointerException
     * If the {@link RecyclerView} does not have a {@link LayoutManager} already set, then this
     * Exception will be thrown.
     */
    public static void apply(final RecyclerView view,
            final boolean includeStartAndEndEdges, @DimenRes final int spacingResId) {
        final LayoutManager lm = view.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        }

        final int spacing = view.getResources().getDimensionPixelSize(spacingResId);
        final ItemDecoration itemDecoration;

        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            final int columns = glm.getSpanCount();

            if (glm.getOrientation() == GridLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalGridImpl(includeStartAndEndEdges, spacing, columns);
            } else {
                itemDecoration = new VerticalGridImpl(includeStartAndEndEdges, spacing, columns);
            }
        } else if (lm instanceof LinearLayoutManager) {
            final LinearLayoutManager llm = (LinearLayoutManager) lm;

            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalLinearImpl(includeStartAndEndEdges, spacing);
            } else {
                itemDecoration = new VerticalLinearImpl(includeStartAndEndEdges, spacing);
            }
        } else if (lm instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) lm;

            if (sglm.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalStaggeredGridImpl(spacing);
            } else {
                itemDecoration = new VerticalStaggeredGridImpl(spacing);
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


    private static abstract class BaseImpl extends ItemDecoration {
        protected final boolean includeStartAndEndEdges;
        protected final int spacing;

        protected BaseImpl(final boolean includeStartAndEndEdges, final int spacing) {
            this.includeStartAndEndEdges = includeStartAndEndEdges;
            this.spacing = spacing;
        }

        protected int getCount(final RecyclerView parent) {
            return parent.getAdapter().getItemCount();
        }

        @Override
        public final void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state) {
            super.getItemOffsets(outRect, view, parent, state);

            final int position = parent.getChildAdapterPosition(view);
            if (position != RecyclerView.NO_POSITION) {
                getItemOffsets(outRect, view, parent, state, position);
            }
        }

        protected abstract void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position);
    }

    private static abstract class GridImpl extends BaseImpl {
        protected final int columns;

        protected GridImpl(final boolean includeStartAndEndEdges, final int spacing,
                final int columns) {
            super(includeStartAndEndEdges, spacing);
            this.columns = columns;
        }

        @Override
        protected final void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {
            final int column = position % columns;
            getItemOffsets(outRect, view, parent, state, position, column);
        }

        protected abstract void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position,
                final int column);
    }

    private static class HorizontalGridImpl extends GridImpl {
        private HorizontalGridImpl(final boolean includeStartAndEndEdges, final int spacing,
                final int columns) {
            super(includeStartAndEndEdges, spacing, columns);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position,
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

    private static class VerticalGridImpl extends GridImpl {
        private VerticalGridImpl(final boolean includeStartAndEndEdges, final int spacing,
                final int columns) {
            super(includeStartAndEndEdges, spacing, columns);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position,
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

    private static abstract class LinearImpl extends BaseImpl {
        protected LinearImpl(final boolean includeStartAndEndEdges,
                final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }
    }

    private static class HorizontalLinearImpl extends LinearImpl {
        private HorizontalLinearImpl(final boolean includeStartAndEndEdges, final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {
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

    private static class VerticalLinearImpl extends LinearImpl {
        private VerticalLinearImpl(final boolean includeStartAndEndEdges,
                final int spacing) {
            super(includeStartAndEndEdges, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {
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

    private static class HorizontalStaggeredGridImpl extends BaseImpl {
        private HorizontalStaggeredGridImpl(final int spacing) {
            super(false, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {
            outRect.right = spacing;
        }
    }

    private static class VerticalStaggeredGridImpl extends BaseImpl {
        private VerticalStaggeredGridImpl(final int spacing) {
            super(false, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {
            outRect.bottom = spacing;
        }
    }

}
