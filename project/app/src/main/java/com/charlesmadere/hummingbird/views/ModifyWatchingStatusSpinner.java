package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.WatchingStatus;

public class ModifyWatchingStatusSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private OnItemSelectedListener mListener;
    private WatchingStatus[] mWatchingStatuses;


    public ModifyWatchingStatusSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ModifyWatchingStatusSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    public WatchingStatus getSelectedItem() {
        return (WatchingStatus) super.getSelectedItem();
    }

    private void initialize() {
        mWatchingStatuses = new WatchingStatus[] {
                WatchingStatus.CURRENTLY_WATCHING, WatchingStatus.PLAN_TO_WATCH,
                WatchingStatus.COMPLETED, WatchingStatus.ON_HOLD, WatchingStatus.DROPPED };
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

    public void setContent(final AnimeLibraryUpdate libraryUpdate) {
        setWatchingStatus(libraryUpdate.getWatchingStatus());
    }

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener l) {
        mListener = l;
    }

    public void setWatchingStatus(final WatchingStatus wsu) {
        for (int i = 0; i < mWatchingStatuses.length; ++i) {
            if (mWatchingStatuses[i].equals(wsu)) {
                setSelection(i);
            }
        }
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final ModifyWatchingStatusSpinner v);
    }


    private class WatchingStatusUpdateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mWatchingStatuses.length;
        }

        @Override
        public WatchingStatus getItem(final int position) {
            return mWatchingStatuses[position];
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
            viewHolder.getView().setText(getItem(position).getTextResId());

            return convertView;
        }
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(final View itemView) {
            super(itemView);
        }

        private TextView getView() {
            return (TextView) itemView;
        }
    }

}
