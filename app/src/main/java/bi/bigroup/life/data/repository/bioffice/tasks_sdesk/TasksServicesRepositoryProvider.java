package bi.bigroup.life.data.repository.bioffice.tasks_sdesk;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class TasksServicesRepositoryProvider {
    private static TasksServicesRepository repository;

    @NonNull
    public static TasksServicesRepository provideRepository(API api) {
        if (repository == null) {
            repository = new TasksServicesRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(TasksServicesRepository repo) {
        repository = repo;
    }
}