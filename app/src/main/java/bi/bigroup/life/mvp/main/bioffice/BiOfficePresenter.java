package bi.bigroup.life.mvp.main.bioffice;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepository;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

@InjectViewState
public class BiOfficePresenter extends BaseMvpPresenter<BiOfficeView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        combineServicesTasks();
    }

    private void combineServicesTasks() {
        TasksServicesRepository repository = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getServiceDeskOutbox(),
                repository.getInboxTasks(false),
                repository.getOutboxTasks(),
                new Func3<List<Service>, List<Task>, List<Task>, CombineServiceTask>() {
                    @Override
                    public CombineServiceTask call(
                            List<Service> outboxServices, List<Task> inboxTasks, List<Task> outboxTasks) {
                        return new CombineServiceTask(outboxServices, inboxTasks, outboxTasks);
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