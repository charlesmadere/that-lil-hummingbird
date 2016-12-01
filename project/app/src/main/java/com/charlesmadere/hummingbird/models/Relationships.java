package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Relationships implements Parcelable {

    @Nullable
    @SerializedName("activities")
    private Relationship mActivities;

    @Nullable
    @SerializedName("actor")
    private Relationship mActor;

    @Nullable
    @SerializedName("blocks")
    private Relationship mBlocks;

    @Nullable
    @SerializedName("castings")
    private Relationship mCastings;

    @Nullable
    @SerializedName("character")
    private Relationship mCharacter;

    @Nullable
    @SerializedName("comment")
    private Relationship mComment;

    @Nullable
    @SerializedName("episodes")
    private Relationship mEpisodes;

    @Nullable
    @SerializedName("favorites")
    private Relationship mFavorites;

    @Nullable
    @SerializedName("followed")
    private Relationship mFollowed;

    @Nullable
    @SerializedName("follower")
    private Relationship mFollower;

    @Nullable
    @SerializedName("followers")
    private Relationship mFollowers;

    @Nullable
    @SerializedName("following")
    private Relationship mFollowing;

    @Nullable
    @SerializedName("franchise")
    private Relationship mFranchise;

    @Nullable
    @SerializedName("genres")
    private Relationship mGenres;

    @Nullable
    @SerializedName("installments")
    private Relationship mInstallments;

    @Nullable
    @SerializedName("item")
    private Relationship mItem;

    @Nullable
    @SerializedName("libraryEntries")
    private Relationship mLibraryEntries;

    @Nullable
    @SerializedName("libraryEntry")
    private Relationship mLibraryEntry;

    @Nullable
    @SerializedName("likes")
    private Relationship mLikes;

    @Nullable
    @SerializedName("linkedProfiles")
    private Relationship mLinkedProfiles;

    @Nullable
    @SerializedName("mappings")
    private Relationship mMappings;

    @Nullable
    @SerializedName("media")
    private Relationship mMedia;

    @Nullable
    @SerializedName("mediaFollows")
    private Relationship mMediaFollows;

    @Nullable
    @SerializedName("parent")
    private Relationship mParent;

    @Nullable
    @SerializedName("person")
    private Relationship mPerson;

    @Nullable
    @SerializedName("post")
    private Relationship mPost;

    @Nullable
    @SerializedName("primaryMedia")
    private Relationship mPrimaryMedia;

    @Nullable
    @SerializedName("replies")
    private Relationship mReplies;

    @Nullable
    @SerializedName("review")
    private Relationship mReview;

    @Nullable
    @SerializedName("reviews")
    private Relationship mReviews;

    @Nullable
    @SerializedName("spoiledUnit")
    private Relationship mSpoiledUnit;

    @Nullable
    @SerializedName("streamer")
    private Relationship mStreamer;

    @Nullable
    @SerializedName("streamingLinks")
    private Relationship mStreamingLinks;

    @Nullable
    @SerializedName("streamingRelationship")
    private Relationship mStreamingRelationship;

    @Nullable
    @SerializedName("subject")
    private Relationship mSubject;

    @Nullable
    @SerializedName("targetUser")
    private Relationship mTargetUser;

    @Nullable
    @SerializedName("unit")
    private Relationship mUnit;

    @Nullable
    @SerializedName("user")
    private Relationship mUser;

    @Nullable
    @SerializedName("userRoles")
    private Relationship mUserRoles;

    @Nullable
    @SerializedName("waifu")
    private Relationship mWaifu;


    @Nullable
    public Relationship getActivities() {
        return mActivities;
    }

    @Nullable
    public Relationship getActor() {
        return mActor;
    }

    @Nullable
    public Relationship getBlocks() {
        return mBlocks;
    }

    @Nullable
    public Relationship getCastings() {
        return mCastings;
    }

    @Nullable
    public Relationship getCharacter() {
        return mCharacter;
    }

    @Nullable
    public Relationship getComment() {
        return mComment;
    }

    @Nullable
    public Relationship getEpisodes() {
        return mEpisodes;
    }

    @Nullable
    public Relationship getFavorites() {
        return mFavorites;
    }

    @Nullable
    public Relationship getFollowed() {
        return mFollowed;
    }

    @Nullable
    public Relationship getFollower() {
        return mFollower;
    }

    @Nullable
    public Relationship getFollowers() {
        return mFollowers;
    }

    @Nullable
    public Relationship getFollowing() {
        return mFollowing;
    }

    @Nullable
    public Relationship getFranchise() {
        return mFranchise;
    }

    @Nullable
    public Relationship getGenres() {
        return mGenres;
    }

    @Nullable
    public Relationship getInstallments() {
        return mInstallments;
    }

    @Nullable
    public Relationship getItem() {
        return mItem;
    }

    @Nullable
    public Relationship getLibraryEntries() {
        return mLibraryEntries;
    }

    @Nullable
    public Relationship getLibraryEntry() {
        return mLibraryEntry;
    }

    @Nullable
    public Relationship getLikes() {
        return mLikes;
    }

    @Nullable
    public Relationship getLinkedProfiles() {
        return mLinkedProfiles;
    }

    @Nullable
    public Relationship getMappings() {
        return mMappings;
    }

    @Nullable
    public Relationship getMedia() {
        return mMedia;
    }

    @Nullable
    public Relationship getMediaFollows() {
        return mMediaFollows;
    }

    @Nullable
    public Relationship getParent() {
        return mParent;
    }

    @Nullable
    public Relationship getPerson() {
        return mPerson;
    }

    @Nullable
    public Relationship getPost() {
        return mPost;
    }

    @Nullable
    public Relationship getPrimaryMedia() {
        return mPrimaryMedia;
    }

    @Nullable
    public Relationship getReplies() {
        return mReplies;
    }

    @Nullable
    public Relationship getReview() {
        return mReview;
    }

    @Nullable
    public Relationship getReviews() {
        return mReviews;
    }

    @Nullable
    public Relationship getSpoiledUnit() {
        return mSpoiledUnit;
    }

    @Nullable
    public Relationship getStreamer() {
        return mStreamer;
    }

    @Nullable
    public Relationship getStreamingLinks() {
        return mStreamingLinks;
    }

    @Nullable
    public Relationship getStreamingRelationship() {
        return mStreamingRelationship;
    }

    @Nullable
    public Relationship getSubject() {
        return mSubject;
    }

    @Nullable
    public Relationship getTargetUser() {
        return mTargetUser;
    }

    @Nullable
    public Relationship getUnit() {
        return mUnit;
    }

    @Nullable
    public Relationship getUser() {
        return mUser;
    }

    @Nullable
    public Relationship getUserRoles() {
        return mUserRoles;
    }

    @Nullable
    public Relationship getWaifu() {
        return mWaifu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mActivities, flags);
        dest.writeParcelable(mActor, flags);
        dest.writeParcelable(mBlocks, flags);
        dest.writeParcelable(mCastings, flags);
        dest.writeParcelable(mCharacter, flags);
        dest.writeParcelable(mComment, flags);
        dest.writeParcelable(mEpisodes, flags);
        dest.writeParcelable(mFavorites, flags);
        dest.writeParcelable(mFollowed, flags);
        dest.writeParcelable(mFollower, flags);
        dest.writeParcelable(mFollowers, flags);
        dest.writeParcelable(mFollowing, flags);
        dest.writeParcelable(mFranchise, flags);
        dest.writeParcelable(mGenres, flags);
        dest.writeParcelable(mInstallments, flags);
        dest.writeParcelable(mItem, flags);
        dest.writeParcelable(mLibraryEntries, flags);
        dest.writeParcelable(mLibraryEntry, flags);
        dest.writeParcelable(mLikes, flags);
        dest.writeParcelable(mLinkedProfiles, flags);
        dest.writeParcelable(mMappings, flags);
        dest.writeParcelable(mMedia, flags);
        dest.writeParcelable(mMediaFollows, flags);
        dest.writeParcelable(mParent, flags);
        dest.writeParcelable(mPerson, flags);
        dest.writeParcelable(mPost, flags);
        dest.writeParcelable(mPrimaryMedia, flags);
        dest.writeParcelable(mReplies, flags);
        dest.writeParcelable(mReview, flags);
        dest.writeParcelable(mReviews, flags);
        dest.writeParcelable(mSpoiledUnit, flags);
        dest.writeParcelable(mStreamer, flags);
        dest.writeParcelable(mStreamingLinks, flags);
        dest.writeParcelable(mStreamingRelationship, flags);
        dest.writeParcelable(mSubject, flags);
        dest.writeParcelable(mTargetUser, flags);
        dest.writeParcelable(mUnit, flags);
        dest.writeParcelable(mUser, flags);
        dest.writeParcelable(mUserRoles, flags);
        dest.writeParcelable(mWaifu, flags);
    }

    public static final Creator<Relationships> CREATOR = new Creator<Relationships>() {
        @Override
        public Relationships createFromParcel(final Parcel source) {
            final Relationships r = new Relationships();
            r.mActivities = source.readParcelable(Relationship.class.getClassLoader());
            r.mActor = source.readParcelable(Relationship.class.getClassLoader());
            r.mBlocks = source.readParcelable(Relationship.class.getClassLoader());
            r.mCastings = source.readParcelable(Relationship.class.getClassLoader());
            r.mCharacter = source.readParcelable(Relationship.class.getClassLoader());
            r.mComment = source.readParcelable(Relationship.class.getClassLoader());
            r.mEpisodes = source.readParcelable(Relationship.class.getClassLoader());
            r.mFavorites = source.readParcelable(Relationship.class.getClassLoader());
            r.mFollowed = source.readParcelable(Relationship.class.getClassLoader());
            r.mFollower = source.readParcelable(Relationship.class.getClassLoader());
            r.mFollowers = source.readParcelable(Relationship.class.getClassLoader());
            r.mFollowing = source.readParcelable(Relationship.class.getClassLoader());
            r.mFranchise = source.readParcelable(Relationship.class.getClassLoader());
            r.mGenres = source.readParcelable(Relationship.class.getClassLoader());
            r.mInstallments = source.readParcelable(Relationship.class.getClassLoader());
            r.mItem = source.readParcelable(Relationship.class.getClassLoader());
            r.mLibraryEntries = source.readParcelable(Relationship.class.getClassLoader());
            r.mLibraryEntry = source.readParcelable(Relationship.class.getClassLoader());
            r.mLikes = source.readParcelable(Relationship.class.getClassLoader());
            r.mLinkedProfiles = source.readParcelable(Relationship.class.getClassLoader());
            r.mMappings = source.readParcelable(Relationship.class.getClassLoader());
            r.mMedia = source.readParcelable(Relationship.class.getClassLoader());
            r.mMediaFollows = source.readParcelable(Relationship.class.getClassLoader());
            r.mParent = source.readParcelable(Relationship.class.getClassLoader());
            r.mPerson = source.readParcelable(Relationship.class.getClassLoader());
            r.mPost = source.readParcelable(Relationship.class.getClassLoader());
            r.mPrimaryMedia = source.readParcelable(Relationship.class.getClassLoader());
            r.mReplies = source.readParcelable(Relationship.class.getClassLoader());
            r.mReview = source.readParcelable(Relationship.class.getClassLoader());
            r.mReviews = source.readParcelable(Relationship.class.getClassLoader());
            r.mSpoiledUnit = source.readParcelable(Relationship.class.getClassLoader());
            r.mStreamer = source.readParcelable(Relationship.class.getClassLoader());
            r.mStreamingLinks = source.readParcelable(Relationship.class.getClassLoader());
            r.mStreamingRelationship = source.readParcelable(Relationship.class.getClassLoader());
            r.mSubject = source.readParcelable(Relationship.class.getClassLoader());
            r.mTargetUser = source.readParcelable(Relationship.class.getClassLoader());
            r.mUnit = source.readParcelable(Relationship.class.getClassLoader());
            r.mUser = source.readParcelable(Relationship.class.getClassLoader());
            r.mUserRoles = source.readParcelable(Relationship.class.getClassLoader());
            r.mWaifu = source.readParcelable(Relationship.class.getClassLoader());
            return r;
        }

        @Override
        public Relationships[] newArray(final int size) {
            return new Relationships[size];
        }
    };

}
