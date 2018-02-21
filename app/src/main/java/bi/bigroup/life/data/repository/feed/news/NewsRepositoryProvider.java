package bi.bigroup.life.data.repository.feed.news;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class NewsRepositoryProvider {
    private static NewsRepository repository;

    @NonNull
    public static NewsRepository provideRepository(API api) {
        if (repository == null) {
            repository = new NewsRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(NewsRepository repo) {
        repository = repo;
    }
}