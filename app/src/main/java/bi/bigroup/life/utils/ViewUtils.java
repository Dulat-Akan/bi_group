package bi.bigroup.life.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ScrollView;

import bi.bigroup.life.R;

public class ViewUtils {

    private static final int VIEW_ANIMATION_DURATION = 200;

    public static final int getSelectableItemBackground(View $receiver) {
        TypedArray typedArray = $receiver.getContext().obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
        try {
            int resourceId = typedArray.getResourceId(0, 0);
            return resourceId;
        } finally {
            typedArray.recycle();
        }
    }

    public static int getRelativeTopInAncestor(ViewGroup ancestor, View view) {
        if (view == null || view == ancestor) {
            return 0;
        } else {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                return view.getTop() + getRelativeTopInAncestor(ancestor, (View) parent);
            } else {
                return view.getTop();
            }
        }
    }

    public static ScrollView findScrollViewAncestor(View view) {
        if (view == null) {
            return null;
        } else {
            ViewParent parent = view.getParent();
            if (parent instanceof ScrollView) {
                return (ScrollView) parent;
            } else if (parent instanceof View) {
                return findScrollViewAncestor((View) parent);
            } else {
                return null;
            }
        }
    }

    public static float getWindowWidth(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void goneView(final View view) {
        if (view.getVisibility() != View.GONE) {
            view.animate().alpha(0.0f).setDuration(VIEW_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {

                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.GONE);
                }
            });
        }
    }

    public static void hideView(final View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.animate().alpha(0.0f).setDuration(VIEW_ANIMATION_DURATION).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public static void showView(View view) {
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() < 1.0f) {
            view.setVisibility(View.VISIBLE);
            view.animate().alpha(1.0f).setDuration(VIEW_ANIMATION_DURATION).setListener(null);
        }
    }

    public static int getResourceIdAttr(final Activity activity, final int attr) {
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }
}
