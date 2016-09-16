package com.charlesmadere.hummingbird.views;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import com.charlesmadere.hummingbird.R;

public final class DividerItemDecoration {

    public static void apply(final RecyclerView view) {
        apply(view, R.drawable.divider);
    }

    public static void apply(final RecyclerView view, final Drawable divider) {
        if (divider == null) {
            throw new NullPointerException("divider must not be null");
        }

        final LayoutManager lm = view.getLayoutManager();

        if (lm == null) {
            throw new NullPointerException("RecyclerView must have a LayoutManager set");
        }

        final ItemDecoration itemDecoration;

        if (lm instanceof LinearLayoutManager) {
            final LinearLayoutManager llm = (LinearLayoutManager) lm;

            if (llm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                itemDecoration = new HorizontalLinearLayoutImpl(divider);
            } else {
                itemDecoration = new VerticalLinearLayoutImpl(divider);
            }
        } else {
            throw new IllegalArgumentException("the given LayoutManager (" +
                    lm.getClass().getSimpleName() + ") isn't a supported type");
        }

        view.addItemDecoration(itemDecoration);
    }

    public static void apply(final RecyclerView view, @DrawableRes final int dividerResId) {
        apply(view, ContextCompat.getDrawable(view.getContext(), dividerResId));
    }


    private static abstract class BaseImpl extends ItemDecoration {
        protected final Drawable mDivider;

        protected BaseImpl(final Drawable divider) {
            mDivider = divider;
        }
    }

    private static class HorizontalLinearLayoutImpl extends BaseImpl {
        private HorizontalLinearLayoutImpl(final Drawable divider) {
            super(divider);
        }

        @Override
        public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
                final State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = mDivider.getIntrinsicWidth();
        }

        @Override
        public void onDraw(final Canvas c, final RecyclerView parent, final State state) {
            super.onDraw(c, parent, state);

            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    private static class VerticalLinearLayoutImpl extends BaseImpl {
        private VerticalLinearLayoutImpl(final Drawable divider) {
            super(divider);
        }

        @Override
        public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
                final State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mDivider.getIntrinsicHeight();
        }

        @Override
        public void onDraw(final Canvas c, final RecyclerView parent, final State state) {
            super.onDraw(c, parent, state);

            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

}
