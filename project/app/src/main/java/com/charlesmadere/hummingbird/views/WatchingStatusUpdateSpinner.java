package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.WatchingStatusUpdate;

public class WatchingStatusUpdateSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private OnItemSelectedListener mListener;
    private WatchingStatusUpdate[] mWatchingStatusUpdates;


    public WatchingStatusUpdateSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public WatchingStatusUpdateSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    public WatchingStatusUpdate getSelectedItem() {
        return (WatchingStatusUpdate) super.getSelectedItem();
    }

    private void initialize() {
        mWatchingStatusUpdates = new WatchingStatusUpdate[] {
                WatchingStatusUpdate.CURRENTLY_WATCHING,
                WatchingStatusUpdate.PLAN_TO_WATCH,
                WatchingStatusUpdate.COMPLETED,
                WatchingStatusUpdate.ON_HOLD,
                WatchingStatusUpdate.DROPPED,
                WatchingStatusUpdate.REMOVE_FROM_LIBRARY
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(new WatchingStatusUpdateAdapter());
        setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        if (mListener != null) {
            mListener.onItemSelected(this);
        }
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
        // intentionally empty
    }

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener l) {
        mListener = l;
    }

    public void setWatchingStatusUpdate(final WatchingStatusUpdate wsu) {
        for (int i = 0; i < mWatchingStatusUpdates.length; ++i) {
            if (mWatchingStatusUpdates[i].equals(wsu)) {
                setSelection(i);
            }
        }
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final WatchingStatusUpdateSpinner v);
    }


    private class WatchingStatusUpdateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mWatchingStatusUpdates.length;
        }

        @Override
        public WatchingStatusUpdate getItem(final int position) {
            return mWatchingStatusUpdates[position];
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.item_dropdown, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mTextView.setText(getItem(position).getTextResId());

            return convertView;
        }
    }


    private static class ViewHolder {
        private final TextView mTextView;

        private ViewHolder(final View view) {
            mTextView = (TextView) view;
        }
    }

}
