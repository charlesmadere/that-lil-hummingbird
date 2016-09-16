package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.models.SimpleDate;
import com.charlesmadere.hummingbird.views.ReplySubstoryStandaloneItemView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class CommentsAdapter extends BaseMultiPaginationAdapter implements Comparator<AbsSubstory> {

    public CommentsAdapter(final Context context) {
        super(context);
    }

    @Override
    public int compare(final AbsSubstory o1, final AbsSubstory o2) {
        return SimpleDate.CHRONOLOGICAL_ORDER.compare(o1.getCreatedAt(), o2.getCreatedAt());
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(3);
        map.put(CommentStory.class, R.layout.item_comment_story_standalone);
        map.put(ReplySubstory.class, R.layout.item_reply_substory_standalone);
        map.put(String.class, R.layout.item_charsequence_plain);
        return map;
    }

    @Override
    public void onBindViewHolder(final AdapterView.ViewHolder holder, final int position) {
        if (holder.getAdapterView() instanceof ReplySubstoryStandaloneItemView) {
            final boolean showDivider = isPaginating() ? position + 2 < getItemCount()
                    : position + 1 < getItemCount();

            ((ReplySubstoryStandaloneItemView) holder.getAdapterView()).setContent(
                    (ReplySubstory) getItem(position), showDivider);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void set(final CommentStory commentStory, @Nullable final Feed feed) {
        final ArrayList<Object> list = new ArrayList<>();
        list.add(commentStory);

        if (feed == null || !feed.hasSubstories()) {
            list.add(getContext().getString(R.string.no_replies));
            super.set(list);
            return;
        }

        final ArrayList<AbsSubstory> substories = feed.getSubstories(AbsSubstory.Type.REPLY);
        if (substories != null && !substories.isEmpty()) {
            Collections.sort(substories, this);
            list.addAll(substories);
        }

        super.set(list);
    }

}
