package bi.bigroup.life.ui.main.biboard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class HotBoardViewPager extends PagerAdapter {
    private Context context;

    private List<News> sliders = new ArrayList<>();
    private Picasso picasso;
    private Callback callback;

    HotBoardViewPager(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addList(List<News> newSliders) {
        if (newSliders == null || newSliders.isEmpty()) {
            sliders = new ArrayList<>();
        } else {
            sliders = new ArrayList<>(newSliders);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_biboard_view_pager, container, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.bindNews(sliders.get(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    class ViewHolder {
        Context context;
        @BindView(R.id.img_slider) ImageView img_slider;
        @BindView(R.id.tv_title) TextView tv_title;

        ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindNews(News news) {
            if (news == null) {
                return;
            }
            tv_title.setText(news.getTitle());
            PicassoUtils.showNewsImage(picasso, img_slider, news.getImageUrl());
        }

        @OnClick(R.id.img_slider)
        void onImgSliderClick() {
            if (callback != null) {
                callback.onNewsClick();
            }
        }
    }


    public interface Callback {
        void onNewsClick();
    }
}

