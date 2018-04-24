package bi.bigroup.life.mvp.main.feed.questionnaires;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.mvp.BaseMvpView;

public interface QuestionnaireDetailView extends BaseMvpView {
    void showTransparentIndicator(boolean show);

    void setQuestionnaire(Questionnaire object);
}