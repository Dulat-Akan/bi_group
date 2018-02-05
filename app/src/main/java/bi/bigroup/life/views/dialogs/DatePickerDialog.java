package bi.bigroup.life.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerDialog extends android.support.v4.app.DialogFragment {
    private Date initialDate;
    private Callback callback;

    private final int DEFAULT_YEAR = 1995;
    private final int DEFAULT_MONTH = 00;
    private final int DEFAULT_DAY = 01;
    public static DatePickerDialog newInstance(Date initialDate) {
        DatePickerDialog fragment = new DatePickerDialog();
        Bundle args = new Bundle();
        if (initialDate != null) {
            args.putLong("initialDate", initialDate.getTime());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("initialDate")) {
            initialDate = new Date(getArguments().getLong("initialDate"));
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        if (initialDate != null) {
            c.setTime(initialDate);
        } else {
            c.set(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_DAY);
        }
        Context context = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
        return new android.app.DatePickerDialog(context,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (callback != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            callback.onDatePicked(calendar.getTime());
                        }
                    }
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
    }

    public interface Callback {
        void onDatePicked(Date date);
    }
}
