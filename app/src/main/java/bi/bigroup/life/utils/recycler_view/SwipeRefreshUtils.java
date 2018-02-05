package bi.bigroup.life.utils.recycler_view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import bi.bigroup.life.utils.ColorUtils;


public class SwipeRefreshUtils {
    public static void setColorSchemeColors(Context context, SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeColors(
                ColorUtils.getColorsForSwipeRefreshLayout(context)
        );
    }

    public static void showSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        }
    }

    public static void hideSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
        }
    }
}
