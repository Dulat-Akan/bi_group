package bi.bigroup.life.data.repository.feed.suggestions;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class SuggestionsRepositoryImpl implements SuggestionsRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<Suggestion> getSuggestion(String id) {
        return api
                .getSuggestion(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> likeSuggestion(String id, int voteType) {
        return api
                .likeSuggestion(id, voteType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Comment> addComment(String id, AddComment addComment) {
        return api
                .addSuggestionsComment(id, addComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> likeSuggestionsComment(String id, String commentId, int vote) {
        return api
                .likeSuggestionComment(id, commentId, vote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Suggestion>> getPopularSuggestions() {
        return api
                .getPopularSuggestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Suggestion>> getAllSuggestions() {
        return api
                .getAllSuggestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}