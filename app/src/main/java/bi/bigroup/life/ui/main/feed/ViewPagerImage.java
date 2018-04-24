package bi.bigroup.life.ui.main.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPagerImage extends PagerAdapter {
    private Context context;
    private Picasso picasso;
    private Callback callback;

    private List<String> sliders = new ArrayList<>();

    public ViewPagerImage(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addImages(List<String> newSliders) {
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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View itemView = inflater.inflate(R.layout.adapter_vp_news_slider, container, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.bindNews(sliders.get(position), position);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    class ViewHolder {
        Context context;
        String sliderImg;
        int position;
        @BindView(R.id.img_slider) ImageView img_slider;

        ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindNews(String sliderImg, int position) {
            this.sliderImg = sliderImg;
            this.position = position;
            PicassoUtils.showNewsImage(picasso, img_slider, sliderImg);
        }

        @OnClick(R.id.img_slider)
        void onImageClick() {
            if (callback != null) {
                callback.onImageClick(position);
            }
        }
    }

    public interface Callback {
        void onImageClick(int position);
    }
}

