package bi.bigroup.life.data.repository.biboard.top_questions;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class TopQuestionsRepositoryProvider {
    private static TopQuestionsRepository repository;

    @NonNull
    public static TopQuestionsRepository provideRepository(API api) {
        if (repository == null) {
            repository = new TopQuestionsRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(TopQuestionsRepository repo) {
        repository = repo;
    }
}