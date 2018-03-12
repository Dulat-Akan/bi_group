package bi.bigroup.life.data.models.bioffice.tasks_sdesk;

import bi.bigroup.life.R;

import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_APPROVED;
import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_CONFIRMED;
import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_EXECUTED;
import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_IN_PROGRESS;
import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_NEW;
import static bi.bigroup.life.data.models.bioffice.tasks_sdesk.TaskEnums.TASK_CODE_REJECT;
import static bi.bigroup.life.utils.DateUtils.getTasksDate;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
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
    public Integer statusCode;
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

    public int getStatusCode() {
        int code = getOkInt(statusCode);
        if (code == TASK_CODE_NEW) {
            return R.string.task_code_0;
        } else if (code == TASK_CODE_IN_PROGRESS) {
            return R.string.task_code_10;
        } else if (code == TASK_CODE_EXECUTED) {
            return R.string.task_code_20;
        } else if (code == TASK_CODE_CONFIRMED) {
            return R.string.task_code_50;
        } else if (code == TASK_CODE_APPROVED) {
            return R.string.task_code_55;
        } else if (code == TASK_CODE_REJECT) {
            return R.string.task_code_60;
        } else {
            return R.string.empty_str;
        }
    }
}
