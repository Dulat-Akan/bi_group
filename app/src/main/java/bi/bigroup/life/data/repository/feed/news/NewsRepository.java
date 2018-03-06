package bi.bigroup.life.data.repository.feed.news;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;

public interface NewsRepository {

    void setAPI(API api);

    Observable<News> getNews(String id);

    Observable<ResponseBody> likeNews(String id);

    Observable<Comment> addComment(String id, AddComment addComment);

    Observable<ResponseBody> likeNewsComment(String id, String commentId, int vote);

    Observable<List<News>> getPopularNews(int top);
}
