package bi.bigroup.life.mvp.main.employees;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.BaseMvpView;

public interface EmployeePageView extends BaseMvpView {
    void setEmployee(Employee employee);
}
