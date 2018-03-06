package bi.bigroup.life.data.models.feed.questionnaire;

import android.content.Context;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Questionnaire {
    public String id;
    public String title;
    public String createDate;
    public String imageStreamId;

    public String getTitle() {
        return replaceNull(title);
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

}
