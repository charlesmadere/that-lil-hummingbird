package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.google.gson.annotations.SerializedName;

public class Group implements Hydratable, Parcelable {

    @SerializedName("member_count")
    private int mMemberCount;

    @Nullable
    @SerializedName("about")
    private String mAbout;

    @Nullable
    @SerializedName("about_formatted")
    private String mAboutFormatted;

    @Nullable
    @SerializedName("avatar_url")
    private String mAvatarUrl;

    @Nullable
    @SerializedName("bio")
    private String mBio;

    @Nullable
    @SerializedName("cover_image_url")
    private String mCoverImageUrl;

    @Nullable
    @SerializedName("current_member_id")
    private String mCurrentMemberId;

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    // hydrated fields
    @Nullable
    private CharSequence mCompiledAbout;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Group && mId.equalsIgnoreCase(((Group) o).getId());
    }

    @Nullable
    public CharSequence getAbout() {
        return mCompiledAbout;
    }

    @Nullable
    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    @Nullable
    public String getBio() {
        return mBio;
    }

    @Nullable
    public String getCoverImageUrl() {
        return mCoverImageUrl;
    }

    @Nullable
    public String getCurrentMemberId() {
        return mCurrentMemberId;
    }

    public String getId() {
        return mId;
    }

    public int getMemberCount() {
        return mMemberCount;
    }

    public String getName() {
        return mName;
    }

    public boolean hasAbout() {
        return !TextUtils.isEmpty(mCompiledAbout);
    }

    public boolean hasAvatar() {
        return MiscUtils.isValidArtwork(mAvatarUrl);
    }

    public boolean hasBio() {
        return !TextUtils.isEmpty(mBio);
    }

    public boolean hasCoverImage() {
        return MiscUtils.isValidArtwork(mCoverImageUrl);
    }

    public boolean hasCurrentMemberId() {
        return !TextUtils.isEmpty(mCurrentMemberId);
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    @WorkerThread
    public void hydrate() {
        if (!TextUtils.isEmpty(mAboutFormatted)) {
            mCompiledAbout = JsoupUtils.parse(mAboutFormatted);
        } else if (!TextUtils.isEmpty(mAbout)) {
            mCompiledAbout = JsoupUtils.parse(mAbout);
        }
    }

    public void setCurrentMemberId(@Nullable final String currentMemberId) {
        mCurrentMemberId = currentMemberId;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mMemberCount);
        dest.writeString(mAbout);
        dest.writeString(mAboutFormatted);
        dest.writeString(mAvatarUrl);
        dest.writeString(mBio);
        dest.writeString(mCoverImageUrl);
        dest.writeString(mCurrentMemberId);
        dest.writeString(mId);
        dest.writeString(mName);
        TextUtils.writeToParcel(mCompiledAbout, dest, flags);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(final Parcel source) {
            final Group g = new Group();
            g.mMemberCount = source.readInt();
            g.mAbout = source.readString();
            g.mAboutFormatted = source.readString();
            g.mAvatarUrl = source.readString();
            g.mBio = source.readString();
            g.mCoverImageUrl = source.readString();
            g.mCurrentMemberId = source.readString();
            g.mId = source.readString();
            g.mName = source.readString();
            g.mCompiledAbout = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            return g;
        }

        @Override
        public Group[] newArray(final int size) {
            return new Group[size];
        }
    };

}
