package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.GroupMember;

import butterknife.ButterKnife;

public class GroupMemberItemView extends CardView implements AdapterView<GroupMember>,
        View.OnClickListener {

    private GroupMember mGroupMember;




    public GroupMemberItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupMemberItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(UserActivity.getLaunchIntent(context, mGroupMember.getUserId()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final GroupMember content) {
        mGroupMember = content;


    }

}
