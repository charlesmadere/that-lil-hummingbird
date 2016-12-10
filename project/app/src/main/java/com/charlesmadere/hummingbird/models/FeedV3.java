package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;

import java.util.ArrayList;

public class FeedV3 implements Hydratable, Parcelable {

    @Nullable
    private ArrayList<AbsStoryV3> mStories;

    @Nullable
    private ArrayList<AnimeV3> mAnime;

    @Nullable
    private ArrayList<LibraryEntry> mLibraryEntries;

    @Nullable
    private ArrayList<MangaV3> mManga;

    @Nullable
    private ArrayList<UserV3> mUsers;

    @Nullable
    private Links mLinks;


    private void addIncludes(@Nullable final ArrayList<DataObject> includes) {
        if (includes == null || includes.isEmpty()) {
            return;
        }

        for (final DataObject include : includes) {
            switch (include.getDataType()) {
                case ANIME:
                    if (mAnime == null) {
                        mAnime = new ArrayList<>();
                    }

                    MiscUtils.exclusiveAdd(mAnime, (AnimeV3) include);
                    break;

                case LIBRARY_ENTRIES:
                    if (mLibraryEntries == null) {
                        mLibraryEntries = new ArrayList<>();
                    }

                    MiscUtils.exclusiveAdd(mLibraryEntries, (LibraryEntry) include);
                    break;

                case MANGA:
                    if (mManga == null) {
                        mManga = new ArrayList<>();
                    }

                    MiscUtils.exclusiveAdd(mManga, (MangaV3) include);
                    break;

                case USERS:
                    if (mUsers == null) {
                        mUsers = new ArrayList<>();
                    }

                    MiscUtils.exclusiveAdd(mUsers, (UserV3) include);
                    break;
            }
        }
    }

    private void addStory(final AbsStoryV3 story) {
        if (mStories == null) {
            mStories = new ArrayList<>();
        }

        MiscUtils.exclusiveAdd(mStories, story);
    }

    @Nullable
    protected ArrayList<AnimeV3> getAnime() {
        return mAnime;
    }

    @Nullable
    protected ArrayList<LibraryEntry> getLibraryEntries() {
        return mLibraryEntries;
    }

    @Nullable
    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    protected ArrayList<MangaV3> getManga() {
        return mManga;
    }

    public AbsStoryV3 getStory(final int index) {
        if (mStories == null) {
            throw new NullPointerException();
        }

        final AbsStoryV3 story = mStories.get(index);
        story.hydrate(this);
        return story;
    }

    public int getStoriesSize() {
        return mStories == null ? 0 : mStories.size();
    }

    @Nullable
    protected ArrayList<UserV3> getUsers() {
        return mUsers;
    }

    protected boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    protected boolean hasLibraryEntries() {
        return mLibraryEntries != null && !mLibraryEntries.isEmpty();
    }

    protected boolean hasManga() {
        return mManga != null && !mManga.isEmpty();
    }

    public boolean hasStories() {
        return mStories != null && !mStories.isEmpty();
    }

    protected boolean hasUsers() {
        return mUsers != null && !mUsers.isEmpty();
    }

    @Override
    @WorkerThread
    public void hydrate() {
        if (hasStories()) {
            // noinspection ConstantConditions
            for (final AbsStoryV3 story : mStories) {
                story.hydrate(this);
            }
        }
    }

    private void setLinks(@Nullable final Links links) {
        mLinks = links;
    }

