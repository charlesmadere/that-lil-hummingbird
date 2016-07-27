package com.charlesmadere.hummingbird.views;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public final class SpaceAndDividerItemDecoration {

    private static final int[] DEFAULT_DIVIDER = { android.R.attr.listDivider };


    public static void apply(final RecyclerView view, final Drawable divider,
            @DimenRes final int spacingResId) {
        if (divider == null) {
            throw new NullPointerException("divider must not be null");
        }

        final LayoutManager lm = view.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        }

        final int spacing = view.getResources().getDimensionPixelSize(spacingResId);
        final ItemDecoration itemDecoration;

        if (lm instanceof LinearLayoutManager) {
            final LinearLayoutManager llm = (LinearLayoutManager) lm;

            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalImpl(divider, spacing);
            } else {
                itemDecoration = new VerticalImpl(divider, spacing);
            }
        } else {
            throw new IllegalArgumentException("the given LayoutManager (" +
                    lm.getClass().getSimpleName() + ") isn't a supported type");
        }

        view.addItemDecoration(itemDecoration);
    }

    public static void apply(final RecyclerView view, @DimenRes final int spacingResId) {
        final TypedArray ta = view.getContext().obtainStyledAttributes(DEFAULT_DIVIDER);
        final Drawable divider = ta.getDrawable(0);
        ta.recycle();

        apply(view, divider, spacingResId);
    }

    public static void apply(final RecyclerView view, @DrawableRes final int dividerResId,
            @DimenRes final int spacingResId) {
        apply(view, ContextCompat.getDrawable(view.getContext(), dividerResId), spacingResId);
    }


    private static abstract class BaseImpl extends ItemDecoration {
        protected final Drawable mDivider;
        protected final int mSpacing;

        protected BaseImpl(final Drawable divider, final int spacing) {
            mDivider = divider;
            mSpacing = spacing;
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

    private static class HorizontalImpl extends BaseImpl {
        private HorizontalImpl(final Drawable divider, final int spacing) {
            super(divider, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {

        }

        @Override
        public void onDraw(final Canvas c, final RecyclerView parent, final State state) {
            super.onDraw(c, parent, state);


        }
    }

    private static class VerticalImpl extends BaseImpl {
        private VerticalImpl(final Drawable divider, final int spacing) {
            super(divider, spacing);
        }

        @Override
        protected void getItemOffsets(final Rect outRect, final View view,
                final RecyclerView parent, final State state, final int position) {

        }

        @Override
        public void onDraw(final Canvas c, final RecyclerView parent, final State state) {
            super.onDraw(c, parent, state);


        }
    }

}
