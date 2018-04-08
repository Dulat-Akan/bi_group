package bi.bigroup.life.mvp.main.feed;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.BaseMvpView;

public interface MainFeedView extends BaseMvpView {
    void successSent();

    void setPopularNews(List<News> object);

}
