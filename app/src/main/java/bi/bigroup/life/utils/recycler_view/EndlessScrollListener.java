package bi.bigroup.life.utils.recycler_view;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import bi.bigroup.life.utils.Validate;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int DEFAULT_THRESHOLD = 5;

    private final int threshold;
    private final RecyclerView recyclerView;
    private final LinearLayoutManager layoutManager;
    private final Handler handler;
    private final Runnable requestMoreItemsRunnable;
    private boolean isRunnablePending;

    public EndlessScrollListener(RecyclerView recyclerView) {
        this(recyclerView, DEFAULT_THRESHOLD);
    }

    public EndlessScrollListener(RecyclerView recyclerView, int threshold) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("Only recycler views with LinearLayoutManagers are supported.");
        }
        this.threshold = threshold;
        this.recyclerView = recyclerView;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        Validate.runningOnUiThread();
        this.handler = new Handler();
        this.requestMoreItemsRunnable = () -> {
            isRunnablePending = false;
            onRequestMoreItems();
        };
    }

    public void triggerCheckForMoreItems() {
        int totalItems = layoutManager.getItemCount();
        int visibleItems = recyclerView.getChildCount();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();

        if (firstVisible + visibleItems + threshold > totalItems) {
            if (recyclerView.isComputingLayout()) {
                if (!isRunnablePending) {
                    isRunnablePending = true;
                    handler.post(requestMoreItemsRunnable);
                }
            } else {
                onRequestMoreItems();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        triggerCheckForMoreItems();
    }

    public abstract void onRequestMoreItems();
}
