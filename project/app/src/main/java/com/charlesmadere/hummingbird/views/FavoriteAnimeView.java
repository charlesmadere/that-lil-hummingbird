package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Anime;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteAnimeView extends CardView implements AdapterView<UserDigest> {

    private ArrayList<UserDigest.Favorite.AnimeItem> mAnime;

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
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sdvPoster0)
    void onPoster0Click() {
        startAnimeActivity(0);
    }

    @OnClick(R.id.sdvPoster1)
    void onPoster1Click() {
        startAnimeActivity(1);
    }

    @OnClick(R.id.sdvPoster2)
    void onPoster2Click() {
        startAnimeActivity(2);
    }

    @OnClick(R.id.sdvPoster3)
    void onPoster3Click() {
        startAnimeActivity(3);
    }

    @OnClick(R.id.sdvPoster4)
    void onPoster4Click() {
        startAnimeActivity(4);
    }

    @OnClick(R.id.sdvPoster5)
    void onPoster5Click() {
        startAnimeActivity(5);
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

        if (mAnime == null) {
            mAnime = new ArrayList<>();
        } else {
            mAnime.clear();
        }

        // noinspection ConstantConditions
        for (final UserDigest.Favorite favorite : favorites) {
            final UserDigest.Favorite.AbsItem item = favorite.getItem();

            if (item.getType() == UserDigest.Favorite.AbsItem.Type.ANIME) {
                mAnime.add((UserDigest.Favorite.AnimeItem) item);
            }
        }

        if (mAnime.isEmpty()) {
            mAnimeGrid0.setVisibility(GONE);
            mAnimeGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        mNoFavorites.setVisibility(GONE);

        setPosterView(mPoster0, mAnime, 1);
        setPosterView(mPoster1, mAnime, 2);
        setPosterView(mPoster2, mAnime, 3);
        mAnimeGrid0.setVisibility(VISIBLE);

        if (mAnime.size() >= 4) {
            setPosterView(mPoster3, mAnime, 4);
            setPosterView(mPoster4, mAnime, 5);
            setPosterView(mPoster5, mAnime, 6);
            mAnimeGrid1.setVisibility(VISIBLE);
        } else {
            mAnimeGrid1.setVisibility(GONE);
        }
    }

    private void setPosterView(final SimpleDraweeView view,
            final ArrayList<UserDigest.Favorite.AnimeItem> anime, final int index) {
        if (anime.size() >= index) {
            view.setImageURI(anime.get(index - 1).getAnime().getPosterImage());
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(INVISIBLE);
        }
    }

    private void startAnimeActivity(final int index) {
        final Context context = getContext();
        final Anime anime = mAnime.get(index).getAnime();
        context.startActivity(AnimeActivity.getLaunchIntent(context, anime));
    }

}
