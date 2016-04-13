package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.charlesmadere.hummingbird.R;

/**
 * A child class of the official Android {@link SwipeRefreshLayout} that helps us work around
 * some of its shortcomings. We should use this view instead of SwipeRefreshLayout in every case.
 */
public class RefreshLayout extends SwipeRefreshLayout {

    private int scrollingChildId;
    private View scrollingChild;


    public RefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    /*
     * http://stackoverflow.com/q/25270171/823952
     */
    @Override
    public boolean canChildScrollUp() {
        if (scrollingChild == null) {
            return super.canChildScrollUp();
        } else {
            return ViewCompat.canScrollVertically(scrollingChild, -1);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (scrollingChildId != 0) {
            findScrollingChild();
        }
    }

    private void findScrollingChild() {
        final View scrollingChild = findViewById(scrollingChildId);

        if (scrollingChild == null) {
            throw new NullPointerException("unable to find scrolling child");
        } else if (scrollingChild instanceof AbsListView || scrollingChild instanceof RecyclerView
                || scrollingChild instanceof ScrollView) {
            this.scrollingChild = scrollingChild;
        } else {
            throw new IllegalStateException("scrollingChild (" + scrollingChild +
                    ") must be an AbsListView, RecyclerView, or ScrollView");
        }
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.RefreshLayout);

        final int spinnerColorsResId = ta.getResourceId(R.styleable.RefreshLayout_spinner_colors,
                R.array.spinner_colors);
        setColorSchemeColors(getResources().getIntArray(spinnerColorsResId));

        if (ta.hasValue(R.styleable.RefreshLayout_scrolling_child)) {
            scrollingChildId = ta.getResourceId(R.styleable.RefreshLayout_scrolling_child, 0);
        }

        ta.recycle();
    }

    /*
     * https://code.google.com/p/android/issues/detail?id=77712
     */
    @Override
    public void setRefreshing(final boolean refreshing) {
        post(new Runnable() {
            @Override
            public void run() {
                RefreshLayout.super.setRefreshing(refreshing);
            }
        });
    }

    public void setScrollingChild(final AbsListView view) {
        scrollingChild = view;
    }

    public void setScrollingChild(final RecyclerView view) {
        scrollingChild = view;
    }

    public void setScrollingChild(final ScrollView view) {
        scrollingChild = view;
    }

}
