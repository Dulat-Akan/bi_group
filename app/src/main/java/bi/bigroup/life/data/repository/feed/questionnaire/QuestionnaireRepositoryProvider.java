package bi.bigroup.life.data.repository.feed.questionnaire;

import android.support.annotation.NonNull;

import bi.bigroup.life.data.network.api.bi_group.API;

public class QuestionnaireRepositoryProvider {
    private static QuestionnaireRepository repository;

    @NonNull
    public static QuestionnaireRepository provideRepository(API api) {
        if (repository == null) {
            repository = new QuestionnaireRepositoryImpl();
        }
        repository.setAPI(api);
        return repository;
    }

    public static void setRepository(QuestionnaireRepository repo) {
        repository = repo;
    }
}