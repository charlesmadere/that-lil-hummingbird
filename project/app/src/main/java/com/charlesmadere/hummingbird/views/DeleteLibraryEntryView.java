package com.charlesmadere.hummingbird.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.SimpleAnimatorListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteLibraryEntryView extends FrameLayout {

    private Listeners mListeners;


    public DeleteLibraryEntryView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public DeleteLibraryEntryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DeleteLibraryEntryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void fade(float start, float end, final int startVisibility, final int endVisibility) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(getResources().getInteger(R.integer.fade_duration));
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                setVisibility(endVisibility);
            }

            @Override
            public void onAnimationStart(final Animator animation) {
                setVisibility(startVisibility);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                setAlpha((float) animation.getAnimatedValue());
            }
        });

        animator.start();
    }

    public void fadeIn() {
        final float alpha = getAlpha();

        if (alpha == 1f && getVisibility() == VISIBLE) {
            return;
        }

        fade(alpha, 1f, VISIBLE, VISIBLE);
    }

    public void fadeOut() {
        final float alpha = getAlpha();

        if (alpha == 0f && getVisibility() == GONE) {
            return;
        }

        fade(alpha, 0f, VISIBLE, GONE);
    }

    public void hide() {
        setAlpha(0f);
        setVisibility(GONE);
    }

    @OnClick(R.id.tvCancel)
    void onCancelClick() {
        if (mListeners != null) {
            mListeners.onCancelClick(this);
        }
    }

    @OnClick(R.id.tvDelete)
    void onDeleteClick() {
        if (mListeners != null) {
            mListeners.onDeleteClick(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.delete_library_entry_view, this);

        ButterKnife.bind(this);
    }

    public void setListeners(@Nullable final Listeners listeners) {
        mListeners = listeners;
    }

    public void show() {
        setAlpha(1f);
        setVisibility(VISIBLE);
    }


    public interface Listeners {
        void onCancelClick(final DeleteLibraryEntryView v);
        void onDeleteClick(final DeleteLibraryEntryView v);
    }

}
