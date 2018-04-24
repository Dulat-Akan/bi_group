package bi.bigroup.life.data.models.feed.questionnaire;

import android.content.Context;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.SecondaryImage;

import static bi.bigroup.life.utils.Constants.buildStreamUrl;
import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class Questionnaire {
    public String id;
    public String title;
    public String name;
    public String createDate;
    public String endDate;
    public String imageStreamId;
    public String authorCode;
    public String authorName;
    public Boolean isPublishedAsGroup;
    public Boolean isAnonymous;
    public String description;
    public Boolean isEditingAnswersAllowed;
    public Boolean isStatisticsDetailsVisible;
    public Boolean isViewingAnswersAllowed;
    public Boolean isStatisticsVisible;
    public Boolean isAuthorVisible;
    public Boolean isAvailableByLink;
    public Boolean isCurrentUserInterviewed;
    public Boolean isReturnToPreviousQuestionAllowed;
    public Boolean isInterruptingAllowed;
    public Boolean isDraft;
    public String adGroupName;
    public AdGroup adGroup;
    public Integer type;
    public String typeDisplayName;
    public List<Respondents> respondents;
    public List<SecondaryImage> secondaryImages;
    public List<Question> questions;

    public String getImageUrl() {
        return isStringOk(imageStreamId) ? buildStreamUrl(imageStreamId) : EMPTY_STR;
    }

    public boolean isAuthorVisible() {
        return isOkBoolean(isAuthorVisible);
    }

    public boolean isAnonymous() {
        return isOkBoolean(isAnonymous);
    }

    public boolean isCurrentUserInterviewed() {
        return isOkBoolean(isCurrentUserInterviewed);
    }

    public boolean isEditingAnswersAllowed() {
        return isOkBoolean(isEditingAnswersAllowed);
    }

    public String getId() {
        return replaceNull(id);
    }

    public String getTitle() {
        return replaceNull(title);
    }

    public String getDescription() {
        return replaceNull(description);
    }

    public String getAuthorName() {
        return replaceNull(authorName);
    }

    public int getQuestionsQuantity() {
        return (questions != null && questions.size() > 0) ? questions.size() : 0;
    }

    public int getRespondentsQuantity() {
        return (respondents != null && respondents.size() > 0) ? respondents.size() : 0;
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

    public String getAuthorCode() {
        return replaceNullTrim(authorCode);
    }

    public class Respondents {
        public String code;
        public String fullName;
    }
}
