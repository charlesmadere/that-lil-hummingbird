package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteMangaView extends CardView implements AdapterView<UserDigest> {

    @BindView(R.id.llFavoriteMangaGrid0)
    LinearLayout mMangaGrid0;

    @BindView(R.id.llFavoriteMangaGrid1)
    LinearLayout mMangaGrid1;

    @BindView(R.id.sdvCover0)
    SimpleDraweeView mCover0;

    @BindView(R.id.sdvCover1)
    SimpleDraweeView mCover1;

    @BindView(R.id.sdvCover2)
    SimpleDraweeView mCover2;

    @BindView(R.id.sdvCover3)
    SimpleDraweeView mCover3;

    @BindView(R.id.sdvCover4)
    SimpleDraweeView mCover4;

    @BindView(R.id.sdvCover5)
    SimpleDraweeView mCover5;

    @BindView(R.id.tvNoFavorites)
    TextView mNoFavorites;


    public FavoriteMangaView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteMangaView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.sdvCover0)
    void onCover0Click() {

    }

    @OnClick(R.id.sdvCover1)
    void onCover1Click() {

    }

    @OnClick(R.id.sdvCover2)
    void onCover2Click() {

    }

    @OnClick(R.id.sdvCover3)
    void onCover3Click() {

    }

    @OnClick(R.id.sdvCover4)
    void onCover4Click() {

    }

    @OnClick(R.id.sdvCover5)
    void onCover5Click() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
    }

    @Override
    public void setContent(final UserDigest content) {
        if (!content.hasFavorites()) {
            mMangaGrid0.setVisibility(GONE);
            mMangaGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        final ArrayList<UserDigest.Favorite> favorites = content.getFavorites();
        final ArrayList<UserDigest.Favorite.MangaItem> manga = new ArrayList<>();

        for (final UserDigest.Favorite favorite : favorites) {
            final UserDigest.Favorite.AbsItem item = favorite.getItem();

            if (item.getType() == UserDigest.Favorite.AbsItem.Type.MANGA) {
                manga.add((UserDigest.Favorite.MangaItem) item);
            }
        }

        if (manga.isEmpty()) {
            mMangaGrid0.setVisibility(GONE);
            mMangaGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        mNoFavorites.setVisibility(GONE);

        setPosterView(mCover0, manga, 1);
        setPosterView(mCover1, manga, 2);
        setPosterView(mCover2, manga, 3);
        mMangaGrid0.setVisibility(VISIBLE);

        if (manga.size() >= 4) {
            setPosterView(mCover3, manga, 4);
            setPosterView(mCover4, manga, 5);
            setPosterView(mCover5, manga, 6);
            mMangaGrid1.setVisibility(VISIBLE);
        } else {
            mMangaGrid1.setVisibility(GONE);
        }
    }

    private void setPosterView(final SimpleDraweeView view,
            final ArrayList<UserDigest.Favorite.MangaItem> manga, final int index) {
        if (manga.size() >= index) {
            view.setImageURI(Uri.parse(manga.get(index - 1).getManga().getPosterImageThumb()));
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(INVISIBLE);
        }
    }

}
