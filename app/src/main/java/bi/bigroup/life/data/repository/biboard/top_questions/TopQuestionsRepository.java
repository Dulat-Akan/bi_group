package bi.bigroup.life.data.repository.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.biboard.top_questions.TopVideoAnswers;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;

public interface TopQuestionsRepository {

    void setAPI(API api);

    Observable<List<TopQuestions>> getTopQuestions();

    Observable<List<TopVideoAnswers>> getTopVideoAnswers();

    Observable<ResponseBody> addQuestion(AddQuestionParams params);

    Observable<List<Tags>> getTags();

    Observable<ResponseBody> likeTopQuestion(String id);
}