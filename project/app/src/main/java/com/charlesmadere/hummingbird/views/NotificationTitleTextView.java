package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;

public class NotificationTitleTextView extends KeyValueTextView {

    public NotificationTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public NotificationTitleTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(final AbsNotification notification) {
        final AbsNotification.AbsSource source = notification.getSource();

        switch (source.getType()) {
            case STORY:
                setText((AbsNotification.StorySource) source);
                break;

            case SUBSTORY:
                setText((AbsNotification.SubstorySource) source);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" +
                        source.getType() + '"');
        }
    }

    private void setText(final AbsNotification.StorySource source) {
        final AbsStory story = source.getStory();

        switch (story.getType()) {
            case COMMENT:
                setText((CommentStory) story);
                break;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + story.getType() + '"');
        }
    }

    private void setText(final AbsNotification.SubstorySource source) {
        final AbsSubstory substory = source.getSubstory();

        switch (substory.getType()) {
            case REPLY:
                setText((ReplySubstory) substory);
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + substory.getType() + '"');
        }
    }

    private void setText(final CommentStory story) {
        setText(story.getPosterId(), getResources().getText(
                R.string.wrote_a_comment_on_your_profile));
    }

    private void setText(final ReplySubstory substory) {
        setText(substory.getUserId(), getResources().getText(
                R.string.wrote_a_reply_to_your_comment));
    }

}
