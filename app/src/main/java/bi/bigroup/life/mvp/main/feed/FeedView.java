package bi.bigroup.life.mvp.main.feed;

import java.util.List;

import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface FeedView extends BaseSwipeRefreshMvpView {
    void successSent();

    void setFeedList(List<Feed> list);

    void addFeedList(List<Feed> list);

    void showLoadingItemIndicator(boolean show);

}
