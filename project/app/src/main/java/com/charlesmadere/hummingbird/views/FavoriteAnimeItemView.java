package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class FavoriteAnimeItemView extends SimpleDraweeView implements
        AdapterView<UserDigest.Favorite.AnimeItem> {

    public FavoriteAnimeItemView(final Context context, final GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FavoriteAnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteAnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FavoriteAnimeItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setContent(final UserDigest.Favorite.AnimeItem content) {
        setImageURI(content.getAnime().getPosterImage());
    }

}
