package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Relationship implements Parcelable {

    @Nullable
    private final ArrayList<DataObject.Stub> mArray;

    @Nullable
    private final DataObject.Stub mObject;

    @Nullable
    private final Links mLinks;


    private Relationship(final ArrayList<DataObject.Stub> array, final DataObject.Stub object,
            final Links links) {
        mArray = array;
        mObject = object;
        mLinks = links;
    }

    private Relationship(final Parcel source) {
        mArray = source.createTypedArrayList(DataObject.Stub.CREATOR);
        mObject = source.readParcelable(DataObject.Stub.class.getClassLoader());
        mLinks = source.readParcelable(Links.class.getClassLoader());
    }

    @Nullable
    public ArrayList<DataObject.Stub> getArray() {
        return mArray;
    }

    @Nullable
    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public DataObject.Stub getObject() {
        return mObject;
    }

    public boolean hasArray() {
        return mArray != null && !mArray.isEmpty();
    }

    public boolean hasLinks() {
        return mLinks != null;
    }

    public boolean hasObject() {
        return mObject != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mArray);
        dest.writeParcelable(mObject, flags);
        dest.writeParcelable(mLinks, flags);
    }

    public static final Creator<Relationship> CREATOR = new Creator<Relationship>() {
        @Override
        public Relationship createFromParcel(final Parcel source) {
            return new Relationship(source);
        }

        @Override
        public Relationship[] newArray(final int size) {
            return new Relationship[size];
        }
    };

    public static final JsonDeserializer<Relationship> JSON_DESERIALIZER = new JsonDeserializer<Relationship>() {
        @Override
        public Relationship deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            final JsonObject jsonObject = json.getAsJsonObject();

            ArrayList<DataObject.Stub> array = null;
            DataObject.Stub object = null;

            if (jsonObject.has("data")) {
                final JsonElement dataJson = jsonObject.get("data");

                if (!dataJson.isJsonNull()) {
                    if (dataJson.isJsonArray()) {
                        final JsonArray arrayJson = dataJson.getAsJsonArray();

                        if (arrayJson.size() >= 1) {
                            array = new ArrayList<>(arrayJson.size());

                            for (final JsonElement arrayElement : arrayJson) {
                                array.add((DataObject.Stub) context.deserialize(arrayElement,
                                        DataObject.Stub.class));
                            }
                        }
                    } else if (dataJson.isJsonObject()) {
                        object = context.deserialize(dataJson, DataObject.Stub.class);
                    }
                }
            }

            Links links = null;

            if (jsonObject.has("links")) {
                final JsonElement linksJson = jsonObject.get("links");

                if (!linksJson.isJsonNull()) {
                    links = context.deserialize(linksJson, Links.class);
                }
            }

            if (array == null && object == null && links == null) {
                return null;
            } else {
                return new Relationship(array, object, links);
            }
        }
    };

}
