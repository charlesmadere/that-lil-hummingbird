package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.LibraryUpdate;
import com.charlesmadere.hummingbird.models.WatchingStatus;
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
    private static final String KEY_LIBRARY_ENTRY = "LibraryEntry";
    private static final String KEY_LIBRARY_UPDATE = "LibraryUpdate";

    private LibraryEntry mLibraryEntry;
    private LibraryUpdate mLibraryUpdate;
    private Listeners mListeners;

    @BindView(R.id.cbRewatching)
    CheckBox mRewatching;

    @BindView(R.id.etPersonalNotes)
    EditText mPersonalNotes;

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


    public static AnimeLibraryUpdateFragment create(final LibraryEntry libraryEntry) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_LIBRARY_ENTRY, libraryEntry);

        final AnimeLibraryUpdateFragment fragment = new AnimeLibraryUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    public LibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    public LibraryUpdate getLibraryUpdate() {
        return mLibraryUpdate;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listeners) {
            mListeners = (Listeners) fragment;
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof Listeners) {
                mListeners = (Listeners) activity;
            }
        }

        if (mListeners == null) {
            throw new IllegalStateException(TAG + " must have a Listener");
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
        mLibraryEntry = args.getParcelable(KEY_LIBRARY_ENTRY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibraryUpdate = savedInstanceState.getParcelable(KEY_LIBRARY_UPDATE);
        }

        if (mLibraryUpdate == null) {
            mLibraryUpdate = new LibraryUpdate(mLibraryEntry);
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
                        mListeners.onRemoveLibraryEntry();
                        dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(final ModifyPublicPrivateSpinner v) {
        mLibraryUpdate.setPrivacy(v.getSelectedItem(), mLibraryEntry);
        update();
    }

    @Override
    public void onItemSelected(final ModifyRatingSpinner v) {
        mLibraryUpdate.setRating(v.getSelectedItem(), mLibraryEntry);
        update();
    }

    @Override
    public void onItemSelected(final ModifyWatchingStatusSpinner v) {
        final WatchingStatus watchingStatus = v.getSelectedItem();
        mLibraryUpdate.setWatchingStatus(watchingStatus, mLibraryEntry);

        if (WatchingStatus.COMPLETED.equals(watchingStatus)) {
            final AbsAnime anime = mLibraryEntry.getAnime();

            if (anime.hasEpisodeCount()) {
                mModifyWatchCountView.setCountAndMax(anime.getEpisodeCount(),
                        anime.getEpisodeCount());
            } else if (mLibraryUpdate.getEpisodesWatched() != null &&
                    mLibraryUpdate.getEpisodesWatched() == 0) {
                mModifyWatchCountView.setCountAndMax(1, 1);
            }
        }

        update();
    }

    @OnTextChanged(R.id.etPersonalNotes)
    void onPersonalNotesTextChanged() {
        final CharSequence charSequence = mPersonalNotes.getText();
        String string = null;

        if (!TextUtils.isEmpty(charSequence)) {
            string = charSequence.toString().trim();
        }

        mLibraryUpdate.setNotes(string, mLibraryEntry);
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onRewatchCountChanged(final ModifyRewatchCountView v) {
        mLibraryUpdate.setRewatchedTimes(v.getCount(), mLibraryEntry);
        update();
    }

    @OnClick(R.id.llRewatching)
    void onRewatchingClick() {
        mRewatching.toggle();
        mLibraryUpdate.setRewatching(mRewatching.isChecked(), mLibraryEntry);
        update();
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {
        mListeners.onUpdateLibraryEntry();
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

        mTitle.setText(mLibraryEntry.getAnime().getTitle());
        mSave.setEnabled(false);

        mModifyWatchCountView.setContent(mLibraryEntry);
        mModifyWatchingStatusSpinner.setContent(mLibraryEntry);
        mModifyPublicPrivateSpinner.setContent(mLibraryEntry);
        mModifyRatingSpinner.setContent(mLibraryEntry);
        mModifyRewatchCountView.setContent(mLibraryEntry);

        mRewatching.setChecked(mLibraryEntry.isRewatching());
        mPersonalNotes.setText(mLibraryEntry.getNotes());

        mModifyWatchCountView.setOnWatchCountChangedListener(this);
        mModifyWatchingStatusSpinner.setOnItemSelectedListener(this);
        mModifyPublicPrivateSpinner.setOnSelectionChangedListener(this);
        mModifyRatingSpinner.setOnItemSelectedListener(this);
        mModifyRewatchCountView.setOnRewatchCountChangedListener(this);
    }

    @Override
    public void onWatchCountChanged(final ModifyWatchCountView v) {
        final int count = v.getCount();
        mLibraryUpdate.setEpisodesWatched(count, mLibraryEntry);

        if (mLibraryEntry.getAnime().hasEpisodeCount()) {
            if (mLibraryEntry.getAnime().getEpisodeCount() == count) {
                mModifyWatchingStatusSpinner.setWatchingStatus(WatchingStatus.COMPLETED);
            } else {
                mModifyWatchingStatusSpinner.setWatchingStatus(mLibraryEntry.getStatus());
            }
        }

        update();
    }

    private void update() {
        mSave.setEnabled(mLibraryUpdate.containsModifications());
    }


    public interface Listeners {
        void onRemoveLibraryEntry();
        void onUpdateLibraryEntry();
    }

}