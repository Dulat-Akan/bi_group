package bi.bigroup.life.ui.main.feed;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.mvp.main.feed.FeedPresenter;
import bi.bigroup.life.mvp.main.feed.FeedView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.ui.main.feed.news.NewsDetailActivity;
import bi.bigroup.life.ui.main.feed.suggestions.SuggestionDetailActivity;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;

public class FeedFragment extends BaseSwipeRefreshFragment implements FeedView {
    @InjectPresenter
    FeedPresenter mvpPresenter;
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
        mvpPresenter.init(getContext(), dataLayer);
        mvpPresenter.getFeedList(false, false);
        configureRecyclerView();
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new FeedAdapter(getContext(), dataLayer.getPicasso());
        mAdapter.setCallback(new FeedAdapter.Callback() {
            @Override
            public void onNewsItemClick(String newsId) {
                startActivity(NewsDetailActivity.getIntent(getContext(), newsId));
            }

            @Override
            public void onSuggestionItemClick(String suggestionId) {
                startActivity(SuggestionDetailActivity.getIntent(getContext(), suggestionId));
            }

            @Override
            public void onNewsLike(String id, boolean liked) {
                mvpPresenter.likeSubscriptionUnsubscribe();
                mvpPresenter.likeNews(id);
            }

            @Override
            public void onSuggestionLike(String id, int voteType) {
                mvpPresenter.suggestionLikeSubscriptionUnsubscribe();
                mvpPresenter.likeSuggestion(id, voteType);
            }
        });
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading() && mAdapter.getItemCount() > 1) {
                    mvpPresenter.getFeedList(true, false);
                }
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getFeedList(false, true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FeedView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
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

    @Override
    public void showTransparentIndicator(boolean show) {
        pb_indicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}