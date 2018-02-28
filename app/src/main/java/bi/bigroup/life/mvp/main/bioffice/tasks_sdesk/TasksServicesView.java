package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface TasksServicesView extends BaseSwipeRefreshMvpView {
    void setEmployeesList(List<Employee> list);

    void addEmployeesList(List<Employee> list);

    void showLoadingItemIndicator(boolean show);
}
