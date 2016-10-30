package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class FavoriteAnimeItemView extends SimpleDraweeView implements
        AdapterView<UserDigest.Favorite.AnimeItem>, View.OnClickListener {

    private UserDigest.Favorite.AnimeItem mAnimeItem;


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
    public void onClick(final View view) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mAnimeItem.getAnime()));
    }

    @Override
    public void setContent(@Nullable final UserDigest.Favorite.AnimeItem content) {
        mAnimeItem = content;

        if (mAnimeItem == null) {
            setImageURI((String) null);
            setClickable(false);
        } else {
            setImageURI(mAnimeItem.getAnime().getPosterImage());
            setOnClickListener(this);
        }
    }

}
