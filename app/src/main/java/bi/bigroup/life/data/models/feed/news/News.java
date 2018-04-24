package bi.bigroup.life.data.models.feed.news;

import java.util.List;

import static bi.bigroup.life.utils.DateUtils.getNewsDate;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
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
    public Boolean isPressService;
    public Boolean isPublishedAsGroup;
    public List<Comment> comments;
    public List<SecondaryImage> secondaryImages;
    public List<Tags> tags;

    public String getId() {
        return replaceNull(id);
    }

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

    public String getDate() {
        return getNewsDate(createDate);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getOkIntQuantity(Integer sum) {
        return getOkInt(sum);
    }

    public boolean isLiked() {
        return isOkBoolean(isLikedByMe);
    }

    public boolean isPublishedAsGroup() {
        return isOkBoolean(isPublishedAsGroup);
    }

    public boolean isPressService() {
        return isOkBoolean(isPressService);
    }

    public int getLikesQuantity() {
        return getOkInt(likesQuantity);
    }

    public void setLikedByMe(Boolean likedByMe) {
        isLikedByMe = likedByMe;
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }


}
