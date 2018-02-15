package bi.bigroup.life.data.repository.employees;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface EmployeesRepository {

    void setAPI(API api);

    Observable<List<Employee>> getEmployees(int rows, int offset, Boolean withDescription);
}