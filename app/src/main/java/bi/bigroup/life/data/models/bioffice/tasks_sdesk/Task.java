package bi.bigroup.life.data.models.bioffice.tasks_sdesk;

import static bi.bigroup.life.utils.DateUtils.getTasksDate;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Task {
    public String id;
    public String topic;
    public String authorCode;
    public String authorName;
    public Boolean isAllDay;
    public String startDate;
    public String endDate;
    public int statusCode;
    public Boolean isExpired;

    public String getId() {
        return replaceNull(id);
    }

    public String getTopic() {
        return replaceNull(topic);
    }

    public String getStartDate() {
        return isStringOk(startDate) ? getTasksDate(startDate) : EMPTY_STR;
    }
}
