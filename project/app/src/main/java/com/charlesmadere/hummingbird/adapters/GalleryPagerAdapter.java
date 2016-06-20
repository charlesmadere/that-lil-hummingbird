package com.charlesmadere.hummingbird.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.views.GalleryPagerView;

import java.util.ArrayList;

public class GalleryPagerAdapter extends PagerAdapter {

    private final ArrayList<String> mUrls;


    public GalleryPagerAdapter(final ArrayList<String> urls) {
        mUrls = urls;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final GalleryPagerView view = GalleryPagerView.inflate(container);
        view.setContent(mUrls.get(position));
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

}
