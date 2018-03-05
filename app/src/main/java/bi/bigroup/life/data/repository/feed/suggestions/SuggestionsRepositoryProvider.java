package bi.bigroup.life.data.repository.feed.suggestions;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class SuggestionsRepositoryProvider {
    private static SuggestionsRepository repository;

    @NonNull
    public static SuggestionsRepository provideRepository(API api) {
        if (repository == null) {
            repository = new SuggestionsRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(SuggestionsRepository repo) {
        repository = repo;
    }
}