package bi.bigroup.life.data.cache.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import bi.bigroup.life.data.models.employees.Employee;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface EmployeeDao {
    @Query("select * from employee")
    List<Employee> loadAllEmployees();

    @Query("select * from employee where code = :code")
    Employee loadUserByCode(String code);

    @Insert(onConflict = IGNORE)
    void insertEmployee(Employee employee);

    @Insert
    void insertMultipleEmployees(List<Employee> employeeList);

    @Delete
    void deleteEmployee(Employee employee);

//    @Query("delete from user where name like :badName OR lastName like :badName")
//    int deleteUsersByName(String badName);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceEmployees(Employee... employees);

    @Delete
    void deleteUsers(Employee employee1, Employee employee2);

    @Query("DELETE FROM employee")
    void deleteAllEmployees();
}
