package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.UserDigest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAnimeView extends CardView implements AdapterView<UserDigest> {

    private ArrayList<UserDigest.Favorite.AnimeItem> mAnime;

    @BindView(R.id.faivPoster0)
    FavoriteAnimeItemView mPoster0;

    @BindView(R.id.faivPoster1)
    FavoriteAnimeItemView mPoster1;

    @BindView(R.id.faivPoster2)
    FavoriteAnimeItemView mPoster2;

    @BindView(R.id.faivPoster3)
    FavoriteAnimeItemView mPoster3;

    @BindView(R.id.faivPoster4)
    FavoriteAnimeItemView mPoster4;

    @BindView(R.id.faivPoster5)
    FavoriteAnimeItemView mPoster5;

    @BindView(R.id.llFavoriteAnimeGrid0)
    LinearLayout mAnimeGrid0;

    @BindView(R.id.llFavoriteAnimeGrid1)
    LinearLayout mAnimeGrid1;

    @BindView(R.id.tvNoFavorites)
    TextView mNoFavorites;

    @BindView(R.id.tvShowMore)
    TextView mShowMore;


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
            mShowMore.setVisibility(GONE);
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

            if (mAnime.size() > 6) {
                mShowMore.setVisibility(VISIBLE);
            } else {
                mShowMore.setVisibility(GONE);
            }
        } else {
            mAnimeGrid1.setVisibility(GONE);
            mShowMore.setVisibility(GONE);
        }
    }

    private void setPosterView(final FavoriteAnimeItemView view,
            final ArrayList<UserDigest.Favorite.AnimeItem> anime, final int index) {
        if (anime.size() >= index) {
            view.setContent(anime.get(index - 1));
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(INVISIBLE);
        }
    }

    public void setOnShowMoreClickListener(@Nullable final OnShowMoreClickListener l) {
        if (l == null) {
            mShowMore.setClickable(false);
        } else {
            mShowMore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {
                    l.onShowMoreClick(FavoriteAnimeView.this);
                }
            });
        }
    }


    public interface OnShowMoreClickListener {
        void onShowMoreClick(final FavoriteAnimeView v);
    }

}
