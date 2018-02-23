package bi.bigroup.life.data.repository.notifications;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class NotificationsRepositoryImpl implements NotificationsRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<Notification>> getNotifications() {
        return api
                .getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Employee> getEmployee(String code) {
        return api
                .getEmployee(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}