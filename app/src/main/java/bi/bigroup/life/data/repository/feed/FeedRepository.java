package bi.bigroup.life.data.repository.feed;

import java.util.List;

import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface FeedRepository {

    void setAPI(API api);

    Observable<List<Feed>> getFeedList(int rows, int offset, Boolean withDescription);

    Observable<List<Feed>> getNewsList(int rows, int offset);

    Observable<List<Feed>> getSuggestionsList(int rows, int offset);

    Observable<List<Feed>> getQuestionnairesList(int rows, int offset);
}