package bi.bigroup.life.ui.main.bioffice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import butterknife.ButterKnife;

class BiOfficeAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT = R.layout.adapter_bioffice;
    private Context context;
    private List<BiOffice> data = new ArrayList<>();
    private Callback callback;

    BiOfficeAdapter(Context context) {
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addList(List<BiOffice> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BiOffice getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(ITEM_LAYOUT, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bindHolder(getItem(position));

        return convertView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        BiOffice bindedBusTicket;

//        @BindView(R.id.img_checkmark) ImageView img_checkmark;
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(BiOffice object) {
            bindedBusTicket = object;
        }
    }

    public interface Callback {
        void onItemClick(BiOffice biOffice);
    }
}
