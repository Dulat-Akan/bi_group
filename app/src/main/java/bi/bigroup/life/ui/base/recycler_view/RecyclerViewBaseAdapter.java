package bi.bigroup.life.ui.base.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bi.bigroup.life.R;

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerViewBaseAdapter.MainViewHolder> {
    protected static final int PROGRESS_BAR_LAYOUT_ID = R.layout.inc_pb_opaque_small;

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(View v) {
            super(v);
        }
    }

    public class SimpleViewHolder extends MainViewHolder {
        public SimpleViewHolder(View v) {
            super(v);
        }
    }

    protected View inflate(ViewGroup parent, int resource) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        return mInflater.inflate(resource, parent, false);
    }

    protected IllegalStateException incorrectOnCreateViewHolder() {
        return new IllegalStateException("Incorrect ViewType found");
    }

    protected IllegalStateException incorrectGetItemViewType() {
        return new IllegalStateException("Incorrect object added");
    }
}