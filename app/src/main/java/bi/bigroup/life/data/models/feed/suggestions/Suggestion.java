package bi.bigroup.life.data.models.feed.suggestions;

import android.content.Context;

import java.util.List;

import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.SecondaryImage;
import bi.bigroup.life.data.models.feed.news.Tags;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class Suggestion {
    public String id;
    public String title;
    public String text;
    public String imageStreamId;
    public String createDate;
    public String authorCode;
    public String authorName;
    public Boolean canEdit;
    public Integer commentsQuantity;
    public Integer likesQuantity;
    public Integer dislikesQuantity;
    public Integer userVote;
    public Integer viewsQuantity;
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
        return replaceNull(imageStreamId);
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getOkIntQuantity(Integer sum) {
        return getOkInt(sum);
    }

    public int getLikesQuantity() {
        return getOkInt(likesQuantity);
    }

    public int getDisLikesQuantity() {
        return getOkInt(dislikesQuantity);
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }

    public void setDisLikesQuantity(int dislikesQuantity) {
        this.dislikesQuantity = dislikesQuantity;
    }

    public void setUserVote(int userVote) {
        this.userVote = userVote;
    }

    public int getUserVote() {
        return getOkInt(userVote);
    }

}
