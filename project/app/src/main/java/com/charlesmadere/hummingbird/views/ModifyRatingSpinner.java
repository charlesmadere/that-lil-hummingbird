package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.Rating;

public class ModifyRatingSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private OnItemSelectedListener mListener;


    public ModifyRatingSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyRatingSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Nullable
    @Override
    public Rating getItemAtPosition(final int position) {
        return (Rating) super.getItemAtPosition(position);
    }

    @Nullable
    @Override
    public Rating getSelectedItem() {
        return (Rating) super.getSelectedItem();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(new ModifyRatingAdapter());
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

    public void setContent(final LibraryEntry libraryEntry) {
        if (libraryEntry.hasRating()) {
            final Rating rating = Rating.from(libraryEntry);
            int position;

            for (position = 0; position < getCount(); ++position) {
                final Rating r = getItemAtPosition(position);

                if (rating.equals(r)) {
                    break;
                }
            }

            setSelection(position);
        } else {
            setSelection(0);
        }
    }

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        mListener = listener;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final ModifyRatingSpinner v);
    }


    private static class ModifyRatingAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Rating.values().length + 1;
        }

        @Nullable
        @Override
        public Rating getItem(final int position) {
            if (position == 0) {
                return null;
            } else {
                return Rating.values()[position - 1];
            }
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = RatingView.inflate(parent);
            }

            final RatingView.ViewHolder viewHolder = ((RatingView) convertView).getViewHolder();
            viewHolder.getView().setContent(getItem(position));

            return convertView;
        }
    }

}
