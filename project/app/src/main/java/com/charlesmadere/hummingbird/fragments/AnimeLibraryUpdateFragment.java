package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateSpinner;
import com.charlesmadere.hummingbird.views.ModifyRatingSpinner;
import com.charlesmadere.hummingbird.views.ModifyRewatchCountView;
import com.charlesmadere.hummingbird.views.ModifyWatchCountView;
import com.charlesmadere.hummingbird.views.ModifyWatchingStatusSpinner;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AnimeLibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateSpinner.OnItemSelectedListener,
        ModifyRatingSpinner.OnItemSelectedListener,
        ModifyRewatchCountView.OnRewatchCountChangedListener,
        ModifyWatchCountView.OnWatchCountChangedListener,
        ModifyWatchingStatusSpinner.OnItemSelectedListener {

    public static final String TAG = "AnimeLibraryUpdateFragment";
    private static final String KEY_ANIME_DIGEST = "AnimeDigest";
    private static final String KEY_LIBRARY_ENTRY = "LibraryEntry";
    private static final String KEY_LIBRARY_UPDATE = "LibraryUpdate";

    private AnimeDigest mAnimeDigest;
    private AnimeLibraryEntry mLibraryEntry;
    private AnimeLibraryUpdate mLibraryUpdate;
    private RemoveListener mRemoveListener;
    private UpdateListener mUpdateListener;

    @BindView(R.id.cbRewatching)
    CheckBox mRewatching;

    @BindView(R.id.etPersonalNotes)
    EditText mPersonalNotes;

    @BindView(R.id.ibDelete)
    ImageButton mDelete;

    @BindView(R.id.ibSave)
    ImageButton mSave;

    @BindView(R.id.modifyPublicPrivateSpinner)
    ModifyPublicPrivateSpinner mModifyPublicPrivateSpinner;

    @BindView(R.id.modifyRatingSpinner)
    ModifyRatingSpinner mModifyRatingSpinner;

    @BindView(R.id.modifyRewatchCountView)
    ModifyRewatchCountView mModifyRewatchCountView;

    @BindView(R.id.modifyWatchCountView)
    ModifyWatchCountView mModifyWatchCountView;

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

    private static AnimeLibraryUpdateFragment create(@Nullable final AnimeDigest animeDigest,
            @Nullable final AnimeLibraryEntry libraryEntry) {
        final Bundle args = new Bundle(2);
        args.putParcelable(KEY_ANIME_DIGEST, animeDigest);
        args.putParcelable(KEY_LIBRARY_ENTRY, libraryEntry);

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
        mAnimeDigest = args.getParcelable(KEY_ANIME_DIGEST);
        mLibraryEntry = args.getParcelable(KEY_LIBRARY_ENTRY);

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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_library_update, container, false);
    }

    @OnClick(R.id.ibDelete)
    void onDeleteClick() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.are_you_sure_you_want_to_remove_this_from_your_library)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mRemoveListener.onRemoveLibraryEntry();
                        dismiss();
                    }
                })
                .show();
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

    @Override
    public void onRewatchCountChanged(final ModifyRewatchCountView v) {
        mLibraryUpdate.setRewatchCount(v.getCount());
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

        mTitle.setText(mLibraryUpdate.getAnimeTitle());
        mSave.setEnabled(false);

        if (mRemoveListener != null) {
            mDelete.setVisibility(View.VISIBLE);
        }

        if (mAnimeDigest == null) {
            mModifyWatchCountView.setContent(mLibraryUpdate, mLibraryEntry);
        } else {
            mModifyWatchCountView.setContent(mLibraryUpdate, mAnimeDigest);
        }

        mModifyWatchingStatusSpinner.setContent(mLibraryUpdate);
        mModifyPublicPrivateSpinner.setContent(mLibraryUpdate);
        mModifyRatingSpinner.setContent(mLibraryUpdate);
        mModifyRewatchCountView.setContent(mLibraryUpdate);

        mRewatching.setChecked(mLibraryUpdate.isRewatching());
        mPersonalNotes.setText(mLibraryUpdate.getNotes());

        mModifyWatchCountView.setOnWatchCountChangedListener(this);
        mModifyWatchingStatusSpinner.setOnItemSelectedListener(this);
        mModifyPublicPrivateSpinner.setOnSelectionChangedListener(this);
        mModifyRatingSpinner.setOnItemSelectedListener(this);
        mModifyRewatchCountView.setOnRewatchCountChangedListener(this);
    }

    @Override
    public void onWatchCountChanged(final ModifyWatchCountView v) {
        mLibraryUpdate.setEpisodesWatched(v.getCount());
        update();
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
