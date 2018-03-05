package bi.bigroup.life.mvp.main.feed.suggestion;

import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.mvp.BaseMvpView;

public interface SuggestionDetailView extends BaseMvpView {
    void setSuggestion(Suggestion object);

    void showTransparentIndicator(boolean show);

    void addNewComment(Comment comment);

}