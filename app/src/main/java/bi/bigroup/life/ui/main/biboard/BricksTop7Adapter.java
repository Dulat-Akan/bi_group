package bi.bigroup.life.ui.main.biboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.getProfilePicture;

class BricksTop7Adapter extends RecyclerViewBaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_biboard_bricks_top7;

    private Context context;
    private Picasso picasso;
    private List<TopQuestions> data;
    private Callback callback;

    BricksTop7Adapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
        this.data = new ArrayList<>();
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addList(List<TopQuestions> newItems) {
        data.clear();
        data.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_ID:
                return new BViewHolder(inflate(parent, LAYOUT_ID));
            default:
                throw incorrectOnCreateViewHolder();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return LAYOUT_ID;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(data.get(position), position);
                break;
        }
    }

    class BViewHolder extends MainViewHolder {
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;

        TopQuestions bindQuestion;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(TopQuestions topQuestion, int position) {
            bindQuestion = topQuestion;
            bindedPosition = position;
            if (topQuestion == null) {
                return;
            }
            PicassoUtils.showAvatar(picasso, img_avatar, getProfilePicture(topQuestion.getCode()), R.drawable.ic_avatar);
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindQuestion);
            }
        }
    }

    interface Callback {
        void onItemClick(TopQuestions notification);
    }
}
