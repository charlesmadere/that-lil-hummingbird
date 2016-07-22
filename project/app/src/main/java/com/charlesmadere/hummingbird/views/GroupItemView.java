package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.GroupActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Group;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupItemView extends CardView implements AdapterView<Group>, View.OnClickListener {

    private Group mGroup;
    private NumberFormat mNumberFormat;

    @BindView(R.id.sdvBackground)
    SimpleDraweeView mBackground;

    @BindView(R.id.tvGroupMembers)
    TextView mGroupMembers;

    @BindView(R.id.tvTitle)
    TextView mTitle;


    public GroupItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupItemView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View view) {
        final Context context = getContext();
        context.startActivity(GroupActivity.getLaunchIntent(context, mGroup));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mNumberFormat = NumberFormat.getInstance();
    }

    @Override
    public void setContent(final Group content) {
        mGroup = content;

        mBackground.setImageURI(mGroup.getCoverImageUrl());
        mTitle.setText(mGroup.getName());
        mGroupMembers.setText(getResources().getQuantityString(R.plurals.x_members,
                mGroup.getMemberCount(), mNumberFormat.format(mGroup.getMemberCount())));
    }

}
