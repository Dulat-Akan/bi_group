package bi.bigroup.life.data.repository.feed;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class FeedRepositoryProvider {
    private static FeedRepository repository;

    @NonNull
    public static FeedRepository provideRepository(API api) {
        if (repository == null) {
            repository = new FeedRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(FeedRepository repo) {
        repository = repo;
    }
}