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
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.ReadingStatus;

public class ModifyReadingStatusSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private static final ReadingStatus[] VALUES = { ReadingStatus.CURRENTLY_READING,
            ReadingStatus.PLAN_TO_READ, ReadingStatus.COMPLETED, ReadingStatus.ON_HOLD,
            ReadingStatus.DROPPED };

    private OnItemSelectedListener mListener;


    public ModifyReadingStatusSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyReadingStatusSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ReadingStatus getItemAtPosition(final int position) {
        return (ReadingStatus) super.getItemAtPosition(position);
    }

    @Override
    public ReadingStatus getSelectedItem() {
        return (ReadingStatus) super.getSelectedItem();
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

    public void setContent(final MangaLibraryUpdate libraryUpdate) {
        setContent(libraryUpdate.getReadingStatus());
    }

    public void setContent(@Nullable ReadingStatus readingStatus) {
        if (readingStatus == null) {
            readingStatus = ReadingStatus.PLAN_TO_READ;
        }

        for (int position = 0; position < getCount(); ++position) {
            final ReadingStatus item = getItemAtPosition(position);

            if (readingStatus == item) {
                setSelection(position);
                return;
            }
        }

        throw new RuntimeException("The given " + ReadingStatus.class.getSimpleName() + " (" +
                readingStatus + ") doesn't exist in the list");
    }

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener l) {
        mListener = l;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final ModifyReadingStatusSpinner v);
    }


    private static class WatchingStatusUpdateAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return VALUES.length;
        }

        @Override
        public ReadingStatus getItem(final int position) {
            return VALUES[position];
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
