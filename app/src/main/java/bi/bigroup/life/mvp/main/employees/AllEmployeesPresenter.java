package bi.bigroup.life.mvp.main.employees;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;

@InjectViewState
public class AllEmployeesPresenter extends BaseMvpPresenter<AllEmployeesView> {
    private int current_page;
    private Subscription listSubscription;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getEmployees(boolean is_load_more, boolean is_refresh) {
        if (listSubscription != null
                && !listSubscription.isUnsubscribed()) {
            listSubscription.unsubscribe();
        }
        if (is_load_more) {
            current_page += REQUEST_COUNT;
        } else {
            current_page = INITIAL_PAGE_NUMBER;
        }
        listSubscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                .getEmployees(REQUEST_COUNT, current_page, null)
                .doOnSubscribe(() -> showLoading(true, is_load_more, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_load_more, is_refresh))
                .subscribe(new Subscriber<List<Employee>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Employee> list) {
                        if (list != null && list.size() > 0) {
                            if (is_refresh) {
                                getViewState().setEmployeesList(list);
                            } else {
                                getViewState().addEmployeesList(list);
                            }
                        } else if (!is_load_more) {
                            getViewState().showNotFoundPlaceholder();
                        }
                    }
                });
    }

    private void showLoading(boolean show, boolean is_load_more, boolean is_refresh) {
        if (!is_load_more && !is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else if (is_load_more) {
            getViewState().showLoadingItemIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}
