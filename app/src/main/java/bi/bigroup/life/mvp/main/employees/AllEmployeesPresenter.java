package bi.bigroup.life.mvp.main.employees;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.db.EmployeeDao;
import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.params.employees.DobCongrats;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;

import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;

@InjectViewState
public class AllEmployeesPresenter extends BaseMvpPresenter<AllEmployeesView> {
    private static final long TEXT_WATCHER_QUERY_DELAY = 300; // in ms
    private Timer timer;

    private int current_page;
    private Subscription listSubscription;
    private Subscription searchSubscription;
    private EmployeeDao employeeDao;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        employeeDao = dataLayer.getDatabase().employeeDao();
    }

    public void getEmployees(boolean is_load_more, boolean is_refresh, boolean isBirthdayToday,
                             boolean isInternetOn) {
        if (listSubscription != null
                && !listSubscription.isUnsubscribed()) {
            listSubscription.unsubscribe();
        }
        if (isInternetOn) {
            if (is_load_more) {
                current_page += REQUEST_COUNT;
            } else {
                current_page = INITIAL_PAGE_NUMBER;
            }
            listSubscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                    .getEmployees(REQUEST_COUNT, current_page, isBirthdayToday)
                    .doOnSubscribe(() -> showLoading(true, is_load_more, is_refresh))
                    .doOnTerminate(() -> showLoading(false, is_load_more, is_refresh))
                    .subscribe(new Subscriber<ListOf<Employee>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            handleResponseError(context, e);
                        }

                        @Override
                        public void onNext(ListOf<Employee> list) {
                            getViewState().showLoadingIndicator(false);
                            if (list != null && list.list != null && list.list.size() > 0) {
                                if (is_refresh || !is_load_more) {
                                    getViewState().setEmployeesList(list.list);
                                } else {
                                    getViewState().addEmployeesList(list.list);
                                }

                                if (!is_load_more) {
                                    new Thread(() -> {
                                        employeeDao.deleteAllEmployees();
                                        try {
                                            employeeDao.insertMultipleEmployees(list.list);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }).start();
                                }
                            } else if (!is_load_more) {
                                getViewState().showNotFoundPlaceholder();
                            }
                        }
                    });
        } else {
            List<Employee> employees = employeeDao.loadAllEmployees();
            if (employees.size() > 0) {
                getViewState().setEmployeesList(employees);
            }
            showLoading(false, is_load_more, is_refresh);
        }
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

    public void onQueryTextChange(final String keyword) {
        if (keyword.isEmpty()) {
            return;
        }

        if (timer != null)
            timer.cancel();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                searchEmployees(keyword);
            }
        }, TEXT_WATCHER_QUERY_DELAY);
    }

    private void searchEmployees(String keyword) {
        if (searchSubscription != null
                && !searchSubscription.isUnsubscribed()) {
            searchSubscription.unsubscribe();
        }
        searchSubscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                .searchEmployees(keyword, null)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
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
                            getViewState().setSearchResults(list);
                        }
                    }
                });
    }

    public List<Employee> filter(List<Employee> userList, String query) {
        query = query.toLowerCase();
        final List<Employee> filteredModelList = new ArrayList<>();
        for (Employee model : userList) {
            final String text = model.getFullName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void sendCongrats(String code, String content) {
        Subscription subscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                .sendCongrats(code, new DobCongrats(content))
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (responseBody != null) {
                            getViewState().showRequestSuccess(R.string.success_response);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}
