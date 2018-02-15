package bi.bigroup.life.data.repository.employees;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class EmployeesRepositoryImpl implements EmployeesRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<Employee>> getEmployees(int rows, int offset, Boolean withDescription) {
        return api
                .getEmployees(rows, offset, withDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}