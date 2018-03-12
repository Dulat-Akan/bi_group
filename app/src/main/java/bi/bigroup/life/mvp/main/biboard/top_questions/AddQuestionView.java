package bi.bigroup.life.mvp.main.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.mvp.BaseMvpView;

public interface AddQuestionView extends BaseMvpView {
    void setTags(List<Tags> object);

    void showContentError(int error);

    void showTransparentIndicator(boolean show);

    void showToastError(int please_select_tag);

    void questionAddedSuccessfully();

}
