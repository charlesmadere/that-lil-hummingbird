package com.charlesmadere.hummingbird.models;

public enum TypefaceEntry {

    OPEN_SANS_BOLD("OpenSans-Bold.ttf"),
    OPEN_SANS_EXTRA_BOLD("OpenSans-ExtraBold.ttf"),
    OPEN_SANS_ITALIC("OpenSans-Italic.ttf"),
    OPEN_SANS_LIGHT("OpenSans-Light.ttf"),
    OPEN_SANS_LIGHT_ITALIC("OpenSans-LightItalic.ttf"),
    OPEN_SANS_REGULAR("OpenSans-Regular.ttf"),
    OPEN_SANS_SEMIBOLD("OpenSans-Semibold.ttf"),
    TEKO_BOLD("Teko-Bold.ttf"),
    TEKO_REGULAR("Teko-Regular.ttf"),
    TEKO_SEMIBOLD("Teko-SemiBold.ttf");

    private final String mPath;


    TypefaceEntry(final String path) {
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

}
