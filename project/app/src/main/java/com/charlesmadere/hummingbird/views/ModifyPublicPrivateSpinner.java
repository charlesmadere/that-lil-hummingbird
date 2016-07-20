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
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.Privacy;

public class ModifyPublicPrivateSpinner extends AppCompatSpinner implements
        AdapterView.OnItemSelectedListener {

    private OnItemSelectedListener mListener;


    public ModifyPublicPrivateSpinner(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyPublicPrivateSpinner(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Privacy getItemAtPosition(final int position) {
        return (Privacy) super.getItemAtPosition(position);
    }

    @Override
    public Privacy getSelectedItem() {
        return (Privacy) super.getSelectedItem();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(new ModifyPrivacyAdapter());
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
        setContent(libraryUpdate.isPrivate() ? Privacy.PRIVATE : Privacy.PUBLIC);
    }

    public void setContent(final MangaLibraryUpdate libraryUpdate) {
        setContent(libraryUpdate.isPrivate() ? Privacy.PRIVATE : Privacy.PUBLIC);
    }

    public void setContent(final Privacy privacy) {
        for (int position = 0; position < getCount(); ++position) {
            final Privacy item = getItemAtPosition(position);

            if (privacy == item) {
                setSelection(position);
                return;
            }
        }

        throw new RuntimeException("The given " + Privacy.class.getSimpleName() + " (" + privacy
                + ") doesn't exist in the list");
    }

    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener l) {
        mListener = l;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(final ModifyPublicPrivateSpinner v);
    }


    private static class ModifyPrivacyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Privacy.values().length;
        }

        @Override
        public Privacy getItem(final int position) {
            return Privacy.values()[position];
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
