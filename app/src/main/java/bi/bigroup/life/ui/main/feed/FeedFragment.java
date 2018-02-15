package bi.bigroup.life.ui.main.feed;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.mvp.main.feed.FeedPresenter;
import bi.bigroup.life.mvp.main.feed.FeedView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.utils.LOTimber;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;
import butterknife.BindView;
import it.sephiroth.android.library.widget.HListView;

public class FeedFragment extends BaseSwipeRefreshFragment implements FeedView {
    @InjectPresenter
    FeedPresenter mvpPresenter;

    @BindView(R.id.hlv_filters) HListView hlv_filters;
    private FilterButtonsAdapter horizontalAdapter;
    private FeedAdapter mAdapter;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_feed;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        configureHorizontalList();
        mvpPresenter.init(getContext(), dataLayer);
        mvpPresenter.getFeedList(false, false);
        configureRecyclerView();
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new FeedAdapter(getContext());
//        mAdapter.setCallback(area -> startActivity(AreaDetailActivity.getIntent(this, area.id)));
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view, 1) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading()) {
                    mvpPresenter.getFeedList(true, false);
                }
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getFeedList(false, true);
    }

    private void configureHorizontalList() {
        horizontalAdapter = new FilterButtonsAdapter(getContext());
        horizontalAdapter.setCallback((position) -> LOTimber.d("salkdasjd"));
        hlv_filters.setAdapter(horizontalAdapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FeedView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    @Override
    public void setFiltersList(List<FilterButton> filterButtonList) {
        horizontalAdapter.setData(filterButtonList);
    }

    @Override
    public void setFeedList(List<Feed> list) {
        mAdapter.clearData();
        mAdapter.addData(list);
    }

    @Override
    public void addFeedList(List<Feed> list) {
        mAdapter.addData(list);
    }

    @Override
    public void showLoadingItemIndicator(boolean show) {
        recycler_view.post(() -> mAdapter.setLoading(show));
    }
}