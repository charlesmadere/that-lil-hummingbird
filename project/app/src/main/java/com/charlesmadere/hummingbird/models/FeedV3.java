package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;

import java.util.ArrayList;

public class FeedV3 implements Parcelable {

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

        mStories.add(story);
    }

    @Nullable
    public Links getLinks() {
        return mLinks;
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

    public boolean hasStories() {
        return mStories != null && !mStories.isEmpty();
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

        private void buildCommentStory(final Action action) {
            final CommentStoryV3 story = new CommentStoryV3(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildFollowStory(final Action action) {
            final FollowStory story = new FollowStory(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildPostStory(final Action action) {
            final PostStory story = new PostStory(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildProgressStory(final Action action) {
            final ProgressedStory story = new ProgressedStory(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildRatedStory(final Action action) {
            final RatedStory story = new RatedStory(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildReviewedStory(final Action action) {
            final ReviewedStory story = new ReviewedStory(action.getId());
            // TODO

            mFeed.addStory(story);
        }

        private void buildUpdatedStory(final Action action) {
            final UpdatedStory story = new UpdatedStory(action.getId());
            // TODO

            mFeed.addStory(story);
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
                        buildCommentStory(action);
                        break;

                    case FOLLOW:
                        buildFollowStory(action);
                        break;

                    case POST:
                        buildPostStory(action);
                        break;

                    case PROGRESSED:
                        buildProgressStory(action);
                        break;

                    case RATED:
                        buildRatedStory(action);
                        break;

                    case REVIEWED:
                        buildReviewedStory(action);
                        break;

                    case UPDATED:
                        buildUpdatedStory(action);
                        break;

                    default:
                        throw new RuntimeException("encountered unknown " +
                                Verb.class.getName() + ": \"" + action.getVerb() + '"');
                }
            }
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

            mFeed.mLinks = mResponse.getLinks();
            final ArrayList<ActionGroup> actionGroups = mResponse.getData();

            // noinspection ConstantConditions
            for (final ActionGroup actionGroup : actionGroups) {
                searchActionGroups(actionGroup);
            }
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

        public Builder setFeed(final FeedV3 feed) {
            mFeed = feed;
            return this;
        }
    }

}
