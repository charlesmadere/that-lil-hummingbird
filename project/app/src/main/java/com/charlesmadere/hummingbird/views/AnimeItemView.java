package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.models.Anime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeItemView extends CardView implements AdapterView<Anime>, View.OnClickListener {

    private Anime mAnime;

    @BindView(R.id.animeView)
    InternalAnimeItemView mAnimeView;


    public AnimeItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimeItemView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Anime getAnime() {
        return mAnime;
    }

    @Override
    public void onClick(final View v) {
        final Context context = getContext();
        context.startActivity(AnimeActivity.getLaunchIntent(context, mAnime));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setOnClickListener(this);
    }

    @Override
    public void setContent(final Anime content) {
        mAnime = content;
        mAnimeView.setContent(content);
    }

}
