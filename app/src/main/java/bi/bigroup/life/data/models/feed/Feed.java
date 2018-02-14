package bi.bigroup.life.data.models.feed;

import java.util.Date;

public class Feed {
    public String id;
    public String authorCode;
    public String authorName;
    public String title;
    public String description;
    public String imageUrl;
    public String createDate;
    public String imageStreamId;
    public Integer likesQuantity;
    public Integer dislikesQuantity;
    public Integer userVote;
    public Integer commentsQuantity;
    public Integer questionsQuantity;
    public Integer viewsQuantity;
    public Boolean isLikedByMe;
    public Boolean isFromSharepoint;
    public FeedEntityType entityType;
}