package bi.bigroup.life.data.repository.notifications;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class NotificationsRepositoryProvider {
    private static NotificationsRepository repository;

    @NonNull
    public static NotificationsRepository provideRepository(API api) {
        if (repository == null) {
            repository = new NotificationsRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(NotificationsRepository repo) {
        repository = repo;
    }
}