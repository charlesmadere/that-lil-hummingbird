package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

public class Feed implements Parcelable {

    @Nullable
    @SerializedName("anime")
    private ArrayList<AbsAnime> mAnime;

    @Nullable
    @SerializedName("notifications")
    private ArrayList<AbsNotification> mNotifications;

    @Nullable
    @SerializedName("stories")
    private ArrayList<AbsStory> mStories;

    @Nullable
    @SerializedName("substories")
    private ArrayList<AbsSubstory> mSubstories;

    @Nullable
    @SerializedName("reviews")
    private ArrayList<AnimeReview> mAnimeReviews;

    @Nullable
    @SerializedName("groups")
    private ArrayList<Group> mGroups;

    @Nullable
    @SerializedName("group_members")
    private ArrayList<GroupMember> mGroupMembers;

    @Nullable
    @SerializedName("manga")
    private ArrayList<Manga> mManga;

    @Nullable
    @SerializedName("manga_library_entries")
    private ArrayList<MangaLibraryEntry> mMangaLibraryEntries;

    @Nullable
    @SerializedName("users")
    private ArrayList<User> mUsers;

    @Nullable
    @SerializedName("meta")
    private Metadata mMetadata;


    @Nullable
    public ArrayList<AbsAnime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<AnimeReview> getAnimeReviews() {
        return mAnimeReviews;
    }

    public int getCursor() {
        if (mMetadata == null || mMetadata.mCursor == null) {
            return 1;
        } else {
            return mMetadata.mCursor;
        }
    }

    @Nullable
    public ArrayList<GroupMember> getGroupMembers() {
        return mGroupMembers;
    }

    @Nullable
    public ArrayList<Group> getGroups() {
        return mGroups;
    }

    @Nullable
    public ArrayList<Manga> getManga() {
        return mManga;
    }

    @Nullable
    public ArrayList<MangaLibraryEntry> getMangaLibraryEntries() {
        return mMangaLibraryEntries;
    }

