package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public final class PaletteUtils {

    public static void applyParallaxColors(final String url, final Activity activity,
            final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
            final SimpleDraweeView simpleDraweeView, final TabLayout tabLayout) {
        final ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(new PalettePostprocessor(activity, appBarLayout,
                        collapsingToolbarLayout, tabLayout))
                .build();

        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request).setOldController(simpleDraweeView.getController())
                .build();

        simpleDraweeView.setController(controller);
    }

}
