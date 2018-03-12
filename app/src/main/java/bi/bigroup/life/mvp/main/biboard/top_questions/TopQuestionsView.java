package bi.bigroup.life.mvp.main.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.mvp.BaseMvpView;

public interface TopQuestionsView extends BaseMvpView {
    void setTopQuestions(List<TopQuestions> list);
}
