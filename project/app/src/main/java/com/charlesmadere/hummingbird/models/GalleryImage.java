package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GalleryImage implements Parcelable {

    @SerializedName("id")
    private String mId;

    @SerializedName("original")
    private String mOriginal;

    @SerializedName("thumbnail")
    private String mThumbnail;


    public String getId() {
        return mId;
    }

    public String getOriginal() {
        return mOriginal;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mId);
        dest.writeString(mOriginal);
        dest.writeString(mThumbnail);
    }

    public static final Creator<GalleryImage> CREATOR = new Creator<GalleryImage>() {
        @Override
        public GalleryImage createFromParcel(final Parcel source) {
            final GalleryImage gi = new GalleryImage();
            gi.mId = source.readString();
            gi.mOriginal = source.readString();
            gi.mThumbnail = source.readString();
            return gi;
        }

        @Override
        public GalleryImage[] newArray(final int size) {
            return new GalleryImage[size];
        }
    };

}
