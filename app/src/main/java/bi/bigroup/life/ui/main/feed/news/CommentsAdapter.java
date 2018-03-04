package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.utils.GlideUtils;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_DISLIKED;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_LIKED;
import static bi.bigroup.life.utils.Constants.getProfilePicture;

class CommentsAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT = R.layout.adapter_news_comment;
    private Context context;
    private List<Comment> data = new ArrayList<>();
    private Callback callback;

    CommentsAdapter(Context context) {
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addList(List<Comment> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }

    void addNewComment(Comment comment) {
        this.data.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Comment getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(ITEM_LAYOUT, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bindHolder(getItem(position), position);

        return convertView;
    }

    class ViewHolder {
        Context context;
        Comment comment;
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.tv_user_fullname) TextView tv_user_fullname;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.img_like) ImageView img_like;
        @BindView(R.id.tv_like_quantity) TextView tv_like_quantity;

        public ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(Comment object, int position) {
            comment = object;
            tv_user_fullname.setText(object.getFullName());
            tv_time.setText(object.getDate(context));
            tv_content.setText(object.getContent());
            GlideUtils.showAvatar(context, img_avatar, getProfilePicture(object.getCode()), R.drawable.ic_avatar);

            tv_like_quantity.setText(String.valueOf(object.getOkIntQuantity(object.likesQuantity)));
            img_like.setImageResource(object.isLiked() ? R.drawable.like_active
                    : R.drawable.like_inactive);
        }

        @OnClick(R.id.ll_like)
        void onLikeClick() {
            if (callback != null) {
                callback.onCommentLike(comment.getId(), comment.isLiked());
                if (comment.isLiked()) {
                    comment.setVote(VOTE_DISLIKED);
                    comment.setLikesQuantity(comment.getLikesQuantity() - 1);
                } else {
                    comment.setVote(VOTE_LIKED);
                    comment.setLikesQuantity(comment.getLikesQuantity() + 1);
                }
                notifyDataSetChanged();
            }
        }
    }

    public interface Callback {
        void onItemClick(BiOffice biOffice);

        void onCommentLike(String id, boolean liked);
    }
}