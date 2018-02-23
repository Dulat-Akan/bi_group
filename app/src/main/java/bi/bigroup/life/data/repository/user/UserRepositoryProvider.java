package bi.bigroup.life.data.repository.user;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class UserRepositoryProvider {
    private static UserRepository repository;

    @NonNull
    public static UserRepository provideRepository(API api) {
        if (repository == null) {
            repository = new UserRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(UserRepository repo) {
        repository = repo;
    }
}