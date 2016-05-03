package com.charlesmadere.hummingbird.misc;

import android.content.Context;
import android.graphics.Typeface;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.models.TypefaceEntry;

import java.io.File;
import java.util.HashMap;

public final class TypefaceStore {

    private static final HashMap<TypefaceEntry, Typeface> STORE = new HashMap<>();
    private static final String BASE_PATH = "fonts" + File.separatorChar;
    private static final String TAG = "TypefaceUtils";


    public static synchronized Typeface get(final int typefaceEntryOrdinal) {
        if (typefaceEntryOrdinal < 0 || typefaceEntryOrdinal >= TypefaceEntry.values().length) {
            throw new IllegalArgumentException("typefaceEntryOrdinal parameter is out of bounds: "
                    + typefaceEntryOrdinal);
        }

        return get(TypefaceEntry.values()[typefaceEntryOrdinal]);
    }

    public static synchronized Typeface get(final TypefaceEntry typefaceEntry) {
        if (typefaceEntry == null) {
            throw new IllegalArgumentException("typefaceEntry parameter can't be null");
        }

        Typeface typeface = STORE.get(typefaceEntry);

        if (typeface == null) {
            final String path = BASE_PATH + typefaceEntry.getPath();
            Timber.d(TAG, "Loading Typeface \"" + path + '"');

            final Context context = ThatLilHummingbird.get();
            typeface = Typeface.createFromAsset(context.getAssets(), path);
            STORE.put(typefaceEntry, typeface);
        }

        return typeface;
    }

}
