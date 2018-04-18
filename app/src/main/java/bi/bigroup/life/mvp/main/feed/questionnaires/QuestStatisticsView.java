package bi.bigroup.life.mvp.main.feed.questionnaires;

import java.util.List;

import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface QuestStatisticsView extends BaseSwipeRefreshMvpView {

    void setQuestionnaireList(List<Questionnaire> list);

    void addQuestionnaireList(List<Questionnaire> list);
}
