package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MangaDigest implements Parcelable {

    @Nullable
    @SerializedName("castings")
    private ArrayList<Casting> mCastings;

    @Nullable
    @SerializedName("characters")
    private ArrayList<Character> mCharacters;

    @SerializedName("full_manga")
    private Manga mManga;


    @Nullable
    public ArrayList<Casting> getCastings() {
        return mCastings;
    }

    @Nullable
    public ArrayList<Character> getCharacters() {
        return mCharacters;
    }

    public String getId() {
        return mManga.getId();
    }

    public Manga getManga() {
        return mManga;
    }

    public String getTitle() {
        return mManga.getTitle();
    }

    public boolean hasCastings() {
        return mCastings != null && !mCastings.isEmpty();
    }

    public boolean hasCharacters() {
        return mCharacters != null && !mCharacters.isEmpty();
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
        dest.writeTypedList(mCastings);
        dest.writeTypedList(mCharacters);
        dest.writeParcelable(mManga, flags);
    }

    public static final Creator<MangaDigest> CREATOR = new Creator<MangaDigest>() {
        @Override
        public MangaDigest createFromParcel(final Parcel source) {
            final MangaDigest md = new MangaDigest();
            md.mCastings = source.createTypedArrayList(Casting.CREATOR);
            md.mCharacters = source.createTypedArrayList(Character.CREATOR);
            md.mManga = source.readParcelable(Manga.class.getClassLoader());
            return md;
        }

        @Override
        public MangaDigest[] newArray(final int size) {
            return new MangaDigest[size];
        }
    };


    public static class Casting implements Parcelable {
        @SerializedName("character_id")
        private String mCharacterId;

        @SerializedName("id")
        private String mId;

        @Nullable
        @SerializedName("language")
        private String mLanguage;

        @Nullable
        @SerializedName("person_id")
        private String mPersonId;

        @Nullable
        @SerializedName("role")
        private String mRole;


        public String getCharacterId() {
            return mCharacterId;
        }

        public String getId() {
            return mId;
        }

        @Nullable
        public String getLanguage() {
            return mLanguage;
        }

        @Nullable
        public String getPersonId() {
            return mPersonId;
        }

        @Nullable
        public String getRole() {
            return mRole;
        }

        public boolean hasLanguage() {
            return !TextUtils.isEmpty(mLanguage);
        }

        public boolean hasPersonId() {
            return !TextUtils.isEmpty(mPersonId);
        }

        public boolean hasRole() {
            return !TextUtils.isEmpty(mRole);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mCharacterId);
            dest.writeString(mId);
            dest.writeString(mLanguage);
            dest.writeString(mPersonId);
            dest.writeString(mRole);
        }

        public static final Creator<Casting> CREATOR = new Creator<Casting>() {
            @Override
            public Casting createFromParcel(final Parcel source) {
                final Casting c = new Casting();
                c.mCharacterId = source.readString();
                c.mId = source.readString();
                c.mLanguage = source.readString();
                c.mPersonId = source.readString();
                c.mRole = source.readString();
                return c;
            }

            @Override
            public Casting[] newArray(final int size) {
                return new Casting[size];
            }
        };
    }


    public static class Character implements Parcelable {
        @SerializedName("id")
        private String mId;

        @SerializedName("image")
        private String mImage;

        @SerializedName("name")
        private String mName;

        public String getId() {
            return mId;
        }

        public String getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
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
            dest.writeString(mId);
            dest.writeString(mImage);
            dest.writeString(mName);
        }

        public static final Creator<Character> CREATOR = new Creator<Character>() {
            @Override
            public Character createFromParcel(final Parcel source) {
                final Character c = new Character();
                c.mId = source.readString();
                c.mImage = source.readString();
                c.mName = source.readString();
                return c;
            }

            @Override
            public Character[] newArray(final int size) {
                return new Character[size];
            }
        };
    }

}
