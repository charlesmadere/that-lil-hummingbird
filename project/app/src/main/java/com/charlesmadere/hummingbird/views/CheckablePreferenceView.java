package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.preferences.BooleanPreference;
import com.charlesmadere.hummingbird.preferences.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckablePreferenceView extends RelativeLayout implements
        Preference.OnPreferenceChangeListener<Boolean>, View.OnClickListener {

    private static final int CHECKABLE_TYPE_CHECKBOX = 0;
    private static final int CHECKABLE_TYPE_SWITCH_COMPAT = 1;

    private BooleanPreference mPreference;
    private CharSequence mDisabledDescriptionText;
    private CharSequence mEnabledDescriptionText;
    private CharSequence mTitleText;
    private int mCheckableType;
    private OnPreferenceChangeListener opcl;

    @BindView(R.id.checkable)
    CompoundButton mCheckable;

    @BindView(R.id.tvDescription)
    TextView mDescription;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public CheckablePreferenceView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public CheckablePreferenceView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckablePreferenceView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
    }

    public boolean isChecked() {
        return mPreference != null && Boolean.TRUE.equals(mPreference.get());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refresh();
    }

    @Override
    public void onClick(final View view) {
        if (mPreference != null) {
            mPreference.toggle();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mPreference != null) {
            mPreference.removeListener(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final LayoutInflater inflater = LayoutInflater.from(getContext());

        if (mCheckableType == CHECKABLE_TYPE_CHECKBOX) {
            inflater.inflate(R.layout.preference_checkbox, this);
        } else if (mCheckableType == CHECKABLE_TYPE_SWITCH_COMPAT) {
            inflater.inflate(R.layout.preference_switch, this);
        } else {
            throw new RuntimeException("mCheckableType is an illegal value: " + mCheckableType);
        }

        ButterKnife.bind(this);
        setOnClickListener(this);

        mTitle.setText(mTitleText);
    }

    @Override
    public void onPreferenceChange(final Preference<Boolean> preference) {
        refresh();

        if (opcl != null) {
            opcl.onPreferenceChange(this);
        }
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.CheckablePreferenceView);
        mDisabledDescriptionText = ta.getText(R.styleable.CheckablePreferenceView_disabledDescriptionText);
        mEnabledDescriptionText = ta.getText(R.styleable.CheckablePreferenceView_enabledDescriptionText);
        mTitleText = ta.getText(R.styleable.CheckablePreferenceView_titleText);
        mCheckableType = ta.getInt(R.styleable.CheckablePreferenceView_checkable_type, -1);
        ta.recycle();
    }

    public void refresh() {
        mCheckable.setChecked(Boolean.TRUE.equals(mPreference.get()));

        if (mCheckable.isChecked()) {
            mDescription.setText(mEnabledDescriptionText);
        } else {
            mDescription.setText(mDisabledDescriptionText);
        }

        final LayoutParams titleParams = (LayoutParams) mTitle.getLayoutParams();
        final LayoutParams checkableParams = (LayoutParams) mCheckable.getLayoutParams();

        if (TextUtils.isEmpty(mDescription.getText())) {
            mDescription.setVisibility(GONE);
            titleParams.addRule(CENTER_VERTICAL);
            checkableParams.addRule(CENTER_VERTICAL);
        } else {
            mDescription.setVisibility(VISIBLE);
            titleParams.removeRule(CENTER_VERTICAL);
            checkableParams.removeRule(CENTER_VERTICAL);
        }

        mTitle.setLayoutParams(titleParams);
        mCheckable.setLayoutParams(checkableParams);
    }

    public void setBooleanPreference(final BooleanPreference preference) {
        if (mPreference != null) {
            mPreference.removeListener(this);
        }

        mPreference = preference;
        mPreference.addListener(this);

        refresh();
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        mCheckable.setEnabled(enabled);
        mDescription.setEnabled(enabled);
        mTitle.setEnabled(enabled);
    }

    public void setOnPreferenceChangeListener(@Nullable final OnPreferenceChangeListener l) {
        this.opcl = l;
    }


    public interface OnPreferenceChangeListener {
        void onPreferenceChange(final CheckablePreferenceView v);
    }

}
