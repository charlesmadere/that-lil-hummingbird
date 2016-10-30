package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.activities.MangaActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class FavoriteMangaItemView extends SimpleDraweeView implements
        AdapterView<UserDigest.Favorite.MangaItem>, View.OnClickListener {

    private UserDigest.Favorite.MangaItem mMangaItem;


    public FavoriteMangaItemView(final Context context, final GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FavoriteMangaItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteMangaItemView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FavoriteMangaItemView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(final View view) {
        final Context context = getContext();
        context.startActivity(MangaActivity.getLaunchIntent(context, mMangaItem.getManga()));
    }

    @Override
    public void setContent(@Nullable final UserDigest.Favorite.MangaItem content) {
        mMangaItem = content;

        if (mMangaItem == null) {
            setImageURI((String) null);
            setClickable(false);
        } else {
            setImageURI(mMangaItem.getManga().getPosterImageThumb());
            setOnClickListener(this);
        }
    }

}
