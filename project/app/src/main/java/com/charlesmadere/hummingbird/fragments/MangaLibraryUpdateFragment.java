package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateSpinner;
import com.charlesmadere.hummingbird.views.ModifyReadingStatusSpinner;

public class MangaLibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateSpinner.OnItemSelectedListener,
        ModifyReadingStatusSpinner.OnItemSelectedListener {

    private static final String TAG = "MangaLibraryUpdateFragment";


    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        // TODO
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library_update, container, false);
    }

    @Override
    public void onItemSelected(final ModifyPublicPrivateSpinner v) {
        // TODO
    }

    @Override
    public void onItemSelected(final ModifyReadingStatusSpinner v) {
        // TODO
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO
    }


    public interface RemoveListener {
        void onRemoveLibraryEntry();
    }

    public interface UpdateListener {
        void onUpdateLibraryEntry();
    }

}
