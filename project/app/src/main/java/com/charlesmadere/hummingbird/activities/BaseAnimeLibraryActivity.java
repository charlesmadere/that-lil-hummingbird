package com.charlesmadere.hummingbird.activities;

import com.charlesmadere.hummingbird.adapters.AnimeLibraryFragmentAdapter;

public abstract class BaseAnimeLibraryActivity extends BaseLibraryActivity {

    protected AnimeLibraryFragmentAdapter mAdapter;


    protected abstract AnimeLibraryFragmentAdapter createAdapter();

    @Override
    protected AnimeLibraryFragmentAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }

        return mAdapter;
    }

}
