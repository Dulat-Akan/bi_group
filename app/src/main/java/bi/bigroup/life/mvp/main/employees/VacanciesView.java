package bi.bigroup.life.mvp.main.employees;

import java.util.List;

import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface VacanciesView extends BaseSwipeRefreshMvpView {
    void setVacanciesList(List<Vacancy> list);

    void addVacanciesList(List<Vacancy> list);

    void showLoadingItemIndicator(boolean show);
}
