package bi.bigroup.life.data.repository.employees;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.employees.DobCongrats;
import okhttp3.ResponseBody;
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
    public Observable<ListOf<Employee>> getEmployees(int rows, int offset, Boolean IsBirthdayToday) {
        return api
                .getEmployees(rows, offset, IsBirthdayToday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Employee>> getEmployeesEvents() {
        return api
                .getEmployeesEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Employee>> searchEmployees(String filterText, String top) {
        return api
                .searchEmployees(filterText, top)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Vacancy>> getVacancies() {
        return api
                .getVacancies()
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

    @Override
    public Observable<ResponseBody> sendCongrats(String code, DobCongrats params) {
        return api
                .sendCongrats(code, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}