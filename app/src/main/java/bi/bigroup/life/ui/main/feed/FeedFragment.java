package bi.bigroup.life.ui.main.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.mvp.main.feed.FeedPresenter;
import bi.bigroup.life.mvp.main.feed.FeedView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.ui.main.BottomNavigationTabFragment;
import bi.bigroup.life.ui.main.feed.news.AddNewsActivity;
import bi.bigroup.life.ui.main.feed.news.NewsDetailActivity;
import bi.bigroup.life.ui.main.feed.suggestions.NewSuggestionActivity;
import bi.bigroup.life.ui.main.feed.suggestions.SuggestionDetailActivity;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;

import static bi.bigroup.life.utils.ConnectionDetector.isInternetOn;
import static bi.bigroup.life.utils.Constants.KEY_CODE;
import static bi.bigroup.life.utils.Constants.KEY_TYPE;

public class FeedFragment extends BaseSwipeRefreshFragment implements FeedView, BottomNavigationTabFragment {
    public static final int TAB_FEED_ALL = 1;
    public static final int TAB_FEED_NEWS = 2;
    public static final int TAB_FEED_SUGGESTIONS = 3;
    public static final int TAB_FEED_QUESTIONNAIRES = 4;

    public static final int UPDATE_NEWS_FEED = 12;
    private int tabType;

    @InjectPresenter
    FeedPresenter mvpPresenter;
    private FeedAdapter mAdapter;

    public static FeedFragment newInstance(int tabType) {
        FeedFragment fragment = new FeedFragment();
        Bundle data = new Bundle();
        data.putInt(KEY_TYPE, tabType);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_feed;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        handleIntent();
        mvpPresenter.getFeedList(false, false, isInternetOn(getContext()));
        configureRecyclerView();
    }

    private void handleIntent() {
        if (getArguments() != null && getArguments().containsKey(KEY_TYPE)) {
            tabType = getArguments().getInt(KEY_TYPE);
        }
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new FeedAdapter(dataLayer.getPicasso());
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
                    mvpPresenter.getFeedList(true, false, true);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (data.getIntExtra(KEY_CODE, 0) == UPDATE_NEWS_FEED) {
            mvpPresenter.getFeedList(false, true, true);
        }
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getFeedList(false, true, isInternetOn(getContext()));
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

    ///////////////////////////////////////////////////////////////////////////
    // BottomNavigationTabFragment implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onBottomNavigationTabReselected() {
        if (mAdapter.getItemCount() > 0)
            ((LinearLayoutManager) recycler_view.getLayoutManager()).scrollToPositionWithOffset(0, 0);
    }

    public void onAddSuggestionClick() {
        startActivityForResult(NewSuggestionActivity.getIntent(getContext()), UPDATE_NEWS_FEED);
    }

    public void onAddNewsClick() {
        startActivityForResult(AddNewsActivity.getIntent(getContext()), UPDATE_NEWS_FEED);
    }
}