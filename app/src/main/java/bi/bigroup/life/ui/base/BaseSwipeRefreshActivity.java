package bi.bigroup.life.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;
import bi.bigroup.life.utils.recycler_view.SwipeRefreshUtils;
import butterknife.BindView;

public abstract class BaseSwipeRefreshActivity extends BaseActivity implements BaseSwipeRefreshMvpView {

    @BindView(R.id.swipeRefresh) protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) protected  RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeRefreshLayout();
        SwipeRefreshUtils.setColorSchemeColors(this, swipeRefreshLayout);
    }

    protected void configureRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this::swipeToRefresh);
    }

    @Override
    public void showRefreshLoading(boolean show){
        swipeRefreshLayout.setRefreshing(show);
    }

    protected abstract void swipeToRefresh();
}
