package bi.bigroup.life.data.repository.bioffice.tasks_sdesk;

import java.util.List;

import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class TasksServicesRepositoryImpl implements TasksServicesRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<Task>> getOutboxTasks() {
        return api
                .getOutboxTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Task>> getInboxTasks(Boolean isOnlyToday) {
        return api
                .getInboxTasks(isOnlyToday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Service>> getServiceDeskOutbox() {
        return api
                .getServiceDeskOutbox()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Service>> getServiceDeskInbox() {
        return api
                .getServiceDeskInbox()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}