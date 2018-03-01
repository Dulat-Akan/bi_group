package bi.bigroup.life.data.models.bioffice.tasks_sdesk;

import static bi.bigroup.life.utils.DateUtils.getServicesDate;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Service {
    public String surname;
    public String name;
    public String task_number;
    public String registration_date;
    public String dateofdelivery;
    public String task_status;
    public String short_description;
    public String customer;
    public String comment;
    public String fullname;
    public String topic;
    public String executorName;
    public String authorName;
    public Boolean isExpired;
    public Boolean isRequest;
    public String endDate;
    public Integer statusCode;

    public String getTopic() {
        return replaceNull(topic);
    }

    public String getStatus() {
        return replaceNull(task_status);
    }

    public String getTaskNumber() {
        return replaceNull(task_number);
    }

    public String getStartDate() {
        return isStringOk(registration_date) ? getServicesDate(registration_date) : EMPTY_STR;
    }
}
