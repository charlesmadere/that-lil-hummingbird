package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.MangaDigest;

public class MangaCharactersAdapter extends BaseAdapter<MangaDigest.Character> {

    public MangaCharactersAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_manga_character;
    }

}
