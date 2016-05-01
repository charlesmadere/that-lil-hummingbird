package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

public class Substory implements Parcelable {

    @SerializedName("id")
    private int mId;

    @Nullable
    @SerializedName("episode_number")
    private Integer mEpisodeNumber;

    @Nullable
    @SerializedName("new_status")
    private NewStatus mNewStatus;

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @Nullable
    @SerializedName("comment")
    private String mComment;

    @Nullable
    @SerializedName("story_id")
    private String mStoryId;

    @Nullable
    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("substory_type")
    private Type mType;

    @Nullable
    @SerializedName("followed_user")
    private User mFollowedUser;


    @Nullable
    public String getComment() {
        return mComment;
    }

    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    @Nullable
    public Integer getEpisodeNumber() {
        return mEpisodeNumber;
    }

    @Nullable
    public User getFollowedUser() {
        return mFollowedUser;
    }

    public int getId() {
        return mId;
    }

    @Nullable
    public NewStatus getNewStatus() {
        return mNewStatus;
    }

    @Nullable
    public String getStoryId() {
        return mStoryId;
    }

    public Type getType() {
        return mType;
    }

    @Nullable
    public String getUserId() {
        return mUserId;
    }

    @Override
    public String toString() {
        return mType.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mId);
        ParcelableUtils.writeInteger(mEpisodeNumber, dest);
        dest.writeParcelable(mNewStatus, flags);
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mComment);
        dest.writeString(mStoryId);
        dest.writeParcelable(mType, flags);
        dest.writeParcelable(mFollowedUser, flags);
    }

    public static final Creator<Substory> CREATOR = new Creator<Substory>() {
        @Override
        public Substory createFromParcel(final Parcel source) {
            final Substory s = new Substory();
            s.mId = source.readInt();
            s.mEpisodeNumber = ParcelableUtils.readInteger(source);
            s.mNewStatus = source.readParcelable(NewStatus.class.getClassLoader());
            s.mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            s.mComment = source.readString();
            s.mStoryId = source.readString();
            s.mType = source.readParcelable(Type.class.getClassLoader());
            s.mFollowedUser = source.readParcelable(User.class.getClassLoader());
            return s;
        }

        @Override
        public Substory[] newArray(final int size) {
            return new Substory[size];
        }
    };


    public enum NewStatus implements Parcelable {
        @SerializedName("completed")
        COMPLETED,

        @SerializedName("currently_watching")
        CURRENTLY_WATCHING,

        @SerializedName("dropped")
        DROPPED,

        @SerializedName("on_hold")
        ON_HOLD,

        @SerializedName("plan_to_watch")
        PLAN_TO_WATCH;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<NewStatus> CREATOR = new Creator<NewStatus>() {
            @Override
            public NewStatus createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public NewStatus[] newArray(final int size) {
                return new NewStatus[size];
            }
        };
    }


    public enum Type implements Parcelable {
        @SerializedName("comment")
        COMMENT,

        @SerializedName("followed")
        FOLLOWED,

        @SerializedName("reply")
        REPLY,

        @SerializedName("watched_episode")
        WATCHED_EPISODE,

        @SerializedName("watchlist_status_update")
        WATCHLIST_STATUS_UPDATE;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Type> CREATOR = new Creator<Type>() {
            @Override
            public Type createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public Type[] newArray(final int size) {
                return new Type[size];
            }
        };
    }

}
