package bi.bigroup.life.ui.main.bioffice;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.HotOffice;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotOfficeViewPager extends PagerAdapter {
    private Context context;

    private List<HotOffice> sliders = new ArrayList<>();

    public HotOfficeViewPager(Context context) {
        this.context = context;
    }

    public void addList(List<HotOffice> newSliders) {
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
        View itemView = inflater.inflate(R.layout.adapter_bioffice_view_pager, container, false);
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

        void bindNews(HotOffice office) {
//            GlideUtils.showImg(context, img_slider, sliderImg);
        }
    }
}

