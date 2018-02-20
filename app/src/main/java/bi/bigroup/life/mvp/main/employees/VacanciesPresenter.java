package bi.bigroup.life.mvp.main.employees;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class VacanciesPresenter extends BaseMvpPresenter<VacanciesView> {
    private Subscription listSubscription;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void getVacancies(boolean is_load_more, boolean is_refresh) {
        if (listSubscription != null
                && !listSubscription.isUnsubscribed()) {
            listSubscription.unsubscribe();
        }
        listSubscription = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi())
                .getVacancies()
                .doOnSubscribe(() -> showLoading(true, is_load_more, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_load_more, is_refresh))
                .subscribe(new Subscriber<List<Vacancy>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Vacancy> list) {
                        if (list != null && list.size() > 0) {
                            if (is_refresh) {
                                getViewState().setVacanciesList(list);
                            } else {
                                getViewState().showLoadingIndicator(false);
                                getViewState().addVacanciesList(list);
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
