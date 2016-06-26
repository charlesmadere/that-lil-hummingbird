package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.ModifyPublicPrivateView;
import com.charlesmadere.hummingbird.views.ModifyRatingSpinner;
import com.charlesmadere.hummingbird.views.ModifyWatchCountView;
import com.charlesmadere.hummingbird.views.WatchingStatusUpdateSpinner;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

public class LibraryUpdateFragment extends BaseBottomSheetDialogFragment implements
        ModifyPublicPrivateView.OnSelectionChangedListener,
        ModifyRatingSpinner.OnItemSelectedListener,
        ModifyWatchCountView.OnWatchCountChangedListener,
        WatchingStatusUpdateSpinner.OnItemSelectedListener {

    public static final String TAG = "LibraryUpdateFragment";

    @BindView(R.id.cbRewatching)
    CheckBox mRewatching;

    @BindView(R.id.ibSave)
    ImageButton mSave;

    @BindView(R.id.modifyPublicPrivateView)
    ModifyPublicPrivateView mModifyPublicPrivateView;

    @BindView(R.id.modifyRatingSpinner)
    ModifyRatingSpinner mModifyRatingSpinner;

    @BindView(R.id.modifyWatchCountView)
    ModifyWatchCountView mModifyWatchCountView;

    @BindView(R.id.sdvPoster)
    SimpleDraweeView mPoster;

    @BindView(R.id.watchingStatusUpdateSpinner)
    WatchingStatusUpdateSpinner mWatchingStatusUpdateSpinner;


    public static LibraryUpdateFragment create() {
        final Bundle args = new Bundle();
        // TODO

        final LibraryUpdateFragment fragment = new LibraryUpdateFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @OnClick(R.id.ibClose)
    void onCloseClick() {
        dismiss();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_library_update, container, false);
    }

    @Override
    public void onItemSelected(final ModifyRatingSpinner v) {
        // TODO
    }

    @Override
    public void onItemSelected(final WatchingStatusUpdateSpinner v) {
        // TODO
    }

    @OnClick(R.id.llRewatching)
    void onRewatchingClick() {
        // TODO
    }

    @OnClick(R.id.ibSave)
    void onSaveClick() {
        // TODO
    }

    @Override
    public void onSelectionChanged(final ModifyPublicPrivateView v) {
        // TODO
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mModifyPublicPrivateView.setOnSelectionChangedListener(this);
        mModifyRatingSpinner.setOnItemSelectedListener(this);
        mModifyWatchCountView.setOnWatchCountChangedListener(this);
    }

    @Override
    public void onWatchCountChanged(final ModifyWatchCountView v) {
        // TODO
    }

}
