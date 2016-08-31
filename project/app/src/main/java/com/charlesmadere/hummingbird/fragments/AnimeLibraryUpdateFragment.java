package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.views.ModifyNumberView;
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateSpinner;
import com.charlesmadere.hummingbird.views.ModifyRatingSpinner;
import com.charlesmadere.hummingbird.views.ModifyWatchingStatusSpinner;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AnimeLibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateSpinner.OnItemSelectedListener,
        ModifyRatingSpinner.OnItemSelectedListener,
        ModifyWatchingStatusSpinner.OnItemSelectedListener {

    public static final String TAG = "AnimeLibraryUpdateFragment";
    private static final String KEY_ANIME_DIGEST = "AnimeDigest";
    private static final String KEY_LIBRARY_ENTRY = "LibraryEntry";
    private static final String KEY_LIBRARY_UPDATE = "LibraryUpdate";

    private AnimeDigest mAnimeDigest;
    private AnimeLibraryEntry mLibraryEntry;
    private AnimeLibraryUpdate mLibraryUpdate;
    private Listener mListener;

    @BindView(R.id.cbRewatching)
    CheckBox mRewatching;

    @BindView(R.id.etPersonalNotes)
    EditText mPersonalNotes;

    @BindView(R.id.ibSave)
    ImageButton mSave;

    @BindView(R.id.mnvRewatchCount)
    ModifyNumberView mRewatchCount;

    @BindView(R.id.mnvWatchCount)
    ModifyNumberView mWatchCount;

    @BindView(R.id.modifyPublicPrivateSpinner)
    ModifyPublicPrivateSpinner mModifyPublicPrivateSpinner;

    @BindView(R.id.modifyRatingSpinner)
    ModifyRatingSpinner mModifyRatingSpinner;

    @BindView(R.id.modifyWatchingStatusSpinner)
    ModifyWatchingStatusSpinner mModifyWatchingStatusSpinner;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public static AnimeLibraryUpdateFragment create(final AnimeDigest animeDigest) {
        return create(animeDigest, null);
    }

    public static AnimeLibraryUpdateFragment create(final AnimeLibraryEntry libraryEntry) {
        return create(null, libraryEntry);
    }

    private static AnimeLibraryUpdateFragment create(final AnimeDigest animeDigest,
            final AnimeLibraryEntry libraryEntry) {
        final Bundle args = new Bundle(1);

        if (animeDigest != null) {
            args.putParcelable(KEY_ANIME_DIGEST, animeDigest);
        } else if (libraryEntry != null) {
            args.putParcelable(KEY_LIBRARY_ENTRY, libraryEntry);
        } else {
            throw new IllegalArgumentException("both animeDigest and libraryEntry can't be null");
        }

        final AnimeLibraryUpdateFragment fragment = new AnimeLibraryUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    public AnimeLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    public AnimeLibraryUpdate getLibraryUpdate() {
        return mLibraryUpdate;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibraryUpdate = savedInstanceState.getParcelable(KEY_LIBRARY_UPDATE);
        }

        if (mLibraryUpdate == null) {
            if (mLibraryEntry == null) {
                mLibraryUpdate = new AnimeLibraryUpdate(mAnimeDigest);
            } else {
                mLibraryUpdate = new AnimeLibraryUpdate(mLibraryEntry);
            }
        }

        mTitle.setText(mLibraryUpdate.getAnimeTitle());
        mSave.setEnabled(false);

        if (mAnimeDigest == null) {
            mWatchCount.setForWatchedCount(mLibraryUpdate, mLibraryEntry);
        } else {
            mWatchCount.setForWatchedCount(mLibraryUpdate, mAnimeDigest);
        }

        mModifyWatchingStatusSpinner.setContent(mLibraryUpdate);
        mModifyPublicPrivateSpinner.setContent(mLibraryUpdate);
        mModifyRatingSpinner.setContent(mLibraryUpdate);
        mRewatchCount.setForRewatchedTimes(mLibraryUpdate);

        mRewatching.setChecked(mLibraryUpdate.isRewatching());
        mPersonalNotes.setText(mLibraryUpdate.getNotes());

        mWatchCount.setListener(new ModifyNumberView.Listener() {
            @Override
            public void onNumberChanged(final ModifyNumberView v) {
                mLibraryUpdate.setEpisodesWatched(mWatchCount.getNumber());
                update();
            }
        });

        mModifyWatchingStatusSpinner.setOnItemSelectedListener(this);
        mModifyPublicPrivateSpinner.setOnItemSelectedListener(this);
        mModifyRatingSpinner.setOnItemSelectedListener(this);

        mRewatchCount.setListener(new ModifyNumberView.Listener() {
            @Override
            public void onNumberChanged(final ModifyNumberView v) {
                mLibraryUpdate.setRewatchCount(mRewatchCount.getNumber());
                update();
            }
        });
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
        dismiss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mAnimeDigest = args.getParcelable(KEY_ANIME_DIGEST);
        mLibraryEntry = args.getParcelable(KEY_LIBRARY_ENTRY);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_library_update, container, false);
    }

    @Override
    public void onItemSelected(final ModifyPublicPrivateSpinner v) {
        mLibraryUpdate.setPrivacy(v.getSelectedItem());
        update();
    }

    @Override
    public void onItemSelected(final ModifyRatingSpinner v) {
        mLibraryUpdate.setRating(v.getSelectedItem());
        update();
    }

    @Override
    public void onItemSelected(final ModifyWatchingStatusSpinner v) {
        mLibraryUpdate.setWatchingStatus(v.getSelectedItem());
        update();
    }

    @OnTextChanged(R.id.etPersonalNotes)
    void onPersonalNotesTextChanged() {
        final CharSequence charSequence = mPersonalNotes.getText();
        String notes = null;

        if (!TextUtils.isEmpty(charSequence)) {
            notes = charSequence.toString().trim();
        }

        mLibraryUpdate.setNotes(notes);
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @OnClick(R.id.llRewatching)
    void onRewatchingClick() {
        mRewatching.toggle();
        mLibraryUpdate.setRewatching(mRewatching.isChecked());
        update();
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {
        mListener.onUpdateLibraryEntry();
        dismiss();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_LIBRARY_UPDATE, mLibraryUpdate);
    }

    @Override
    protected boolean shouldAutoExpand() {
        return true;
    }

    private void update() {
        mSave.setEnabled(mLibraryUpdate.containsModifications());
    }


    public interface Listener {
        void onUpdateLibraryEntry();
    }

}
