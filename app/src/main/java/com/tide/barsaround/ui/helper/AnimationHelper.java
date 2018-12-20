package com.tide.barsaround.ui.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import static com.tide.barsaround.ui.common.ColorConstants.OPAQUE_ALPHA;
import static com.tide.barsaround.ui.common.ColorConstants.TRANSPARENT_ALPHA;

public class AnimationHelper {

    /**
     * performs a crossfade animation on two views, fading one out and then fading a new one. Good for loading spinners etc...
     * The viewToFadeIn should really have visibility set to Gone before this animation begins.
     */

    public static void crossFadeAnimation(final View viewToFadeOut, final View viewToFadeIn,
                                          final int duration) {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        viewToFadeIn.setAlpha(TRANSPARENT_ALPHA);
        viewToFadeIn.setVisibility(View.VISIBLE);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        viewToFadeOut.animate().alpha(TRANSPARENT_ALPHA).setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        viewToFadeOut.setVisibility(View.GONE);

                        // Animate the content view to 100% opacity, and clear any animation
                        // listener set on the view.
                        viewToFadeIn.animate().alpha(OPAQUE_ALPHA).setDuration(duration);
                    }
                });


    }


    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

}
