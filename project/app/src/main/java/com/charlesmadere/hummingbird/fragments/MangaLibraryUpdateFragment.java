package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateSpinner;
import com.charlesmadere.hummingbird.views.ModifyReadingStatusSpinner;

import butterknife.BindView;
import butterknife.OnClick;

public class MangaLibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateSpinner.OnItemSelectedListener,
        ModifyReadingStatusSpinner.OnItemSelectedListener {

    public static final String TAG = "MangaLibraryUpdateFragment";
    private static final String KEY_MANGA_DIGEST = "MangaDigest";
    private static final String KEY_LIBRARY_ENTRY = "LibraryEntry";
    private static final String KEY_LIBRARY_UPDATE = "LibraryUpdate";

    private MangaDigest mMangaDigest;
    private MangaLibraryEntry mLibraryEntry;
    private MangaLibraryUpdate mLibraryUpdate;
    private RemoveListener mRemoveListener;
    private UpdateListener mUpdateListener;

    @BindView(R.id.ibDelete)
    ImageButton mDelete;

    @BindView(R.id.ibSave)
    ImageButton mSave;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public static MangaLibraryUpdateFragment create(final MangaDigest mangaDigest) {
        return create(mangaDigest, null);
    }

    public static MangaLibraryUpdateFragment create(final MangaLibraryEntry libraryEntry) {
        return create(null, libraryEntry);
    }

    private static MangaLibraryUpdateFragment create(final MangaDigest mangaDigest,
            final MangaLibraryEntry libraryEntry) {
        final Bundle args = new Bundle(1);

        if (mangaDigest != null) {
            args.putParcelable(KEY_MANGA_DIGEST, mangaDigest);
        } else if (libraryEntry != null) {
            args.putParcelable(KEY_LIBRARY_ENTRY, libraryEntry);
        } else {
            throw new IllegalArgumentException("both mangaDigest and libraryEntry can't be null");
        }

        final MangaLibraryUpdateFragment fragment = new MangaLibraryUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    public MangaLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    public MangaLibraryUpdate getLibraryUpdate() {
        return mLibraryUpdate;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof UpdateListener) {
            mUpdateListener = (UpdateListener) fragment;

            if (fragment instanceof RemoveListener) {
                mRemoveListener = (RemoveListener) fragment;
            }
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof UpdateListener) {
                mUpdateListener = (UpdateListener) activity;

                if (activity instanceof RemoveListener) {
                    mRemoveListener = (RemoveListener) activity;
                }
            }
        }

        if (mUpdateListener == null && mRemoveListener == null) {
            throw new IllegalStateException(TAG + " must have a listener attached");
        }
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismiss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mMangaDigest = args.getParcelable(KEY_MANGA_DIGEST);
        mLibraryEntry = args.getParcelable(KEY_LIBRARY_ENTRY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibraryUpdate = savedInstanceState.getParcelable(KEY_LIBRARY_UPDATE);
        }

        if (mLibraryUpdate == null) {
            if (mLibraryEntry == null) {
                mLibraryUpdate = new MangaLibraryUpdate(mMangaDigest);
            } else {
                mLibraryUpdate = new MangaLibraryUpdate(mLibraryEntry);
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library_update, container, false);
    }

    @OnClick(R.id.ibDelete)
    void onDeleteClick() {
        // TODO
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
        update();
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {
        mUpdateListener.onUpdateLibraryEntry();
        dismiss();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mLibraryUpdate != null) {
            outState.putParcelable(KEY_LIBRARY_UPDATE, mLibraryUpdate);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle.setText(mLibraryUpdate.getMangaTitle());
        mSave.setEnabled(false);

        if (mRemoveListener != null) {
            mDelete.setVisibility(View.VISIBLE);
        }

        // TODO
    }

    private void update() {
        mSave.setEnabled(mLibraryUpdate.containsModifications());
    }


    public interface RemoveListener {
        void onRemoveLibraryEntry();
    }

    public interface UpdateListener {
        void onUpdateLibraryEntry();
    }

}
