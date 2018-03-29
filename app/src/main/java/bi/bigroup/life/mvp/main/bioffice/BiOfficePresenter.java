package bi.bigroup.life.mvp.main.bioffice;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepository;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepositoryProvider;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static bi.bigroup.life.utils.Constants.TOP_3;

@InjectViewState
public class BiOfficePresenter extends BaseMvpPresenter<BiOfficeView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getPopularNews();
        combineServicesTasks();
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

    private void combineServicesTasks() {
        TasksServicesRepository repository = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
//                repository.getServiceDeskOutbox(),
                repository.getInboxTasks(false),
                repository.getOutboxTasks(),
                new Func2<List<Task>, List<Task>, CombineServiceTask>() {//List<Service>,
                    @Override
                    public CombineServiceTask call(
                            List<Task> inboxTasks, List<Task> outboxTasks) {//List<Service> outboxServices,
                        return new CombineServiceTask(null, inboxTasks, outboxTasks);
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CombineServiceTask>() {
                    @Override
                    public void onNext(CombineServiceTask object) {
                        if (object != null) {
                            getViewState().setCombinedServiceTask(object);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }
                });
    }
}