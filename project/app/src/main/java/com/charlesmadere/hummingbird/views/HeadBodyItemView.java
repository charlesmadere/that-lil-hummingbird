package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadBodyItemView extends RelativeLayout {

    private CharSequence mBodyText;
    private CharSequence mHeadText;
    private Drawable mIconDrawable;

    @BindView(R.id.ivIcon)
    ImageView mIcon;

    @BindView(R.id.tvBody)
    TextView mBody;

    @BindView(R.id.tvHead)
    TextView mHead;


    public static HeadBodyItemView inflate(final ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return (HeadBodyItemView) inflater.inflate(R.layout.item_head_body, parent, false);
    }

    public HeadBodyItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public HeadBodyItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeadBodyItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() == 0) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(R.layout.item_head_body_internal, this);
        }

        ButterKnife.bind(this);

        mBody.setText(mBodyText);
        mHead.setText(mHeadText);
        setIcon(mIconDrawable);
    }

    protected void parseAttributes(@Nullable final AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.HeadBodyItemView);
        mBodyText = ta.getText(R.styleable.HeadBodyItemView_bodyText);
        mHeadText = ta.getText(R.styleable.HeadBodyItemView_headText);
        mIconDrawable = ta.getDrawable(R.styleable.HeadBodyItemView_iconDrawable);
        ta.recycle();
    }

    public void setBody(@Nullable final CharSequence text) {
        mBody.setText(text);
    }

    public void setBody(@StringRes final int textResId) {
        mBody.setText(textResId);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        mIcon.setEnabled(enabled);
        mBody.setEnabled(enabled);
        mHead.setEnabled(enabled);
    }

    public void setHead(@Nullable final CharSequence text) {
        mHead.setText(text);
    }

    public void setHead(@StringRes final int textResId) {
        mHead.setText(textResId);
    }

    public void setIcon(@Nullable final Drawable drawable) {
        mIcon.setImageDrawable(drawable);

        if (drawable == null) {
            mIcon.setVisibility(GONE);
        } else {
            mIcon.setVisibility(VISIBLE);
        }
    }

    public void setIcon(@DrawableRes final int drawableResId) {
        setIcon(ContextCompat.getDrawable(getContext(), drawableResId));
    }

}
