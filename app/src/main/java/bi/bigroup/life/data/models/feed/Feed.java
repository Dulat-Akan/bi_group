package bi.bigroup.life.data.models.feed;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static bi.bigroup.life.utils.DateUtils.getNewsDate;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

@Entity
public class Feed {
    @PrimaryKey
    @NonNull
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
    public Boolean isPressService;
    public FeedEntityType entityType;
    public ImageSize imageSize;

    public boolean isLiked() {
        return isOkBoolean(isLikedByMe);
    }

    public boolean isPressService() {
        return isOkBoolean(isPressService);
    }

    public int getLikesQuantity() {
        return getOkInt(likesQuantity);
    }

    public int getDisLikesQuantity() {
        return getOkInt(dislikesQuantity);
    }

    public void setLikedByMe(Boolean likedByMe) {
        isLikedByMe = likedByMe;
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

    public int getLayoutType() {
        if (entityType != null) {
            return getOkInt(entityType.code);
        } else {
            return 0;
        }
    }

    public void setLayoutType(int code) {
        if (entityType != null) {
            entityType.setCode(code);
        } else {
            entityType = new FeedEntityType(code);
        }
    }

    public String getId() {
        return replaceNull(id);
    }

    public String getImageUrl() {
        return replaceNull(imageUrl);
    }

    public int getOkIntQuantity(Integer sum) {
        return getOkInt(sum);
    }

    public String getDate() {
        return getNewsDate(createDate);
    }

    public String getCode() {
        return replaceNullTrim(authorCode);
    }

    public class ImageSize {
        public Integer width;
        public Integer height;

        public int getWidth() {
            return getOkInt(width);
        }

        public int getHeight() {
            return getOkInt(height);
        }
    }
}