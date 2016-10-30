package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FavoriteMangaAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.UserDigest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class FavoriteMangaFragment extends BaseBottomSheetDialogFragment {

    private static final String TAG = "FavoriteMangaFragment";

    private Listener mListener;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    public static FavoriteMangaFragment create() {
        return new FavoriteMangaFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(TAG + " must attach to Listener");
        }
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismissAllowingStateLoss();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favorite_manga, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<UserDigest.Favorite.MangaItem> favorites = mListener.getFavoriteManga();

        if (favorites == null || favorites.isEmpty()) {
            dismissAllowingStateLoss();
            return;
        }

        final FavoriteMangaAdapter adapter = new FavoriteMangaAdapter(getContext());
        adapter.set(favorites);
        mRecyclerView.setAdapter(adapter);
    }


    public interface Listener {
        @Nullable
        ArrayList<UserDigest.Favorite.MangaItem> getFavoriteManga();
    }

}
