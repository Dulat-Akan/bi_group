package bi.bigroup.life.views.dialogs;

import android.content.Context;

public class CommonDialog {
    private Context context;
//    private Callback callback;
//    private CallbackPositiveNegative callbackPositiveNegative;
//    private CallbackSingleBtn callbackSingleBtn;
//    private CallbackDatePicker callbackDatePicker;

    public CommonDialog(Context context) {
        this.context = context;
    }

//    public void setCallback(CallbackSingleBtn callbackSingleBtn) {
//        this.callbackSingleBtn = callbackSingleBtn;
//    }
//
//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }
//
//    public void setCallback(CallbackDatePicker callback) {
//        this.callbackDatePicker = callback;
//    }
//
//    public void setCallback(CallbackPositiveNegative callbackPositiveNegative) {
//        this.callbackPositiveNegative = callbackPositiveNegative;
//    }

//    public void showDialogTitleDescSingleBtn(String title, String description, String btnText, boolean showTitle, boolean showDescription) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_titledesc_single_btn);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        dialog.findViewById(R.id.tv_title).setVisibility(showTitle ? View.VISIBLE : View.GONE);
//        ((TextView) dialog.findViewById(R.id.tv_title)).setText(title);
//        ((TextView) dialog.findViewById(R.id.tv_desc)).setText(description);
//        dialog.findViewById(R.id.tv_desc).setVisibility(showDescription ? View.VISIBLE : View.GONE);
//
//        final Button btn_action = dialog.findViewById(R.id.btn_action);
//        btn_action.setText(btnText);
//        btn_action.setOnClickListener(v -> {
//            if (callbackSingleBtn != null) {
//                callbackSingleBtn.onClickAction();
//            }
//            dialog.dismiss();
//        });
//        dialog.setCancelable(false);
//        dialog.show();
//    }
//
//    public void showDialogPositiveNegative(String title, String description, String txtPositive, String txtNegative,
//                                           boolean showTitle, boolean showDescription) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_positive_negative);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//        final Button btn_positive = dialog.findViewById(R.id.btn_positive);
//        final Button btn_negative = dialog.findViewById(R.id.btn_negative);
//        btn_positive.setText(txtPositive);
//        btn_negative.setText(txtNegative);
//
//        dialog.findViewById(R.id.tv_title).setVisibility(showTitle ? View.VISIBLE : View.GONE);
//        ((TextView) dialog.findViewById(R.id.tv_title)).setText(title);
//        ((TextView) dialog.findViewById(R.id.tv_desc)).setText(description);
//        dialog.findViewById(R.id.tv_desc).setVisibility(showDescription ? View.VISIBLE : View.GONE);
//
//        View.OnClickListener clickListener = v -> {
//            if (callbackPositiveNegative != null) {
//                if (v.equals(btn_positive))
//                    callbackPositiveNegative.onClickPositive();
//                else if (v.equals(btn_positive))
//                    callbackPositiveNegative.onClickNegative();
//                dialog.dismiss();
//            }
//        };
//        btn_positive.setOnClickListener(clickListener);
//        btn_negative.setOnClickListener(clickListener);
//        dialog.show();
//    }
//
//    public void showDialogDoubleButtons(String firstTxt, String secondTxt) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_double_buttons);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        final Button btn_first = dialog.findViewById(R.id.btn_first);
//        final Button btn_second = dialog.findViewById(R.id.btn_second);
//        btn_first.setText(firstTxt);
//        btn_second.setText(secondTxt);
//        View.OnClickListener clickListener = v -> {
//            if (callbackPositiveNegative != null) {
//                if (v.equals(btn_first))
//                    callbackPositiveNegative.onClickPositive();
//                else if (v.equals(btn_second))
//                    callbackPositiveNegative.onClickNegative();
//                dialog.dismiss();
//            }
//        };
//        btn_first.setOnClickListener(clickListener);
//        btn_second.setOnClickListener(clickListener);
//        dialog.show();
//    }
//
//    public void showDatePickerDialog(boolean isEndDate) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_date_picker);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//        final MaterialCalendarView calendarView = dialog.findViewById(R.id.calendarView);
//
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        calendarView.setOnDateChangedListener((widget, date, selected) -> {
//            if (callbackDatePicker != null) {
//                callbackDatePicker.onDateSelected(date, isEndDate);
//            }
//            dialog.dismiss();
//        });
//        calendarView.state().edit()
//                .setFirstDayOfWeek(Calendar.WEDNESDAY)
//                .setMaximumDate(CalendarDay.from(year, month, day))
//                .commit();
//        dialog.show();
//    }
//
//
//    public interface CallbackDatePicker {
//        void onDateSelected(CalendarDay date, boolean isEndDate);
//    }
//
//    public interface CallbackSingleBtn {
//        void onClickAction();
//    }
//
//    public interface Callback {
//        void onClickYesWithText(String text);
//
//        void onClickCancel();
//    }

    public interface CallbackPositiveNegative {
        void onClickPositive();

        void onClickNegative();
    }
}
