package bi.bigroup.life.data.models.biboard.top_questions;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.Tags;

import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class TopQuestions {
    public String id;
    public String createDate;
    public String text;
    public String authorCode;
    public String authorName;
    public Integer likesQuantity;
    public Boolean isLikedByMe;
    public Boolean canAnswer;
    public List<Tags> tags;
//            "answers":[],

    public String getId() {
        return replaceNull(id);
    }

    public String getCode() {
        return replaceNullTrim(authorCode);
    }

    public String getAuthorName() {
        return replaceNull(authorName);
    }

    public Integer getLikesQuantity() {
        return getOkInt(likesQuantity);
    }
}
