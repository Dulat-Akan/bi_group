package bi.bigroup.life.data.repository.feed.questionnaire;

import java.util.List;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface QuestionnaireRepository {

    void setAPI(API api);

    Observable<Questionnaire> getQuestionnaire(String id);

    Observable<List<Questionnaire>> getPopularQuestionnaires();

    Observable<List<Questionnaire>> getAllQuestionnaire();
}