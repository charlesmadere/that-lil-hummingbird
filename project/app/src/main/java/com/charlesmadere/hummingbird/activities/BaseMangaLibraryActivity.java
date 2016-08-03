package com.charlesmadere.hummingbird.activities;

import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;

public abstract class BaseMangaLibraryActivity extends BaseLibraryActivity {

    protected MangaLibraryFragmentAdapter mAdapter;


    protected abstract MangaLibraryFragmentAdapter createAdapter();

    @Override
    protected MangaLibraryFragmentAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }

        return mAdapter;
    }

}
