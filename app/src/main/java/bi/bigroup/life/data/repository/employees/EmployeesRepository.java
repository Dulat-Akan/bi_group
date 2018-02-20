package bi.bigroup.life.data.repository.employees;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface EmployeesRepository {

    void setAPI(API api);

    Observable<ListOf<Employee>> getEmployees(int rows, int offset, Boolean IsBirthdayToday);

    Observable<Employee> getEmployee(String code);
}