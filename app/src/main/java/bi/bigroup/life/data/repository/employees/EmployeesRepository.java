package bi.bigroup.life.data.repository.employees;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.employees.DobCongrats;
import okhttp3.ResponseBody;
import rx.Observable;

public interface EmployeesRepository {

    void setAPI(API api);

    Observable<ListOf<Employee>> getEmployees(int rows, int offset, Boolean IsBirthdayToday);

    Observable<List<Employee>> getEmployeesEvents();

    Observable<List<Employee>> searchEmployees(String filterText, String top);

    Observable<List<Vacancy>> getVacancies();

    Observable<Employee> getEmployee(String code);

    Observable<ResponseBody> sendCongrats(String code, DobCongrats params);
}