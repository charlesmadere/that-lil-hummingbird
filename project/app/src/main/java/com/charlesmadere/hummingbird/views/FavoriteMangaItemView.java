package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

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

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    public void setContent(final UserDigest.Favorite.MangaItem content) {
        mMangaItem = content;
        setImageURI(mMangaItem.getManga().getPosterImageThumb());
    }

}
