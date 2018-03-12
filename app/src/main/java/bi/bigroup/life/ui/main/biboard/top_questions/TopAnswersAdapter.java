package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.top_questions.TopVideoAnswers;
import bi.bigroup.life.data.models.user.advantages.BricksGallery;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import butterknife.ButterKnife;
import butterknife.OnClick;

class TopAnswersAdapter extends RecyclerViewBaseAdapter {
    private static final int HEADER_LAYOUT_ID = R.layout.adapter_topq_answers_video;
    private static final int LAYOUT_ID = R.layout.adapter_topq_answers;

    private Context context;
    private List<TopVideoAnswers> data;
    private Callback callback;

    TopAnswersAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    void addList(List<TopVideoAnswers> newItems) {
        int positionStart = data.size();
        int itemCount = newItems.size();
        data.addAll(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_LAYOUT_ID:
                return new BViewHeaderHolder(inflate(parent, HEADER_LAYOUT_ID));
            case LAYOUT_ID:
                return new BViewHolder(inflate(parent, LAYOUT_ID));
            default:
                throw incorrectOnCreateViewHolder();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_LAYOUT_ID;
        } else {
            return LAYOUT_ID;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(position);//data.get(position)
                break;
        }
    }

    class BViewHeaderHolder extends MainViewHolder {
//        @BindView(R.id.tv_title) TextView tv_title;

        BricksGallery bindedObject;
        int bindedPosition;

        BViewHeaderHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(int position) {
//            bindedObject = bricksGallery;
            bindedPosition = position;
//            if (bricksGallery == null) {
//                return;
//            }
//            tv_title.setText(bricksGallery.title);
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindedObject);
            }
        }
    }

    class BViewHolder extends MainViewHolder {
//        @BindView(R.id.tv_title) TextView tv_title;

        BricksGallery bindedObject;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(int position) {
//            bindedObject = bricksGallery;
            bindedPosition = position;
//            if (bricksGallery == null) {
//                return;
//            }
//            tv_title.setText(bricksGallery.title);
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindedObject);
            }
        }
    }

    interface Callback {
        void onItemClick(BricksGallery notification);
    }
}
