package bi.bigroup.life.mvp.main.feed;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.TOP_3;

@InjectViewState
public class MainFeedPresenter extends BaseMvpPresenter<MainFeedView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getPopularNews();
    }

    private void getPopularNews() {
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getPopularNews(TOP_3)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<News> object) {
                        if (object != null && object.size() > 0) {
                            getViewState().setPopularNews(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}
