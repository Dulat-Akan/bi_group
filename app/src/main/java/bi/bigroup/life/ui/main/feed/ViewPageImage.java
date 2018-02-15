package bi.bigroup.life.ui.main.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.utils.GlideUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

class ViewPagerImage extends PagerAdapter {
    private Context context;

    private List<String> sliders = new ArrayList<>();

    public ViewPagerImage(Context context) {
        this.context = context;
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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adapter_vp_news_slider, container, false);
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

        ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindNews(String sliderImg) {
            GlideUtils.showImg(context, img_slider, sliderImg);
        }
    }
}

