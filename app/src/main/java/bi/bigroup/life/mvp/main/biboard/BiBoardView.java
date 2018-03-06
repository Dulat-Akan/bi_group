package bi.bigroup.life.mvp.main.biboard;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.BaseMvpView;

public interface BiBoardView extends BaseMvpView {
    void successSent();

    void setPopularNews(List<News> object);
}
