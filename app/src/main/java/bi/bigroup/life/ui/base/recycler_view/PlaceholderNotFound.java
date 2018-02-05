package bi.bigroup.life.ui.base.recycler_view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bi.bigroup.life.R;
import bi.bigroup.life.utils.recycler_view.SwipeRefreshUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class PlaceholderNotFound {
    private View view;
    @BindView(R.id.v_empty_view) LinearLayout v_empty_view;
    @BindView(R.id.list_placeholder_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_msg) TextView tv_msg;
    @BindView(R.id.btn_try_again) TextView btn_try_again;

    public PlaceholderNotFound(View view) {
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public void setText(String textRes) {
        if (!isStringOk(textRes)) {
            tv_msg.setText(null);
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setText(textRes);
            tv_msg.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonText(@StringRes int textRes) {
        if (textRes == 0) {
            btn_try_again.setText(null);
            btn_try_again.setVisibility(View.GONE);
        } else {
            btn_try_again.setText(textRes);
            btn_try_again.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonCallback(View.OnClickListener clickListener) {
        btn_try_again.setOnClickListener(clickListener);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(refreshListener);
        }
    }

    public void setRefreshing(boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    public void setSwipeRefreshColorSchemeResources(Context context) {
        if (swipeRefreshLayout != null) {
            SwipeRefreshUtils.setColorSchemeColors(context, swipeRefreshLayout);
        }
    }

    public void setVisibility(int visibility) {
        view.setVisibility(visibility);
    }

    public void setMessageTextColor(int color) {
        tv_msg.setTextColor(ContextCompat.getColor(view.getContext(), color));
    }
}
