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
import rx.functions.Func2;
import rx.schedulers.Schedulers;

@InjectViewState
public class BiOfficePresenter extends BaseMvpPresenter<BiOfficeView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
//        combineServiceTask();
    }

    private void combineServiceTask() {
        TasksServicesRepository repository = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getServiceDeskInbox(),
                repository.getInboxTasks(false),
                new Func2<List<Service>, List<Task>, CombineServiceTask>() {
                    @Override
                    public CombineServiceTask call(List<Service> services, List<Task> tasks) {
                        return new CombineServiceTask(services, tasks);
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
