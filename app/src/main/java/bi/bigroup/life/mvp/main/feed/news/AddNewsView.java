package bi.bigroup.life.mvp.main.feed.news;

import bi.bigroup.life.mvp.BaseMvpView;

public interface AddNewsView extends BaseMvpView {
    void showRequestPermissionDialog(boolean isRequestPermission);

    void selectMultipleImages();
}
