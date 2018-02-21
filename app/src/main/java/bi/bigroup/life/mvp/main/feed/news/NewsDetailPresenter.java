package bi.bigroup.life.mvp.main.feed.news;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class NewsDetailPresenter extends BaseMvpPresenter<NewsDetailView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getNewsDetail(String id) {
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getNews(id)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(News object) {
                        if (object != null) {
                            getViewState().setNews(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}