    @WorkerThread
    private void trimToSize() {
        if (mStories != null) {
            if (mStories.isEmpty()) {
                mStories = null;
            } else {
                mStories.trimToSize();
            }
        }

        if (mAnime != null) {
            if (mAnime.isEmpty()) {
                mAnime = null;
            } else {
                mAnime.trimToSize();
            }
        }

        if (mLibraryEntries != null) {
            if (mLibraryEntries.isEmpty()) {
                mLibraryEntries = null;
            } else {
                mLibraryEntries.trimToSize();
            }
        }

        if (mManga != null) {
            if (mManga.isEmpty()) {
                mManga = null;
            } else {
                mManga.trimToSize();
            }
        }

        if (mUsers != null) {
            if (mUsers.isEmpty()) {
                mUsers = null;
            } else {
                mUsers.trimToSize();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsStoryList(mStories, dest, flags);
        dest.writeTypedList(mAnime);
        dest.writeTypedList(mLibraryEntries);
        dest.writeTypedList(mManga);
        dest.writeTypedList(mUsers);
        dest.writeParcelable(mLinks, flags);
    }

    public static final Creator<FeedV3> CREATOR = new Creator<FeedV3>() {
        @Override
        public FeedV3 createFromParcel(final Parcel source) {
            final FeedV3 f = new FeedV3();
            f.mStories = ParcelableUtils.readAbsStoryList(source);
            f.mAnime = source.createTypedArrayList(AnimeV3.CREATOR);
            f.mLibraryEntries = source.createTypedArrayList(LibraryEntry.CREATOR);
            f.mManga = source.createTypedArrayList(MangaV3.CREATOR);
            f.mUsers = source.createTypedArrayList(UserV3.CREATOR);
            f.mLinks = source.readParcelable(Links.class.getClassLoader());
            return f;
        }

        @Override
        public FeedV3[] newArray(final int size) {
            return new FeedV3[size];
        }
    };


    public static class Builder implements Hydratable {
        private final ArrayResponse<ActionGroup> mResponse;
        private FeedV3 mFeed;

        public Builder(final ArrayResponse<ActionGroup> response) {
            mResponse = response;
        }

        private void findActions(final ArrayList<DataObject.Stub> array) {
            final ArrayList<DataObject> included = mResponse.getIncluded();

            ArrayList<Action> actions = null;

            for (final DataObject.Stub object : array) {
                final DataType dataType = object.getDataType();
                final String id = object.getId();

                // noinspection ConstantConditions
                for (final DataObject inc : included) {
                    if (dataType == inc.getDataType() && id.equals(inc.getId())) {
                        if (actions == null) {
                            actions = new ArrayList<>();
                        }

                        actions.add((Action) inc);
                    }
                }
            }

            if (actions == null || actions.isEmpty()) {
                return;
            }

            for (final Action action : actions) {
                switch (action.getVerb()) {
                    case COMMENT:
                        mFeed.addStory(new CommentStoryV3(action));
                        break;

                    case FOLLOW:
                        mFeed.addStory(new FollowStory(action));
                        break;

                    case POST:
                        mFeed.addStory(new PostStory(action));
                        break;

                    case PROGRESSED:
                        mFeed.addStory(new ProgressedStory(action));
                        break;

                    case RATED:
                        mFeed.addStory(new RatedStory(action));
                        break;

                    case REVIEWED:
                        mFeed.addStory(new ReviewedStory(action));
                        break;

                    case UPDATED:
                        mFeed.addStory(new UpdatedStory(action));
                        break;

                    default:
                        throw new RuntimeException("encountered unknown " +
                                Verb.class.getName() + ": \"" + action.getVerb() + '"');
                }
            }
        }

        @Nullable
        @WorkerThread
        public FeedV3 build() {
            hydrate();
            return mFeed;
        }

        @Nullable
        public FeedV3 getFeed() {
            return mFeed;
        }

        @Override
        @WorkerThread
        public void hydrate() {
            if (!mResponse.hasData() || !mResponse.hasIncluded()) {
                return;
            }

            if (mFeed == null) {
                mFeed = new FeedV3();
            }

            mFeed.addIncludes(mResponse.getIncluded());
            mFeed.setLinks(mResponse.getLinks());

            final ArrayList<ActionGroup> actionGroups = mResponse.getData();

            // noinspection ConstantConditions
            for (final ActionGroup actionGroup : actionGroups) {
                searchActionGroups(actionGroup);
            }

            mFeed.hydrate();
            mFeed.trimToSize();
        }

        private void searchActionGroups(final ActionGroup actionGroup) {
            final Relationships relationships = actionGroup.getRelationships();
            final Relationship activities = relationships.getActivities();

            if (activities == null) {
                return;
            }

            final ArrayList<DataObject.Stub> array;

            if (activities.hasArray()) {
                array = activities.getArray();
            } else if (activities.hasObject()) {
                array = new ArrayList<>(1);
                array.add(activities.getObject());
            } else {
                return;
            }

            findActions(array);
        }

        public Builder setFeed(@Nullable final FeedV3 feed) {
            mFeed = feed;
            return this;
        }
    }

}
