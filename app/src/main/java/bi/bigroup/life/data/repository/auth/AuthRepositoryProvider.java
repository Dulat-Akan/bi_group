package bi.bigroup.life.data.repository.auth;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class AuthRepositoryProvider {
    private static AuthRepository repository;

    @NonNull
    public static AuthRepository provideRepository(API api) {
        if (repository == null) {
            repository = new AuthRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(AuthRepository repo) {
        repository = repo;
    }
}