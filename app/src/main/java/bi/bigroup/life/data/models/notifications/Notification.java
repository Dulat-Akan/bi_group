package bi.bigroup.life.data.models.notifications;

import android.content.Context;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Notification {
    public String id;
    public String message;
    public Integer eventType;
    public String authorName;
    public String createDate;
    public String entityId;

    public String getMessage() {
        return replaceNull(message);
    }

    public String getCreateDate(Context context) {
        return getDisplayableTime(createDate, context);
    }
}
