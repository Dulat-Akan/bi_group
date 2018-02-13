package bi.bigroup.life.mvp.notifications;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class NotificationsPresenter extends BaseMvpPresenter<NotificationsView> {

    public void getNotifications(boolean is_load_more, boolean is_refresh) {
        if (is_load_more)
            return;

//        Subscription subscription = NotificationsRepositoryProvider.provideRepository(dataLayer.getApi())
//                .getAreasList(
//                        is_load_more ? current_page + 1 : INITIAL_PAGE_NUMBER,
//                        REQUEST_COUNT)
//                .doOnSubscribe(() -> showLoading(true, is_load_more, is_refresh))
//                .doOnTerminate(() -> showLoading(false, is_load_more, is_refresh))
//                .subscribe(new Subscriber<List<Notification>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        handleResponseError(context, e);
//                    }
//
//                    @Override
//                    public void onNext(List<Notification> notificationList) {
//                        if (notificationList != null && notificationList.size() > 0) {
//                            if (is_refresh) {
//                                getViewState().setNotificationsList(notificationList);
//                            } else {
//                                getViewState().addNotificationsList(notificationList);
//                            }
//                        } else if (!is_load_more) {
//                            getViewState().showNotFoundPlaceholder();
//                        }
//                    }
//                });
//
//        compositeSubscription.add(subscription);
    }

    private void showLoading(boolean show, boolean is_load_more, boolean is_refresh) {
        if (!is_load_more && !is_refresh) {
            getViewState().showLoadingIndicator(show);
        } else if (is_load_more) {
            getViewState().showLoadingItemIndicator(show);
        } else {
            getViewState().showRefreshLoading(show);
        }
    }
}
