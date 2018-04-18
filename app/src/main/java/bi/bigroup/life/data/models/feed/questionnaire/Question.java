package bi.bigroup.life.data.models.feed.questionnaire;

import java.util.List;

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
    public List<String> userAnswer;
    public String userComment;
    public Boolean shouldComment;
    public Integer totalAnswers;
    public Integer totalVotes;
}
