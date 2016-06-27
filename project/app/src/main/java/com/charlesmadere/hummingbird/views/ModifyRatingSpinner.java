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

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.LibraryUpdate;

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

    @Override
    public LibraryUpdate.Rating getSelectedItem() {
        return (LibraryUpdate.Rating) super.getSelectedItem();
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

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        mListener = listener;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final ModifyRatingSpinner v);
    }


    private static class ModifyRatingAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return LibraryUpdate.Rating.values().length;
        }

        @Override
        public LibraryUpdate.Rating getItem(final int position) {
            return LibraryUpdate.Rating.values()[position];
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.item_rating_view, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mRatingView.setContent(getItem(position));

            return convertView;
        }
    }


    private static class ViewHolder {
        private final RatingView mRatingView;

        private ViewHolder(final View view) {
            mRatingView = (RatingView) view;
        }
    }

}
