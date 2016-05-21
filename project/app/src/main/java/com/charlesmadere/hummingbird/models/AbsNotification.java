package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public abstract class AbsNotification implements Parcelable {

    @SerializedName("source")
    private AbsSource mSource;

    @SerializedName("seen")
    private boolean mSeen;

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;


    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public String getId() {
        return mId;
    }

    public AbsSource getSource() {
        return mSource;
    }

    public abstract Type getType();

    public User getUser() {
        return mSource.getUser();
    }

    public void hydrate(final Feed feed) {
        mSource.hydrate(feed);
    }

    public boolean isSeen() {
        return mSeen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mSource = ParcelableUtils.readAbsNotificationAbsSourceFromParcel(source);
        mSeen = source.readInt() != 0;
        mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsNotificationAbsSourceToParcel(mSource, dest, flags);
        dest.writeInt(mSeen ? 1 : 0);
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mId);
    }


    public static abstract class AbsSource implements Parcelable {
        @SerializedName("id")
        private String mId;

        public String getId() {
            return mId;
        }

        public abstract Type getType();

        public abstract User getUser();

        public abstract void hydrate(final Feed feed);

        @Override
        public int describeContents() {
            return 0;
        }

        protected void readFromParcel(final Parcel source) {
            mId = source.readString();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mId);
        }

        public enum Type implements Parcelable {
            @SerializedName("story")
            STORY,

            @SerializedName("substory")
            SUBSTORY;

            public static Type from(final String type) {
                switch (type) {
                    case "story":
                        return STORY;

                    case "substory":
                        return SUBSTORY;

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
                    final int ordinal = source.readInt();
                    return values()[ordinal];
                }

                @Override
                public Type[] newArray(final int size) {
                    return new Type[size];
                }
            };
        }

        public static final JsonDeserializer<AbsSource> JSON_DESERIALIZER = new JsonDeserializer<AbsSource>() {
            @Override
            public AbsSource deserialize(final JsonElement json,
                    final java.lang.reflect.Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                final JsonObject jsonObject = json.getAsJsonObject();
                final Type type = Type.from(jsonObject.get("type").getAsString());

                final AbsSource source;

                switch (type) {
                    case STORY:
                        source = context.deserialize(json, StorySource.class);
                        break;

                    case SUBSTORY:
                        source = context.deserialize(json, SubstorySource.class);
                        break;

                    default:
                        throw new RuntimeException("encountered unknown " + Type.class.getName()
                                + ": \"" + type + '"');
                }

                return source;
            }
        };
    }


    public static class StorySource extends AbsSource implements Parcelable {
        // hydrated fields
        private AbsStory mStory;

        public AbsStory getStory() {
            return mStory;
        }

        @Override
        public Type getType() {
            return Type.STORY;
        }

        @Override
        public User getUser() {
            switch (mStory.getType()) {
                case COMMENT:
                    return ((CommentStory) mStory).getPoster();

                default:
                    return mStory.getUser();
            }
        }

        @Override
        public void hydrate(final Feed feed) {
            for (final AbsStory story : feed.getStories()) {
                if (getId().equalsIgnoreCase(story.getId())) {
                    mStory = story;
                    break;
                }
            }
        }

        @Override
        protected void readFromParcel(final Parcel source) {
            super.readFromParcel(source);
            mStory = ParcelableUtils.readAbsStoryFromParcel(source);
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            ParcelableUtils.writeAbsStoryToParcel(mStory, dest, flags);
        }

        public static final Creator<StorySource> CREATOR = new Creator<StorySource>() {
            @Override
            public StorySource createFromParcel(final Parcel source) {
                final StorySource ss = new StorySource();
                ss.readFromParcel(source);
                return ss;
            }

            @Override
            public StorySource[] newArray(final int size) {
                return new StorySource[size];
            }
        };
    }


    public static class SubstorySource extends AbsSource implements Parcelable {
        // hydrated fields
        private AbsSubstory mSubstory;

        public AbsSubstory getSubstory() {
            return mSubstory;
        }

        @Override
        public Type getType() {
            return Type.SUBSTORY;
        }

        @Override
        public User getUser() {
            switch (mSubstory.getType()) {
                case FOLLOWED:
                    return ((FollowedSubstory) mSubstory).getUser();

                case REPLY:
                    return ((ReplySubstory) mSubstory).getUser();

                default:
                    throw new RuntimeException("encountered illegal " +
                            AbsSubstory.Type.class.getName() + ": \"" + mSubstory.getType() + '"');
            }
        }

        @Override
        public void hydrate(final Feed feed) {
            for (final AbsSubstory substory : feed.getSubstories()) {
                if (getId().equalsIgnoreCase(substory.getId())) {
                    mSubstory = substory;
                    break;
                }
            }
        }

        @Override
        protected void readFromParcel(final Parcel source) {
            super.readFromParcel(source);
            mSubstory = ParcelableUtils.readAbsSubstoryFromParcel(source);
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            ParcelableUtils.writeAbsSubstoryToParcel(mSubstory, dest, flags);
        }

        public static final Creator<SubstorySource> CREATOR = new Creator<SubstorySource>() {
            @Override
            public SubstorySource createFromParcel(final Parcel source) {
                final SubstorySource ss = new SubstorySource();
                ss.readFromParcel(source);
                return ss;
            }

            @Override
            public SubstorySource[] newArray(final int size) {
                return new SubstorySource[size];
            }
        };
    }


    public enum Type implements Parcelable {
        @SerializedName("comment_reply")
        COMMENT_REPLY,

        @SerializedName("profile_comment")
        PROFILE_COMMENT;

        public static Type from(final String type) {
            switch (type) {
                case "comment_reply":
                    return COMMENT_REPLY;

                case "profile_comment":
                    return PROFILE_COMMENT;

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
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public Type[] newArray(final int size) {
                return new Type[size];
            }
        };
    }

    public static final JsonDeserializer<AbsNotification> JSON_DESERIALIZER = new JsonDeserializer<AbsNotification>() {
        @Override
        public AbsNotification deserialize(final JsonElement json,
                final java.lang.reflect.Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final Type type = Type.from(jsonObject.get("notification_type").getAsString());

            final AbsNotification notification;

            switch (type) {
                case COMMENT_REPLY:
                    notification = context.deserialize(json, CommentReplyNotification.class);
                    break;

                case PROFILE_COMMENT:
                    notification = context.deserialize(json, ProfileCommentNotification.class);
                    break;

                default:
                    throw new RuntimeException("encountered unknown " + Type.class.getName()
                            + ": \"" + type + '"');
            }

            return notification;
        }
    };

}