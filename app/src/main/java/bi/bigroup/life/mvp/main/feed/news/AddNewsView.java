package bi.bigroup.life.mvp.main.feed.news;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.mvp.BaseMvpView;

public interface AddNewsView extends BaseMvpView {
    void showRequestPermissionDialog(boolean isRequestPermission);

    void selectMultipleImages();

    void setNewsTags(List<Tags> object);

    void showTitleError(int error);

    void showContentError(int error);

    void showTransparentIndicator(boolean show);

    void showToastError(int please_select_tag);

    void newsAddedSuccessfully();
}
