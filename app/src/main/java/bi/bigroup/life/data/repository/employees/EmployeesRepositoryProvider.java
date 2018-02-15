package bi.bigroup.life.data.repository.employees;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class EmployeesRepositoryProvider {
    private static EmployeesRepository repository;

    @NonNull
    public static EmployeesRepository provideRepository(API api) {
        if (repository == null) {
            repository = new EmployeesRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(EmployeesRepository repo) {
        repository = repo;
    }
}