package bi.bigroup.life.data.repository.feed.news;

import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface NewsRepository {

    void setAPI(API api);

    Observable<News> getNews(String id);
}