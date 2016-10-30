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

public class FavoriteMangaView extends CardView implements AdapterView<UserDigest> {

    private ArrayList<UserDigest.Favorite.MangaItem> mManga;

    @BindView(R.id.fmivCover0)
    FavoriteMangaItemView mCover0;

    @BindView(R.id.fmivCover1)
    FavoriteMangaItemView mCover1;

    @BindView(R.id.fmivCover2)
    FavoriteMangaItemView mCover2;

    @BindView(R.id.fmivCover3)
    FavoriteMangaItemView mCover3;

    @BindView(R.id.fmivCover4)
    FavoriteMangaItemView mCover4;

    @BindView(R.id.fmivCover5)
    FavoriteMangaItemView mCover5;

    @BindView(R.id.llFavoriteMangaGrid0)
    LinearLayout mMangaGrid0;

    @BindView(R.id.llFavoriteMangaGrid1)
    LinearLayout mMangaGrid1;

    @BindView(R.id.tvNoFavorites)
    TextView mNoFavorites;

    @BindView(R.id.tvShowMore)
    TextView mShowMore;


    public FavoriteMangaView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteMangaView(final Context context, final AttributeSet attrs,
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
            mMangaGrid0.setVisibility(GONE);
            mMangaGrid1.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        final ArrayList<UserDigest.Favorite> favorites = content.getFavorites();

        if (mManga == null) {
            mManga = new ArrayList<>();
        } else {
            mManga.clear();
        }

        // noinspection ConstantConditions
        for (final UserDigest.Favorite favorite : favorites) {
            final UserDigest.Favorite.AbsItem item = favorite.getItem();

            if (item.getType() == UserDigest.Favorite.AbsItem.Type.MANGA) {
                mManga.add((UserDigest.Favorite.MangaItem) item);
            }
        }

        if (mManga.isEmpty()) {
            mMangaGrid0.setVisibility(GONE);
            mMangaGrid1.setVisibility(GONE);
            mShowMore.setVisibility(GONE);
            mNoFavorites.setVisibility(VISIBLE);
            return;
        }

        mNoFavorites.setVisibility(GONE);

        setCoverView(mCover0, mManga, 1);
        setCoverView(mCover1, mManga, 2);
        setCoverView(mCover2, mManga, 3);
        mMangaGrid0.setVisibility(VISIBLE);

        if (mManga.size() >= 4) {
            setCoverView(mCover3, mManga, 4);
            setCoverView(mCover4, mManga, 5);
            setCoverView(mCover5, mManga, 6);
            mMangaGrid1.setVisibility(VISIBLE);

            if (mManga.size() > 6) {
                mShowMore.setVisibility(VISIBLE);
            } else {
                mShowMore.setVisibility(GONE);
            }
        } else {
            mMangaGrid1.setVisibility(GONE);
            mShowMore.setVisibility(GONE);
        }
    }

    private void setCoverView(final FavoriteMangaItemView view,
            final ArrayList<UserDigest.Favorite.MangaItem> manga, final int index) {
        if (manga.size() >= index) {
            view.setContent(manga.get(index - 1));
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
                    l.onShowMoreClick(FavoriteMangaView.this);
                }
            });
        }
    }


    public interface OnShowMoreClickListener {
        void onShowMoreClick(final FavoriteMangaView v);
    }

}
