package bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;

public class EmployeesLayoutAdapter extends ArrayAdapter<Employee> {
    private LayoutInflater layoutInflater;
    private List<Employee> data;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public EmployeesLayoutAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        data = new ArrayList<>();
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addData(List<Employee> newItems) {
        data.addAll(newItems);
        notifyDataSetChanged();
    }

    public List<Employee> getData() {
        return data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_tags_list, null);
        }
        Employee employee = getItem(position);

        TextView name = view.findViewById(R.id.tv_title);
        LinearLayout ll_content = view.findViewById(R.id.ll_content);
        if (employee != null) {
            name.setText(employee.getFullName());
            ll_content.setOnClickListener(view1 -> {
                if (callback != null) {
                    callback.onTabSelected(employee);
                }
            });
        }
        return view;
    }

    public interface Callback {
        void onTabSelected(Employee employee);
    }
}
