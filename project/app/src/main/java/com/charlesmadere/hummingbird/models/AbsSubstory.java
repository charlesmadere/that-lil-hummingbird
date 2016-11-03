package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public abstract class AbsSubstory implements Parcelable {

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("story_id")
    private String mStoryId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AbsSubstory && mId.equalsIgnoreCase(((AbsSubstory) o).getId());
    }

    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public String getId() {
        return mId;
    }

    public String getStoryId() {
        return mStoryId;
    }

    public abstract Type getType();

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @WorkerThread
    public void hydrate(final Feed feed) {
        // method intentionally blank, children can override
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
        mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mId = source.readString();
        mStoryId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mStoryId);
    }


    public enum Type implements Parcelable {
        @SerializedName("followed")
        FOLLOWED,

        @SerializedName("reply")
        REPLY,

        @SerializedName("watched_episode")
        WATCHED_EPISODE,

        @SerializedName("watchlist_status_update")
        WATCHLIST_STATUS_UPDATE;


        public static Type from(final String type) {
            switch (type) {
                case "followed":
                    return FOLLOWED;

                case "reply":
                    return REPLY;

                case "watched_episode":
                    return WATCHED_EPISODE;

                case "watchlist_status_update":
                    return WATCHLIST_STATUS_UPDATE;

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

    public static final Comparator<AbsSubstory> CHRONOLOGICAL_ORDER = new Comparator<AbsSubstory>() {
        @Override
        public int compare(final AbsSubstory lhs, final AbsSubstory rhs) {
            return SimpleDate.CHRONOLOGICAL_ORDER.compare(lhs.getCreatedAt(),
                    rhs.getCreatedAt());
        }
    };

    public static final Comparator<AbsSubstory> REVERSE_CHRONOLOGICAL_ORDER = new Comparator<AbsSubstory>() {
        @Override
        public int compare(final AbsSubstory lhs, final AbsSubstory rhs) {
            return SimpleDate.REVERSE_CHRONOLOGICAL_ORDER.compare(lhs.getCreatedAt(),
                    rhs.getCreatedAt());
        }
    };

    public static final JsonDeserializer<AbsSubstory> JSON_DESERIALIZER = new JsonDeserializer<AbsSubstory>() {
        @Override
        public AbsSubstory deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final Type type = Type.from(jsonObject.get("type").getAsString());

            final AbsSubstory substory;

            switch (type) {
                case FOLLOWED:
                    substory = context.deserialize(json, FollowedSubstory.class);
                    break;

                case REPLY:
                    substory = context.deserialize(json, ReplySubstory.class);
                    break;

                case WATCHED_EPISODE:
                    substory = context.deserialize(json, WatchedEpisodeSubstory.class);
                    break;

                case WATCHLIST_STATUS_UPDATE:
                    substory = context.deserialize(json, WatchlistStatusUpdateSubstory.class);
                    break;

                default:
                    throw new RuntimeException("encountered unknown " + Type.class.getName() +
                            ": \"" + type + '"');
            }

            return substory;
        }
    };

}
