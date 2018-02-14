package bi.bigroup.life.mvp.main.feed;

import java.util.List;

import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.mvp.BaseMvpView;

public interface FeedView extends BaseMvpView {
    void successSent();

    void setFiltersList(List<FilterButton> filterButtonList);
}
