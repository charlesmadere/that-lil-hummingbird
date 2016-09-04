package com.charlesmadere.hummingbird.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class SimpleDate implements Parcelable {

    private static final SimpleDateFormat[] FORMATS = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd", Locale.US)
    };

    private final Date mDate;


    private static String fixTimeZone(final String dateString) {
        if (dateString.endsWith("Z")) {
            return dateString.replace("Z", "+0000");
        } else {
            return dateString;
        }
    }

    public SimpleDate(final long time) {
        this(new Date(time));
    }

    private SimpleDate(final Date date) {
        mDate = date;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof SimpleDate && mDate.equals(((SimpleDate) o).getDate());
    }

    public Date getDate() {
        return mDate;
    }

    public CharSequence getRelativeDateTimeText(final Context context) {
        return DateUtils.getRelativeDateTimeString(context, mDate.getTime(),
                DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
    }

    public CharSequence getRelativeTimeText(final Context context) {
        return DateUtils.getRelativeTimeSpanString(context, mDate.getTime());
    }

    @Override
    public int hashCode() {
        return mDate.hashCode();
    }

    public boolean isInTheFuture() {
        final long now = System.currentTimeMillis();

        if (mDate.getTime() < now || DateUtils.isToday(mDate.getTime())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return mDate.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(mDate.getTime());
    }

    public static final Creator<SimpleDate> CREATOR = new Creator<SimpleDate>() {
        @Override
        public SimpleDate createFromParcel(final Parcel source) {
            return new SimpleDate(source.readLong());
        }

        @Override
        public SimpleDate[] newArray(final int size) {
            return new SimpleDate[size];
        }
    };

    public static final Comparator<SimpleDate> CHRONOLOGICAL_ORDER = new Comparator<SimpleDate>() {
        @Override
        public int compare(final SimpleDate lhs, final SimpleDate rhs) {
            return lhs.getDate().compareTo(rhs.getDate());
        }
    };

    public static final Comparator<SimpleDate> REVERSE_CHRONOLOGICAL_ORDER = new Comparator<SimpleDate>() {
        @Override
        public int compare(final SimpleDate lhs, final SimpleDate rhs) {
            return CHRONOLOGICAL_ORDER.compare(rhs, lhs);
        }
    };

    public static final JsonDeserializer<SimpleDate> JSON_DESERIALIZER = new JsonDeserializer<SimpleDate>() {
        @Override
        public SimpleDate deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final String dateString = fixTimeZone(json.getAsString());

            for (final SimpleDateFormat format : FORMATS) {
                try {
                    return new SimpleDate(format.parse(dateString));
                } catch (final ParseException e) {
                    // this can be safely ignored
                }
            }

            throw new JsonParseException("Couldn't parse date: \"" + dateString + "'");
        }
    };

}
