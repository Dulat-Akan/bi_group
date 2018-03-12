package bi.bigroup.life.data.repository.extra;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class ExtraRepositoryProvider {
    private static ExtraRepository repository;

    @NonNull
    public static ExtraRepository provideRepository(API api) {
        if (repository == null) {
            repository = new ExtraRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(ExtraRepository repo) {
        repository = repo;
    }
}