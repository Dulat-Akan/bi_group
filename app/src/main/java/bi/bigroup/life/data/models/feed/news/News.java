package bi.bigroup.life.data.models.feed.news;

import android.content.Context;

import java.util.List;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class News {
    public String id;
    public String title;
    public String imageUrl;
    public String text;
    public String rawText;
    public String createDate;
    public String authorCode;
    public String authorName;
    public Integer commentsQuantity;
    public Integer likesQuantity;
    public Integer viewsQuantity;
    public Boolean isLikedByMe;
    public Boolean isHistoryEvent;
    public Boolean isFromSharepoint;
    public List<String> comments;
    public List<String> secondaryImages;
    public List<String> tags;

    public String getAuthorCode() {
        return replaceNullTrim(authorCode);
    }

    public String getAuthorName() {
        return replaceNull(authorName);
    }

    public String getTitle() {
        return replaceNull(title);
    }

    public String getText() {
        return replaceNull(text);
    }

    public String getImageUrl() {
        return replaceNull(imageUrl);
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

}
