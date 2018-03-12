package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class TasksServicesPresenter extends BaseMvpPresenter<TasksServicesView> {

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getInboxTasks(boolean is_refresh, boolean isOnlyToday) {
        Subscription listSubscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
                .getInboxTasks(isOnlyToday)
                .doOnSubscribe(() -> showLoading(true, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_refresh))
                .subscribe(new Subscriber<List<Task>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Task> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().addList(list);
                        }
                    }
                });
        compositeSubscription.add(listSubscription);
    }

    public void getOutboxTasks(boolean is_refresh) {
        Subscription listSubscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
                .getOutboxTasks()
                .doOnSubscribe(() -> showLoading(true, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_refresh))
                .subscribe(new Subscriber<List<Task>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Task> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().addList(list);
                        }
                    }
                });
        compositeSubscription.add(listSubscription);
    }

//    public void getServiceDeskInbox(boolean is_refresh, boolean isOnlyToday) {
//        Subscription listSubscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
//                .getServiceDeskInbox()
//                .doOnSubscribe(() -> showLoading(true, is_refresh))
//                .doOnTerminate(() -> showLoading(false, is_refresh))
//                .subscribe(new Subscriber<List<Service>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        handleResponseError(context, e);
//                    }
//
//                    @Override
//                    public void onNext(List<Service> list) {
//                        if (list != null && list.size() > 0) {
//                            getViewState().setList(list);
//                        }
//                        getInboxTasks(is_refresh, isOnlyToday);
//                    }
//                });
//        compositeSubscription.add(listSubscription);
//    }

    public void getServiceDeskOutbox(boolean is_refresh) {
        Subscription listSubscription = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi())
                .getServiceDeskOutbox()
                .doOnSubscribe(() -> showLoading(true, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_refresh))
                .subscribe(new Subscriber<List<Service>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Service> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().setList(list);
                        }
                        getOutboxTasks(is_refresh);
                    }
                });
        compositeSubscription.add(listSubscription);
    }


    private void showLoading(boolean show, boolean is_refresh) {
        if (!is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}
