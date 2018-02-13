package bi.bigroup.life.ui.profile;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bi.bigroup.life.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RowViewHolder {
    private ViewGroup rowView;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.img_icon) ImageView img_icon;
    private Callback callback;

    RowViewHolder(View view) {
        ButterKnife.bind(this, view);
        rowView = (ViewGroup) view;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void bindHolder(@StringRes int titleRes, @DrawableRes int icon) {
        if (titleRes == 0 && icon == 0) {
            rowView.setVisibility(View.GONE);
            tv_title.setText(null);
        } else {
            rowView.setVisibility(View.VISIBLE);
            tv_title.setText(titleRes);
            img_icon.setImageResource(icon);
        }
    }

    @OnClick(R.id.ll_row)
    void onRowClick() {
        if (callback != null) {
            callback.onRowClick();
        }
    }

    public interface Callback {
        void onRowClick();
    }
}
