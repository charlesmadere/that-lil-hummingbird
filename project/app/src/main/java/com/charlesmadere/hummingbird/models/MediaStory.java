package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MediaStory extends AbsStory {

    @SerializedName("media")
    private Media mMedia;


    public Media getMedia() {
        return mMedia;
    }

    @Override
    public Type getType() {
        return Type.MEDIA_STORY;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mMedia = source.readParcelable(Media.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mMedia, flags);
    }

    public static final Creator<MediaStory> CREATOR = new Creator<MediaStory>() {
        @Override
        public MediaStory createFromParcel(final Parcel source) {
            final MediaStory ms = new MediaStory();
            ms.readFromParcel(source);
            return ms;
        }

        @Override
        public MediaStory[] newArray(final int size) {
            return new MediaStory[size];
        }
    };


    public static class Media implements Parcelable {
        @SerializedName("id")
        private String mId;

        @SerializedName("type")
        private Type mType;

        public String getId() {
            return mId;
        }

        public Type getType() {
            return mType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mId);
            dest.writeParcelable(mType, flags);
        }

        public static final Creator<Media> CREATOR = new Creator<Media>() {
            @Override
            public Media createFromParcel(final Parcel source) {
                final Media m = new Media();
                m.mId = source.readString();
                m.mType = source.readParcelable(Type.class.getClassLoader());
                return m;
            }

            @Override
            public Media[] newArray(final int size) {
                return new Media[size];
            }
        };


        public enum Type implements Parcelable {
            @SerializedName("anime")
            ANIME;

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

}
