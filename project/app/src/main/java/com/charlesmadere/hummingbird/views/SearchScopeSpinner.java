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
import com.charlesmadere.hummingbird.models.SearchScope;

public class SearchScopeSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private OnSearchScopeSelectedListener mListener;


    public SearchScopeSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchScopeSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SearchScope getSelectedSearchScope() {
        return (SearchScope) super.getSelectedItem();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(new SearchScopeAdapter());
        setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
            final long id) {
        if (mListener != null) {
            mListener.onSearchScopeSelected(this);
        }
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {
        // intentionally empty
    }

    public void setOnSearchScopeSelectedListener(@Nullable final OnSearchScopeSelectedListener l) {
        mListener = l;
    }


    public interface OnSearchScopeSelectedListener {
        void onSearchScopeSelected(final SearchScopeSpinner v);
    }


    private static class SearchScopeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return SearchScope.values().length;
        }

        @Override
        public SearchScope getItem(final int position) {
            return SearchScope.values()[position];
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
