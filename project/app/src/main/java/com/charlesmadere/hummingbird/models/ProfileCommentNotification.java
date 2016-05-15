package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileCommentNotification extends AbsNotification implements Parcelable {

    public AbsUser getPoster() {
        final AbsSource source = getSource();

        switch (source.getType()) {
            case STORY:
                return getPoster(((StorySource) source).getStory());

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" +
                        source.getType() + '"');
        }
    }

    private AbsUser getPoster(final AbsStory story) {
        switch (story.getType()) {
            case COMMENT:
                return ((CommentStory) story).getPoster();

            default:
                return story.getUser();
        }
    }

    @Override
    public Type getType() {
        return Type.PROFILE_COMMENT;
    }

    public static final Creator<ProfileCommentNotification> CREATOR = new Creator<ProfileCommentNotification>() {
        @Override
        public ProfileCommentNotification createFromParcel(final Parcel source) {
            final ProfileCommentNotification pcn = new ProfileCommentNotification();
            pcn.readFromParcel(source);
            return pcn;
        }

        @Override
        public ProfileCommentNotification[] newArray(final int size) {
            return new ProfileCommentNotification[size];
        }
    };

}
