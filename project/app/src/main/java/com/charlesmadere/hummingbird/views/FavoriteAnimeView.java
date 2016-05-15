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

public class FavoriteAnimeView extends CardView implements AdapterView<UserDigest> {

    @BindView(R.id.llFavoriteAnimeGrid0)
    LinearLayout mAnimeGrid0;

    @BindView(R.id.llFavoriteAnimeGrid1)
    LinearLayout mAnimeGrid1;

    @BindView(R.id.sdvPoster0)
    SimpleDraweeView mPoster0;

    @BindView(R.id.sdvPoster1)
    SimpleDraweeView mPoster1;

    @BindView(R.id.sdvPoster2)
    SimpleDraweeView mPoster2;

    @BindView(R.id.sdvPoster3)
    SimpleDraweeView mPoster3;

    @BindView(R.id.sdvPoster4)
    SimpleDraweeView mPoster4;

    @BindView(R.id.sdvPoster5)
    SimpleDraweeView mPoster5;

    @BindView(R.id.tvNoFavorites)
    TextView mNoFavorites;


    public FavoriteAnimeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteAnimeView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            mAnimeGrid0.setVisibility(GONE);
            mAnimeGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        final ArrayList<UserDigest.Favorite> favorites = content.getFavorites();
        final ArrayList<UserDigest.Favorite.AnimeItem> anime = new ArrayList<>();

        for (final UserDigest.Favorite favorite : favorites) {
            final UserDigest.Favorite.AbsItem item = favorite.getItem();

            if (item.getType() == UserDigest.Favorite.AbsItem.Type.ANIME) {
                anime.add((UserDigest.Favorite.AnimeItem) item);
            }
        }

        if (anime.isEmpty()) {
            mAnimeGrid0.setVisibility(GONE);
            mAnimeGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        setPosterView(mPoster0, anime, 1);
        setPosterView(mPoster1, anime, 2);
        setPosterView(mPoster2, anime, 3);
        mAnimeGrid0.setVisibility(VISIBLE);

        if (anime.size() >= 4) {
            setPosterView(mPoster3, anime, 4);
            setPosterView(mPoster4, anime, 5);
            setPosterView(mPoster5, anime, 6);
            mAnimeGrid1.setVisibility(VISIBLE);
        } else {
            mAnimeGrid1.setVisibility(GONE);
        }
    }

    private void setPosterView(final SimpleDraweeView view,
            final ArrayList<UserDigest.Favorite.AnimeItem> anime, final int index) {
        if (anime.size() >= index) {
            view.setImageURI(Uri.parse(anime.get(index - 1).getAnime().getThumbnail()));
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(INVISIBLE);
        }
    }

}
