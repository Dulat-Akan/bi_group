package bi.bigroup.life.data.repository.feed.questionnaire;

import java.util.List;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.questionnaire.QuestionnaireAnswer;
import okhttp3.ResponseBody;
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
    public Observable<Questionnaire> getQuestionnaire(String id) {
        return api
                .getQuestionnaire(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

    @Override
    public Observable<Questionnaire> getQuestStatistics(String id) {
        return api
                .getQuestStatistics(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> questionnaireUserAnswers(String id, String questionId, QuestionnaireAnswer dto) {
        return api
                .questionnaireUserAnswers(id, questionId, dto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}