package bi.bigroup.life.data.repository.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class TopQuestionsRepositoryImpl implements TopQuestionsRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<List<TopQuestions>> getTopQuestions() {
        return api
                .getTopQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> addQuestion(AddQuestionParams params) {
        return api
                .addQuestion(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Tags>> getTags() {
        return api
                .getNewsTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}