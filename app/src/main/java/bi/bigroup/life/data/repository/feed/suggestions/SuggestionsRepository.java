package bi.bigroup.life.data.repository.feed.suggestions;

import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;

public interface SuggestionsRepository {

    void setAPI(API api);

    Observable<Suggestion> getSuggestion(String id);

    Observable<ResponseBody> likeSuggestion(String id, int voteType);

    Observable<Comment> addComment(String id, AddComment addComment);

    Observable<ResponseBody> likeSuggestionsComment(String id, String commentId, int vote);
}