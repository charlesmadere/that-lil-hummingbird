package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public abstract class AbsStory implements Parcelable {

    @Nullable
    @SerializedName("substory_ids")
    private ArrayList<String> mSubstoryIds;

    @SerializedName("substory_count")
    private int mSubstoryCount;

    @SerializedName("total_votes")
    private int mTotalVotes;

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private ArrayList<AbsSubstory> mSubstories;
    private User mUser;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AbsStory && mId.equalsIgnoreCase(((AbsStory) o).getId());
    }

    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public String getId() {
        return mId;
    }

    public ArrayList<AbsSubstory> getSubstories() {
        return mSubstories;
    }

    public int getSubstoryCount() {
        return mSubstoryCount;
    }

    @Nullable
    public ArrayList<String> getSubstoryIds() {
        return mSubstoryIds;
    }

    public int getTotalVotes() {
        return mTotalVotes;
    }

    public abstract Type getType();

    public User getUser() {
        return mUser;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasSubstoryIds() {
        return mSubstoryIds != null && !mSubstoryIds.isEmpty();
    }

    public void hydrate(final Feed feed) {
        for (final User user : feed.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                break;
            }
        }

        if (hasSubstoryIds() && feed.hasSubstories()) {
            mSubstories = new ArrayList<>();

            for (final String substoryId : mSubstoryIds) {
                for (final AbsSubstory substory : feed.getSubstories()) {
                    if (substoryId.equalsIgnoreCase(substory.getId()) &&
                            !mSubstories.contains(substory)) {
                        mSubstories.add(substory);
                    }
                }
            }

            if (mSubstories.isEmpty()) {
                mSubstories = null;
            } else {
                mSubstories.trimToSize();
            }
        }
    }

    protected void setTotalVotes(final int totalVotes) {
        mTotalVotes = totalVotes;
    }

    @Override
    public String toString() {
        return getType().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mSubstoryIds = source.createStringArrayList();
        mSubstoryCount = source.readInt();
        mTotalVotes = source.readInt();
        mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mId = source.readString();
        mUserId = source.readString();
        mSubstories = ParcelableUtils.readAbsSubstoryList(source);
        mUser = source.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeStringList(mSubstoryIds);
        dest.writeInt(mSubstoryCount);
        dest.writeInt(mTotalVotes);
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mUserId);
        ParcelableUtils.writeAbsSubstoryList(mSubstories, dest, flags);
        dest.writeParcelable(mUser, flags);
    }


    public enum Type implements Parcelable {
        @SerializedName("comment")
        COMMENT,

        @SerializedName("followed")
        FOLLOWED,

        @SerializedName("media_story")
        MEDIA_STORY;


        public static Type from(final String type) {
            switch (type) {
                case "comment":
                    return COMMENT;

                case "followed":
                    return FOLLOWED;

                case "media_story":
                    return MEDIA_STORY;

                default:
                    throw new IllegalArgumentException("encountered unknown " +
                            Type.class.getName() + ": \"" + type + '"');
            }
        }

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
                return values()[source.readInt()];
            }

            @Override
            public Type[] newArray(final int size) {
                return new Type[size];
            }
        };
    }


    public static final JsonDeserializer<AbsStory> JSON_DESERIALIZER = new JsonDeserializer<AbsStory>() {
        @Override
        public AbsStory deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final Type type = Type.from(jsonObject.get("type").getAsString());

            final AbsStory story;

            switch (type) {
                case COMMENT:
                    story = context.deserialize(json, CommentStory.class);
                    break;

                case FOLLOWED:
                    story = context.deserialize(json, FollowedStory.class);
                    break;

                case MEDIA_STORY:
                    story = context.deserialize(json, MediaStory.class);
                    break;

                default:
                    throw new RuntimeException("encountered unknown " + Type.class.getName() +
                            ": \"" + type + '"');
            }

            return story;
        }
    };

}
