package bi.bigroup.life.data.models.feed.questionnaire;

import java.util.List;

import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Question {
    public String id;
    public String text;
    public String questionText;
    public Integer minAnswersQuantity;
    public Integer maxAnswersQuantity;
    public Boolean canComment;
    public Boolean isOpen;
    public Boolean hasUserAnswer;
    public Boolean isDeleted;
    public List<Variants> variants;
    public List<String> comments;
    public List<String> userAnswer;
    public String userComment;
    public Boolean shouldComment;
    public Integer totalAnswers;
    public Integer totalVotes;
    public transient boolean isCommented;

    public String getId() {
        return replaceNull(id);
    }

    public String getQuestionText() {
        return replaceNull(questionText);
    }

    public String getText() {
        return replaceNull(text);
    }

    public String getUserComment() {
        return replaceNull(userComment);
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public boolean isCommented() {
        return isOkBoolean(isCommented);
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public int getUserVote() {
        return getOkInt(totalVotes);
    }

    public int getTotalAnswers() {
        return getOkInt(totalAnswers);
    }

    public boolean userCanComment() {
        return isOkBoolean(canComment);
    }

    public boolean hasUserAnswer() {
        return isOkBoolean(hasUserAnswer);
    }
}
