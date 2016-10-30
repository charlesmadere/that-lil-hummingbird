package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.MangaActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Manga;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteMangaView extends CardView implements AdapterView<UserDigest> {

    private ArrayList<UserDigest.Favorite.MangaItem> mManga;

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

    @BindView(R.id.tvShowMore)
    TextView mShowMore;


    public FavoriteMangaView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteMangaView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick(R.id.sdvCover0)
    void onCover0Click() {
        startMangaActivity(0);
    }

    @OnClick(R.id.sdvCover1)
    void onCover1Click() {
        startMangaActivity(1);
    }

    @OnClick(R.id.sdvCover2)
    void onCover2Click() {
        startMangaActivity(2);
    }

    @OnClick(R.id.sdvCover3)
    void onCover3Click() {
        startMangaActivity(3);
    }

    @OnClick(R.id.sdvCover4)
    void onCover4Click() {
        startMangaActivity(4);
    }

    @OnClick(R.id.sdvCover5)
    void onCover5Click() {
        startMangaActivity(5);
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

    private void setCoverView(final SimpleDraweeView view,
            final ArrayList<UserDigest.Favorite.MangaItem> manga, final int index) {
        if (manga.size() >= index) {
            view.setImageURI(manga.get(index - 1).getManga().getPosterImageThumb());
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

    private void startMangaActivity(final int index) {
        final Context context = getContext();
        final Manga manga = mManga.get(index).getManga();
        context.startActivity(MangaActivity.getLaunchIntent(context, manga.getId(),
                manga.getTitle()));
    }


    public interface OnShowMoreClickListener {
        void onShowMoreClick(final FavoriteMangaView v);
    }

}
