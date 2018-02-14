package bi.bigroup.life.ui.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;
import bi.bigroup.life.utils.recycler_view.SwipeRefreshUtils;
import butterknife.BindView;

public abstract class BaseSwipeRefreshFragment extends BaseFragment implements BaseSwipeRefreshMvpView {
    @BindView(R.id.swipeRefresh) protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) protected RecyclerView recycler_view;

    protected void configureRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        setSwipeRefreshLayout();
        SwipeRefreshUtils.setColorSchemeColors(getContext(), swipeRefreshLayout);
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this::swipeToRefresh);
    }

    protected abstract void swipeToRefresh();

    ///////////////////////////////////////////////////////////////////////////
    // BaseSwipeRefreshMvpView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showRefreshLoading(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

}
