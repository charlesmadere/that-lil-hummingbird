package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GalleryImage;

public class GalleryAdapter extends BaseAdapter<GalleryImage> {

    public GalleryAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_gallery;
    }

}
