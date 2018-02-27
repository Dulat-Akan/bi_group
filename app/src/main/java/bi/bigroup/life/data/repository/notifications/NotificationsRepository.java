package bi.bigroup.life.data.repository.notifications;

import java.util.List;

import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.ResponseBody;
import rx.Observable;

public interface NotificationsRepository {

    void setAPI(API api);

    Observable<List<Notification>> getNotifications();

    Observable<ResponseBody> removeNotification(String id);
}