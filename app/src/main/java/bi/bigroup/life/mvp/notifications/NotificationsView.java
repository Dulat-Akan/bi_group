package bi.bigroup.life.mvp.notifications;

import java.util.List;

import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface NotificationsView extends BaseSwipeRefreshMvpView {

    void setNotificationsList(List<Notification> list);

    void addNotificationsList(List<Notification> list);
}
