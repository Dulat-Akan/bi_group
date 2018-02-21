package bi.bigroup.life.mvp.main.feed.news;

import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.BaseMvpView;

public interface NewsDetailView extends BaseMvpView {
    void setNews(News object);
}
