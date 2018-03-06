package bi.bigroup.life.data.repository.feed.questionnaire;

import java.util.List;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class QuestionnaireRepositoryImpl implements QuestionnaireRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<Questionnaire>> getPopularQuestionnaires() {
        return api
                .getPopularQuestionnaires()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Questionnaire>> getAllQuestionnaire() {
        return api
                .getAllQuestionnaires()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}