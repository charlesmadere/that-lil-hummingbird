package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed implements Parcelable {

    @Nullable
    @SerializedName("anime")
    private ArrayList<AbsAnime> mAnime;

    @Nullable
    @SerializedName("stories")
    private ArrayList<AbsStory> mStories;

    @Nullable
    @SerializedName("substories")
    private ArrayList<AbsSubstory> mSubstories;

    @Nullable
    @SerializedName("groups")
    private ArrayList<Group> mGroups;

    @Nullable
    @SerializedName("group_members")
    private ArrayList<GroupMember> mGroupMembers;

    @Nullable
    @SerializedName("users")
    private ArrayList<User> mUsers;

    @SerializedName("meta")
    private Metadata mMetadata;


    @Nullable
    public ArrayList<AbsAnime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<GroupMember> getGroupMembers() {
        return mGroupMembers;
    }

    @Nullable
    public ArrayList<Group> getGroups() {
        return mGroups;
    }

    public Metadata getMetadata() {
        return mMetadata;
    }

    @Nullable
    public ArrayList<AbsStory> getStories() {
        return mStories;
    }

    @Nullable
    public ArrayList<AbsSubstory> getSubstories() {
        return mSubstories;
    }

    @Nullable
    public ArrayList<User> getUsers() {
        return mUsers;
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    public boolean hasGroupMembers() {
        return mGroupMembers != null && !mGroupMembers.isEmpty();
    }

    public boolean hasGroups() {
        return mGroups != null && !mGroups.isEmpty();
    }

    public boolean hasStories() {
        return mStories != null && !mStories.isEmpty();
    }

    public boolean hasSubstories() {
        return mSubstories != null && !mSubstories.isEmpty();
    }

    public boolean hasUsers() {
        return mUsers != null && !mUsers.isEmpty();
    }

    public void hydrate() {
        if (!hasStories()) {
            // nothing to do
            return;
        }

        if (hasSubstories()) {
            for (final AbsSubstory substory : mSubstories) {
                if (substory.getType() == AbsSubstory.Type.REPLY) {
                    ((ReplySubstory) substory).hydrate(mUsers);
                }
            }
        }

        for (final AbsStory story : mStories) {
            story.hydrate(mSubstories, mUsers);

            switch (story.getType()) {
                case COMMENT:
                    hydrateStory((CommentStory) story);
                    break;

                case FOLLOWED:
                    hydrateStory((FollowedStory) story);

                case MEDIA_STORY:
                    hydrateStory((MediaStory) story);
                    break;

                default:
                    throw new RuntimeException("encountered unknown " +
                            AbsStory.Type.class.getName() + ": \"" + story.getType() + '"');
            }
        }
    }

    private void hydrateStory(final CommentStory story) {
        if (hasGroups()) {
            final String groupId = story.getGroupId();

            for (final Group group : mGroups) {
                if (groupId.equalsIgnoreCase(group.getId())) {
                    story.setGroup(group);
                    break;
                }
            }
        }

        if (hasUsers()) {
            final String posterId = story.getPosterId();

            for (final User user : mUsers) {
                if (posterId.equalsIgnoreCase(user.getName())) {
                    story.setPoster(user);
                    break;
                }
            }
        }
    }

    private void hydrateStory(final FollowedStory story) {
        // TODO
    }

    private void hydrateStory(final MediaStory story) {
        final MediaStory.AbsMedia media = story.getMedia();

        switch (media.getType()) {
            case ANIME:
                if (hasAnime()) {
                    final MediaStory.AnimeMedia animeMedia = (MediaStory.AnimeMedia) media;
                    final String animeId = animeMedia.getId();

                    for (final AbsAnime anime : mAnime) {
                        if (animeId.equalsIgnoreCase(anime.getId())) {
                            animeMedia.setAnime(anime);
                            break;
                        }
                    }
                }
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        MediaStory.AbsMedia.Type.class.getName() + ": \"" + media.getType() + '"');
        }
    }

    public void merge(@Nullable final Feed feed) {
        if (feed == null) {
            return;
        }

        if (feed.hasAnime()) {
            if (hasAnime()) {
                mAnime.addAll(feed.getAnime());
            } else {
                mAnime = feed.getAnime();
            }
        }

        if (feed.hasGroupMembers()) {
            if (hasGroupMembers()) {
                mGroupMembers.addAll(feed.getGroupMembers());
            } else {
                mGroupMembers = feed.getGroupMembers();
            }
        }

        if (feed.hasGroups()) {
            if (hasGroups()) {
                mGroups.addAll(feed.getGroups());
            } else {
                mGroups = feed.getGroups();
            }
        }

        if (feed.hasStories()) {
            if (hasStories()) {
                mStories.addAll(feed.getStories());
            } else {
                mStories = feed.getStories();
            }
        }

        if (feed.hasSubstories()) {
            if (hasSubstories()) {
                mSubstories.addAll(feed.getSubstories());
            } else {
                mSubstories = feed.getSubstories();
            }
        }

        if (feed.hasUsers()) {
            if (hasUsers()) {
                mUsers.addAll(feed.getUsers());
            } else {
                mUsers = feed.getUsers();
            }
        }

        mMetadata.setCursor(feed.getMetadata().getCursor() + 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsAnimeListToParcel(mAnime, dest, flags);
        ParcelableUtils.writeAbsStoryListToParcel(mStories, dest, flags);
        ParcelableUtils.writeAbsSubstoryListToParcel(mSubstories, dest, flags);
        dest.writeTypedList(mGroups);
        dest.writeTypedList(mGroupMembers);
        dest.writeTypedList(mUsers);
        dest.writeParcelable(mMetadata, flags);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(final Parcel source) {
            final Feed f = new Feed();
            f.mAnime = ParcelableUtils.readAbsAnimeListFromParcel(source);
            f.mStories = ParcelableUtils.readAbsStoryListFromParcel(source);
            f.mSubstories = ParcelableUtils.readAbsSubstoryListFromParcel(source);
            f.mGroups = source.createTypedArrayList(Group.CREATOR);
            f.mGroupMembers = source.createTypedArrayList(GroupMember.CREATOR);
            f.mUsers = source.createTypedArrayList(User.CREATOR);
            f.mMetadata = source.readParcelable(Metadata.class.getClassLoader());
            return f;
        }

        @Override
        public Feed[] newArray(final int size) {
            return new Feed[size];
        }
    };


    public static class Metadata implements Parcelable {
        @SerializedName("cursor")
        private int mCursor;


        public int getCursor() {
            return mCursor;
        }

        public void setCursor(final int cursor) {
            mCursor = cursor;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mCursor);
        }

        public static final Creator<Metadata> CREATOR = new Creator<Metadata>() {
            @Override
            public Metadata createFromParcel(final Parcel source) {
                final Metadata m = new Metadata();
                m.mCursor = source.readInt();
                return m;
            }

            @Override
            public Metadata[] newArray(final int size) {
                return new Metadata[size];
            }
        };
    }

}
