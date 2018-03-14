package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class SearchUserPresenter extends BaseMvpPresenter<SearchUserView> {
    private static final long TEXT_WATCHER_QUERY_DELAY = 300; // in ms
    private Timer timer;
    private Subscription searchSubscription;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
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
}
