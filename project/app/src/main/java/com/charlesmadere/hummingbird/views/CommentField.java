package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class CommentField extends LinearLayout {

    private Listener mListener;

    @BindView(R.id.etCommentField)
    EditText mCommentField;

    @BindView(R.id.ibPostComment)
    ImageButton mPostComment;


    public CommentField(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentField(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentField(final Context context, final AttributeSet attrs, final int defStyleAttr,
            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void clear() {
        mCommentField.setText("");
    }

    public String getComment() {
        return mCommentField.getText().toString().trim();
    }

    public boolean isValid() {
        final CharSequence text = mCommentField.getText();
        return !TextUtils.isEmpty(text) && TextUtils.getTrimmedLength(text) >= 1;
    }

    @OnTextChanged(R.id.etCommentField)
    void onCommentFieldTextChanged() {
        mPostComment.setEnabled(isValid());
    }

    @OnEditorAction(R.id.etCommentField)
    boolean onCommentFieldEditorAction(final int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEND && isValid() && mListener != null) {
            mListener.onCommentPosted();
        }

        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ibPostComment)
    void onPostCommentClick() {
        if (isValid() && mListener != null) {
            mListener.onCommentPosted();
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        mCommentField.setEnabled(enabled);
        mPostComment.setEnabled(isValid());
    }

    public void setListener(@Nullable final Listener listener) {
        mListener = listener;
    }


    public interface Listener {
        void onCommentPosted();
    }

}
