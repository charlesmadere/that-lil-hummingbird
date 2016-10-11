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
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.views.ModifyNumberView;
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateSpinner;
import com.charlesmadere.hummingbird.views.ModifyRatingSpinner;
import com.charlesmadere.hummingbird.views.ModifyReadingStatusSpinner;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MangaLibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateSpinner.OnItemSelectedListener,
        ModifyRatingSpinner.OnItemSelectedListener,
        ModifyReadingStatusSpinner.OnItemSelectedListener {

    public static final String TAG = "MangaLibraryUpdateFragment";
    private static final String KEY_LIBRARY_ENTRY_ID = "LibraryEntryId";
    private static final String KEY_LIBRARY_UPDATE = "LibraryUpdate";

    private Listener mListener;
    private MangaLibraryUpdate mLibraryUpdate;
    private String mLibraryEntryId;

    @BindView(R.id.cbReReading)
    CheckBox mReReading;

    @BindView(R.id.etPersonalNotes)
    EditText mPersonalNotes;

    @BindView(R.id.ibSave)
    ImageButton mSave;

    @BindView(R.id.mnvChaptersRead)
    ModifyNumberView mChaptersRead;

    @BindView(R.id.mnvReReadCount)
    ModifyNumberView mReReadCount;

    @BindView(R.id.mnvVolumesRead)
    ModifyNumberView mVolumesRead;

    @BindView(R.id.modifyPublicPrivateSpinner)
    ModifyPublicPrivateSpinner mModifyPublicPrivateSpinner;

    @BindView(R.id.modifyRatingSpinner)
    ModifyRatingSpinner mModifyRatingSpinner;

    @BindView(R.id.modifyReadingStatusSpinner)
    ModifyReadingStatusSpinner mModifyReadingStatusSpinner;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public static MangaLibraryUpdateFragment create() {
        return new MangaLibraryUpdateFragment();
    }

    public static MangaLibraryUpdateFragment create(final String libraryEntryId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_LIBRARY_ENTRY_ID, libraryEntryId);

        final MangaLibraryUpdateFragment fragment = new MangaLibraryUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public MangaDigest getDigest() {
        return ((DigestListener) mListener).getMangaDigest();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    public MangaLibraryEntry getLibraryEntry() {
        return ((LibraryEntryListener) mListener).getMangaLibraryEntry(mLibraryEntryId);
    }

    public String getLibraryEntryId() {
        return mLibraryEntryId;
    }

    public MangaLibraryUpdate getLibraryUpdate() {
        return mLibraryUpdate;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibraryUpdate = savedInstanceState.getParcelable(KEY_LIBRARY_UPDATE);
        }

        final MangaDigest digest;
        final MangaLibraryEntry libraryEntry;

        if (TextUtils.isEmpty(mLibraryEntryId)) {
            digest = getDigest();
            libraryEntry = null;
        } else {
            digest = null;
            libraryEntry = getLibraryEntry();
        }

        if (mLibraryUpdate == null) {
            if (TextUtils.isEmpty(mLibraryEntryId)) {
                mLibraryUpdate = new MangaLibraryUpdate(digest);
            } else {
                mLibraryUpdate = new MangaLibraryUpdate(libraryEntry);
            }
        }

        mTitle.setText(mLibraryUpdate.getMangaTitle());
        mSave.setEnabled(false);

        if (TextUtils.isEmpty(mLibraryEntryId)) {
            mChaptersRead.setForChaptersRead(mLibraryUpdate, digest);
            mVolumesRead.setForVolumesRead(mLibraryUpdate, digest);
        } else {
            mChaptersRead.setForChaptersRead(mLibraryUpdate, libraryEntry);
            mVolumesRead.setForVolumesRead(mLibraryUpdate, libraryEntry);
        }

        mModifyPublicPrivateSpinner.setContent(mLibraryUpdate);
        mModifyRatingSpinner.setContent(mLibraryUpdate);
        mModifyReadingStatusSpinner.setContent(mLibraryUpdate);

        mReReading.setChecked(mLibraryUpdate.isReReading());
        mReReadCount.setForReReadCount(mLibraryUpdate);
        mPersonalNotes.setText(mLibraryUpdate.getNotes());

        mChaptersRead.setListener(new ModifyNumberView.Listener() {
            @Override
            public void onNumberChanged(final ModifyNumberView v) {
                mLibraryUpdate.setChaptersRead(v.getNumber());
                update();
            }
        });

        mVolumesRead.setListener(new ModifyNumberView.Listener() {
            @Override
            public void onNumberChanged(final ModifyNumberView v) {
                mLibraryUpdate.setVolumesRead(v.getNumber());
                update();
            }
        });

        mModifyPublicPrivateSpinner.setOnItemSelectedListener(this);
        mModifyRatingSpinner.setOnItemSelectedListener(this);
        mModifyReadingStatusSpinner.setOnItemSelectedListener(this);

        mReReadCount.setListener(new ModifyNumberView.Listener() {
            @Override
            public void onNumberChanged(final ModifyNumberView v) {
                mLibraryUpdate.setReReadCount(v.getNumber());
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
        dismissAllowingStateLoss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if (args != null && !args.isEmpty()) {
            mLibraryEntryId = args.getString(KEY_LIBRARY_ENTRY_ID);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library_update, container, false);
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
    public void onItemSelected(final ModifyReadingStatusSpinner v) {
        mLibraryUpdate.setReadingStatus(v.getSelectedItem());
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

    @OnClick(R.id.llReReading)
    void onReReadingClick() {
        mReReading.toggle();
        mLibraryUpdate.setReReading(mReReading.isChecked());
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {
        mListener.onUpdateLibraryEntry();
        dismissAllowingStateLoss();
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


    public interface DigestListener extends Listener {
        MangaDigest getMangaDigest();
    }

    public interface LibraryEntryListener extends Listener {
        MangaLibraryEntry getMangaLibraryEntry(final String libraryEntryId);
    }

    private interface Listener {
        void onUpdateLibraryEntry();
    }

}
