package bi.bigroup.life.mvp.main.biboard.top_questions;

import bi.bigroup.life.mvp.BaseMvpView;

public interface AddQuestionView extends BaseMvpView {
    void showTagsError(int field_error);

    void showContentError(int field_error);

}
