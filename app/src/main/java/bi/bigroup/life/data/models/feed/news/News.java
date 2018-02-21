package bi.bigroup.life.data.models.feed.news;

import java.util.List;

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
}
