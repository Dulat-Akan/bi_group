package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.BaseMvpView;

public interface SearchUserView extends BaseMvpView {
    void setSearchResults(List<Employee> searchResults);
}
