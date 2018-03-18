package bi.bigroup.life.mvp.main.employees;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.db.EmployeeDao;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class EmployeePagePresenter extends BaseMvpPresenter<EmployeePageView> {

    private EmployeeDao employeeDao;

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        employeeDao = dataLayer.getDatabase().employeeDao();
    }

    public void getEmployee(String code, boolean isInternetOn) {
        if (isInternetOn) {
            Subscription subscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                    .getEmployee(code)
                    .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                    .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                    .subscribe(new Subscriber<Employee>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            handleResponseError(context, e);
                        }

                        @Override
                        public void onNext(Employee object) {
                            if (object != null) {
                                getViewState().setEmployee(object);
                            }
                        }
                    });
            compositeSubscription.add(subscription);
        } else {
            Employee employee = employeeDao.loadUserByCode(code);
            if (employee != null) {
                getViewState().setEmployee(employee);
            }
        }
    }
}