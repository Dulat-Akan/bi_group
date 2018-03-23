package bi.bigroup.life.ui.main.menu;

import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bi.bigroup.life.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RowViewHolder {
    private ViewGroup rowView;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_description) TextView tv_description;
    @BindView(R.id.separator) View separator;
    private Callback callback;
    private boolean hideLastSeparator;

    RowViewHolder(View view, boolean hideLastSeparator) {
        ButterKnife.bind(this, view);
        this.hideLastSeparator = hideLastSeparator;
        rowView = (ViewGroup) view;
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void bindHolder(@StringRes int titleRes, @StringRes int descriptionRes) {
        if (titleRes == 0 && descriptionRes == 0) {
            rowView.setVisibility(View.GONE);
            tv_title.setText(null);
            tv_description.setText(null);
        } else {
            rowView.setVisibility(View.VISIBLE);
            tv_title.setText(titleRes);
            tv_description.setText(descriptionRes);
        }
        separator.setVisibility(hideLastSeparator ? View.GONE : View.VISIBLE);
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
