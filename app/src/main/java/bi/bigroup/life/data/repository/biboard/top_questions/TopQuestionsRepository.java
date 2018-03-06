package bi.bigroup.life.data.repository.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface TopQuestionsRepository {

    void setAPI(API api);

    Observable<List<TopQuestions>> getTopQuestions();
}