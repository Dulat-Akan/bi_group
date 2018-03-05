package bi.bigroup.life.data.models.feed.news;

public class AddComment {
    public String commentText;
    public Boolean isPositiveComment;

    public AddComment(String commentText) {
        this.commentText = commentText;
    }

    public AddComment(String commentText, Boolean isPositiveComment) {
        this.commentText = commentText;
        this.isPositiveComment = isPositiveComment;
    }
}
