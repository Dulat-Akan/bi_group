package bi.bigroup.life.data.models.bioffice.top_questions;

import java.util.List;

public class AddQuestionParams {
    public String questionText;
    public Boolean isAnonymous;
    public List<String> tags;

    public AddQuestionParams(String questionText, Boolean isAnonymous, List<String> tags) {
        this.questionText = questionText;
        this.isAnonymous = isAnonymous;
        this.tags = tags;
    }
}
