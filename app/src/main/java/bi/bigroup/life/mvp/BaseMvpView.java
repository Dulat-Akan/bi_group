package bi.bigroup.life.mvp;

import com.arellomobile.mvp.MvpView;

public interface BaseMvpView extends MvpView {
    void showRequestError(String errorMessage);

    void showLoadingIndicator(boolean show);

    void onInvalidToken();

    void showNotFoundPlaceholder();

    void showRequestSuccess(int message);

}
