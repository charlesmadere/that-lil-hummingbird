package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.Story;
import com.charlesmadere.hummingbird.models.Substory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryItemFollowedView extends CardView implements AdapterView<Story>,
        View.OnClickListener {

    private NumberFormat mNumberFormat;
    private Story mStory;

    @BindView(R.id.sdvAvatar)
    SimpleDraweeView mAvatar;

    @BindView(R.id.sifsvZero)
    StoryItemFollowedSubstoryView mSubstoryZero;

    @BindView(R.id.sifsvOne)
    StoryItemFollowedSubstoryView mSubstoryOne;

    @BindView(R.id.sifsvTwo)
    StoryItemFollowedSubstoryView mSubstoryTwo;

    @BindView(R.id.tvTimeAgo)
    TextView mTimeAgo;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public StoryItemFollowedView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryItemFollowedView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Story getStory() {
        return mStory;
    }

    @Override
    public void onClick(final View v) {
        // TODO
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        setOnClickListener(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final Story content) {
        mStory = content;

        final AbsUser user = mStory.getUser();
        mAvatar.setImageURI(Uri.parse(user.getAvatar()));

        final Resources res = getResources();
        mTitle.setText(res.getString(R.string.x_y, user.getName(),
                res.getQuantityString(R.plurals.followed_x_users, mStory.getSubstoriesCount(),
                        mNumberFormat.format(mStory.getSubstoriesCount()))));
        mTimeAgo.setText(mStory.getUpdatedAt().getRelativeTimeText(getContext()));

        final ArrayList<Substory> substories = mStory.getSubstories();

        if (substories.size() >= 3) {
            mSubstoryTwo.setContent(substories.get(2));
            mSubstoryTwo.setVisibility(VISIBLE);
        } else {
            mSubstoryTwo.setVisibility(GONE);
        }

        if (substories.size() >= 2) {
            mSubstoryOne.setContent(substories.get(1));
            mSubstoryOne.setVisibility(VISIBLE);
        } else {
            mSubstoryOne.setVisibility(GONE);
        }

        mSubstoryZero.setContent(substories.get(0));
    }

}
