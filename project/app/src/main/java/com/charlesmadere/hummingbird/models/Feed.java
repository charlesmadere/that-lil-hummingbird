package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;

public class Feed implements Hydratable {

    @Nullable
    @SerializedName("story")
    private AbsStory mStory;

    @Nullable
    @SerializedName("anime")
    private ArrayList<Anime> mAnime;

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
    @SerializedName("library_entries")
    private ArrayList<AnimeLibraryEntry> mAnimeLibraryEntries;

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


    public void addAnime(@Nullable final ArrayList<Anime> anime) {
        if (anime == null || anime.isEmpty()) {
            return;
        }

        if (hasAnime()) {
            MiscUtils.exclusiveAdd(mAnime, anime);
        } else {
            mAnime = anime;
        }
    }

    @Nullable
    public ArrayList<Anime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<String> getAnimeIdsNeededForAnimeReviews() {
        if (!hasAnimeReviews()) {
            return null;
        }

        final ArrayList<String> animeIds = new ArrayList<>();

        // noinspection ConstantConditions
        for (final AnimeReview animeReview : mAnimeReviews) {
            if (animeReview.getAnime() == null) {
                final String animeId = animeReview.getAnimeId();

                if (!animeIds.contains(animeId)) {
                    animeIds.add(animeId);
                }
            }
        }

        if (animeIds.isEmpty()) {
            return null;
        } else {
            return animeIds;
        }
    }

    @Nullable
    public ArrayList<AnimeLibraryEntry> getAnimeLibraryEntries() {
        return mAnimeLibraryEntries;
    }

    public int getAnimeLibraryEntriesSize() {
        return mAnimeLibraryEntries == null ? 0 : mAnimeLibraryEntries.size();
    }

    @Nullable
    public ArrayList<AnimeReview> getAnimeReviews() {
        return mAnimeReviews;
    }

    public int getAnimeReviewsSize() {
        return mAnimeReviews == null ? 0 : mAnimeReviews.size();
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

    public int getGroupMembersSize() {
        return mGroupMembers == null ? 0 : mGroupMembers.size();
    }

    @Nullable
    public ArrayList<Group> getGroups() {
        return mGroups;
    }

    public int getGroupsSize() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Nullable
    public ArrayList<Manga> getManga() {
        return mManga;
    }

    @Nullable
    public ArrayList<MangaLibraryEntry> getMangaLibraryEntries() {
        return mMangaLibraryEntries;
    }

    public int getMangaLibraryEntriesSize() {
        return mMangaLibraryEntries == null ? 0 : mMangaLibraryEntries.size();
    }

    @Nullable
    public ArrayList<AbsNotification> getNotifications() {
        return mNotifications;
    }

    public int getNotificationsSize() {
        return mNotifications == null ? 0 : mNotifications.size();
    }

    @Nullable
    public ArrayList<AbsStory> getStories() {
        return mStories;
    }

    public int getStoriesSize() {
        return mStories == null ? 0 : mStories.size();
    }

    @Nullable
    public AbsStory getStory() {
        return mStory;
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

        // noinspection ConstantConditions
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

    public int getSubstoriesSize() {
        return mSubstories == null ? 0 : mSubstories.size();
    }

    @Nullable
    public ArrayList<User> getUsers() {
        return mUsers;
    }

    public int getUsersSize() {
        return mUsers == null ? 0 : mUsers.size();
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    public boolean hasAnimeLibraryEntries() {
        return mAnimeLibraryEntries != null && !mAnimeLibraryEntries.isEmpty();
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

    public boolean hasStory() {
        return mStory != null;
    }

    public boolean hasSubstories() {
        return mSubstories != null && !mSubstories.isEmpty();
    }

    public boolean hasUsers() {
        return mUsers != null && !mUsers.isEmpty();
    }

    @Override
    public void hydrate() {
        if (hasAnimeLibraryEntries()) {
            // noinspection ConstantConditions
            for (final AnimeLibraryEntry ale : mAnimeLibraryEntries) {
                ale.hydrate(this);
            }
        }

        if (hasAnimeReviews()) {
            // noinspection ConstantConditions
            final Iterator<AnimeReview> iterator = mAnimeReviews.iterator();

            do {
                final AnimeReview animeReview = iterator.next();

                if (!animeReview.hydrate(this)) {
                    iterator.remove();
                }
            } while (iterator.hasNext());
        }

        if (hasGroupMembers()) {
            // noinspection ConstantConditions
            for (final GroupMember groupMember : mGroupMembers) {
                groupMember.hydrate(this);
            }
        }

        if (hasGroups()) {
            // noinspection ConstantConditions
            for (final Group group : mGroups) {
                group.hydrate();
            }
        }

        if (hasMangaLibraryEntries()) {
            // noinspection ConstantConditions
            for (final MangaLibraryEntry mle : mMangaLibraryEntries) {
                mle.hydrate(this);
            }
        }

        if (hasNotifications()) {
            // noinspection ConstantConditions
            for (final AbsNotification notification : mNotifications) {
                notification.hydrate(this);
            }
        }

        if (hasSubstories()) {
            // noinspection ConstantConditions
            for (final AbsSubstory substory : mSubstories) {
                substory.hydrate(this);
            }
        }

        if (hasStories()) {
            // noinspection ConstantConditions
            for (final AbsStory story : mStories) {
                story.hydrate(this);
            }
        }

        if (hasStory()) {
            // noinspection ConstantConditions
            mStory.hydrate(this);
        }

        if (hasUsers()) {
            // noinspection ConstantConditions
            for (final User user : mUsers) {
                user.hydrate();
            }
        }
    }

    public void merge(@Nullable final Feed feed) {
        if (feed == null) {
            return;
        }

        addAnime(feed.getAnime());

        if (feed.hasAnimeLibraryEntries()) {
            if (hasAnimeLibraryEntries()) {
                MiscUtils.exclusiveAdd(mAnimeLibraryEntries, feed.getAnimeLibraryEntries());
            } else {
                mAnimeLibraryEntries = feed.getAnimeLibraryEntries();
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

        if (feed.hasMangaLibraryEntries()) {
            if (hasMangaLibraryEntries()) {
                MiscUtils.exclusiveAdd(mMangaLibraryEntries, feed.getMangaLibraryEntries());
            } else {
                mMangaLibraryEntries = feed.getMangaLibraryEntries();
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


    public static class Metadata implements Parcelable {
        @Nullable
        @SerializedName("cursor")
        private Integer mCursor;


        @Override
        public String toString() {
            return mCursor == null ? null : String.valueOf(mCursor);
        }

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