    @Nullable
    public ArrayList<AbsNotification> getNotifications() {
        return mNotifications;
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
    public ArrayList<AbsSubstory> getSubstories(final AbsSubstory.Type type) {
        if (!hasSubstories()) {
            return null;
        }

        final ArrayList<AbsSubstory> substories = new ArrayList<>(mSubstories.size());

        for (final AbsSubstory substory : mSubstories) {
            if (substory.getType() == type) {
                substories.add(substory);
            }
        }

        if (substories.isEmpty()) {
            return null;
        } else {
            substories.trimToSize();
            return substories;
        }
    }

    @Nullable
    public ArrayList<User> getUsers() {
        return mUsers;
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    public boolean hasAnimeReviews() {
        return mAnimeReviews != null && !mAnimeReviews.isEmpty();
    }

    public boolean hasGroupMembers() {
        return mGroupMembers != null && !mGroupMembers.isEmpty();
    }

    public boolean hasGroups() {
        return mGroups != null && !mGroups.isEmpty();
    }

    public boolean hasManga() {
        return mManga != null && !mManga.isEmpty();
    }

    public boolean hasMangaLibraryEntries() {
        return mMangaLibraryEntries != null && !mMangaLibraryEntries.isEmpty();
    }

    public boolean hasCursor() {
        return mMetadata != null && mMetadata.mCursor != null;
    }

    public boolean hasNotifications() {
        return mNotifications != null && !mNotifications.isEmpty();
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
        if (hasGroupMembers()) {
            for (final GroupMember groupMember : mGroupMembers) {
                groupMember.hydrate(this);
            }
        }

        if (hasGroups()) {
            for (final Group group : mGroups) {
                group.hydrate(this);
            }
        }

        if (hasMangaLibraryEntries()) {
            for (final MangaLibraryEntry mle : mMangaLibraryEntries) {
                mle.hydrate(this);
            }
        }

        if (hasNotifications()) {
            for (final AbsNotification notification : mNotifications) {
                notification.hydrate(this);
            }
        }

        if (hasSubstories()) {
            Collections.sort(mSubstories, AbsSubstory.COMPARATOR);

            for (final AbsSubstory substory : mSubstories) {
                substory.hydrate(this);
            }
        }

        if (hasStories()) {
            for (final AbsStory story : mStories) {
                story.hydrate(this);
            }
        }
    }

    public void merge(@Nullable final Feed feed) {
        if (feed == null) {
            return;
        }

        if (feed.hasAnime()) {
            if (hasAnime()) {
                MiscUtils.exclusiveAdd(mAnime, feed.getAnime());
            } else {
                mAnime = feed.getAnime();
            }
        }

        if (feed.hasAnimeReviews()) {
            if (hasAnimeReviews()) {
                MiscUtils.exclusiveAdd(mAnimeReviews, feed.getAnimeReviews());
            } else {
                mAnimeReviews = feed.getAnimeReviews();
            }
        }

        if (feed.hasGroupMembers()) {
            if (hasGroupMembers()) {
                MiscUtils.exclusiveAdd(mGroupMembers, feed.getGroupMembers());
            } else {
                mGroupMembers = feed.getGroupMembers();
            }
        }

        if (feed.hasGroups()) {
            if (hasGroups()) {
                MiscUtils.exclusiveAdd(mGroups, feed.getGroups());
            } else {
                mGroups = feed.getGroups();
            }
        }

        if (feed.hasManga()) {
            if (hasManga()) {
                MiscUtils.exclusiveAdd(mManga, feed.getManga());
            } else {
                mManga = feed.getManga();
            }
        }

        if (feed.hasNotifications()) {
            if (hasNotifications()) {
                MiscUtils.exclusiveAdd(mNotifications, feed.getNotifications());
            } else {
                mNotifications = feed.getNotifications();
            }
        }

        if (feed.hasStories()) {
            if (hasStories()) {
                MiscUtils.exclusiveAdd(mStories, feed.getStories());
            } else {
                mStories = feed.getStories();
            }
        }

        if (feed.hasSubstories()) {
            if (hasSubstories()) {
                MiscUtils.exclusiveAdd(mSubstories, feed.getSubstories());
            } else {
                mSubstories = feed.getSubstories();
            }
        }

        if (feed.hasUsers()) {
            if (hasUsers()) {
                MiscUtils.exclusiveAdd(mUsers, feed.getUsers());
            } else {
                mUsers = feed.getUsers();
            }
        }

        mMetadata = feed.mMetadata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsAnimeListToParcel(mAnime, dest, flags);
        ParcelableUtils.writeAbsNotificationListToParcel(mNotifications, dest, flags);
        ParcelableUtils.writeAbsStoryListToParcel(mStories, dest, flags);
        ParcelableUtils.writeAbsSubstoryListToParcel(mSubstories, dest, flags);
        dest.writeTypedList(mAnimeReviews);
        dest.writeTypedList(mGroups);
        dest.writeTypedList(mGroupMembers);
        dest.writeTypedList(mManga);
        dest.writeTypedList(mMangaLibraryEntries);
        dest.writeTypedList(mUsers);
        dest.writeParcelable(mMetadata, flags);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(final Parcel source) {
            final Feed f = new Feed();
            f.mAnime = ParcelableUtils.readAbsAnimeListFromParcel(source);
            f.mNotifications = ParcelableUtils.readAbsNotificationListFromParcel(source);
            f.mStories = ParcelableUtils.readAbsStoryListFromParcel(source);
            f.mSubstories = ParcelableUtils.readAbsSubstoryListFromParcel(source);
            f.mAnimeReviews = source.createTypedArrayList(AnimeReview.CREATOR);
            f.mGroups = source.createTypedArrayList(Group.CREATOR);
            f.mGroupMembers = source.createTypedArrayList(GroupMember.CREATOR);
            f.mManga = source.createTypedArrayList(Manga.CREATOR);
            f.mMangaLibraryEntries = source.createTypedArrayList(MangaLibraryEntry.CREATOR);
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
        @Nullable
        @SerializedName("cursor")
        private Integer mCursor;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            ParcelableUtils.writeInteger(mCursor, dest);
        }

        public static final Creator<Metadata> CREATOR = new Creator<Metadata>() {
            @Override
            public Metadata createFromParcel(final Parcel source) {
                final Metadata m = new Metadata();
                m.mCursor = ParcelableUtils.readInteger(source);
                return m;
            }

            @Override
            public Metadata[] newArray(final int size) {
                return new Metadata[size];
            }
        };
    }

}
