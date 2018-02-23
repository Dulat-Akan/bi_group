package bi.bigroup.life.mvp.notifications;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.data.repository.notifications.NotificationsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Subscriber;
import rx.Subscription;

@InjectViewState
public class NotificationsPresenter extends BaseMvpPresenter<NotificationsView> {

    public void getNotifications(boolean is_refresh) {
        Subscription subscription = NotificationsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getNotifications()
                .doOnSubscribe(() -> showLoading(true, is_refresh))
                .doOnTerminate(() -> showLoading(false, is_refresh))
                .subscribe(new Subscriber<List<Notification>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<Notification> notificationList) {
                        if (notificationList != null && notificationList.size() > 0) {
                            if (is_refresh) {
                                getViewState().setNotificationsList(notificationList);
                            } else {
                                getViewState().addNotificationsList(notificationList);
                            }
                        } else {
                            getViewState().showNotFoundPlaceholder();
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    private void showLoading(boolean show, boolean is_refresh) {
        if (!is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}
