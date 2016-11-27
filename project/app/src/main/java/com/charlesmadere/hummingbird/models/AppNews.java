package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppNews implements Parcelable {

    @Nullable
    @SerializedName("links")
    private ArrayList<Link> mLinks;

    @SerializedName("important")
    private boolean mImportant;

    @SerializedName("epoch")
    private long mEpoch;

    @SerializedName("body")
    private String mBody;

    @SerializedName("head")
    private String mHead;

    @SerializedName("id")
    private String mId;

    // hydrated fields
    private SimpleDate mDate;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AppNews && mId.equalsIgnoreCase(((AppNews) o).getId());
    }

    public String getBody() {
        return mBody;
    }

    public SimpleDate getDate() {
        if (mDate == null) {
            mDate = new SimpleDate(getEpoch());
        }

        return mDate;
    }

    public long getEpoch() {
        return TimeUnit.SECONDS.toMillis(mEpoch);
    }

    public String getHead() {
        return mHead;
    }

    public String getId() {
        return mId;
    }

    @Nullable
    public ArrayList<Link> getLinks() {
        return mLinks;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasLinks() {
        return mLinks != null && !mLinks.isEmpty();
    }

    public boolean isImportant() {
        return mImportant;
    }

    @Override
    public String toString() {
        return getHead();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mLinks);
        dest.writeInt(mImportant ? 1 : 0);
        dest.writeLong(mEpoch);
        dest.writeString(mBody);
        dest.writeString(mHead);
        dest.writeString(mId);
        dest.writeParcelable(mDate, flags);
    }

    public static final Creator<AppNews> CREATOR = new Creator<AppNews>() {
        @Override
        public AppNews createFromParcel(final Parcel source) {
            final AppNews an = new AppNews();
            an.mLinks = source.createTypedArrayList(Link.CREATOR);
            an.mImportant = source.readInt() != 0;
            an.mEpoch = source.readLong();
            an.mBody = source.readString();
            an.mHead = source.readString();
            an.mId = source.readString();
            an.mDate = source.readParcelable(SimpleDate.class.getClassLoader());
            return an;
        }

        @Override
        public AppNews[] newArray(final int size) {
            return new AppNews[size];
        }
    };


    public static class Link implements Parcelable {
        @SerializedName("title")
        private String mTitle;

        @SerializedName("url")
        private String mUrl;

        public String getTitle() {
            return mTitle;
        }

        public String getUrl() {
            return mUrl;
        }

        @Override
        public String toString() {
            return getTitle();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mTitle);
            dest.writeString(mUrl);
        }

        public static final Creator<Link> CREATOR = new Creator<Link>() {
            @Override
            public Link createFromParcel(final Parcel source) {
                final Link l = new Link();
                l.mTitle = source.readString();
                l.mUrl = source.readString();
                return l;
            }

            @Override
            public Link[] newArray(final int size) {
                return new Link[size];
            }
        };
    }

}
