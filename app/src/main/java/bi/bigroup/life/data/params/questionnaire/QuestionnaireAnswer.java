package bi.bigroup.life.data.params.questionnaire;

import java.util.List;

public class QuestionnaireAnswer {
    public List<String> userAnswerVariantIds;
    public boolean isCommented;
    public String userAnswerCommentText;

    public void setUserAnswerVariantIds(List<String> userAnswerVariantIds) {
        this.userAnswerVariantIds = userAnswerVariantIds;
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public void setUserAnswerCommentText(String userAnswerCommentText) {
        this.userAnswerCommentText = userAnswerCommentText;
    }
}
