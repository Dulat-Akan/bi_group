package bi.bigroup.life.data.repository.feed;

import java.util.List;

import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class FeedRepositoryImpl implements FeedRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<Feed>> getFeedList(int rows, int offset, Boolean withDescription) {
        return api
                .getFeedList(rows, offset, withDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Feed>> getNewsList(int rows, int offset) {
        return api
                .getNewsList(rows, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Feed>> getSuggestionsList(int rows, int offset) {
        return api
                .getSuggestionsList(rows, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Feed>> getQuestionnairesList(int rows, int offset) {
        return api
                .getQuestionnairesList(rows, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}