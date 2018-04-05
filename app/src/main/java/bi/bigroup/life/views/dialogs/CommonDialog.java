package bi.bigroup.life.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.utils.picasso.PicassoUtils;

import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class CommonDialog {
    private Context context;
    private CallbackYesNo callbackYesNo;
    private CallbackDouble callDoubleButtons;
    private CallbackEnterMsgBtn callbackEnterMsgBtn;

    public CommonDialog(Context context) {
        this.context = context;
    }

    public void setCallback(CallbackYesNo callbackYesNo) {
        this.callbackYesNo = callbackYesNo;
    }

    public void setCallDoubleButtons(CallbackDouble callDoubleButtons) {
        this.callDoubleButtons = callDoubleButtons;
    }

    public void setCallback(CallbackEnterMsgBtn callbackEnterMsgBtn) {
        this.callbackEnterMsgBtn = callbackEnterMsgBtn;
    }

    public void showDialogYesNo(String title) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yesno);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        final Button btn_yes = dialog.findViewById(R.id.btn_yes);
        final Button btn_no = dialog.findViewById(R.id.btn_no);
        ((TextView) dialog.findViewById(R.id.tv_title)).setText(title);

        View.OnClickListener clickListener = v -> {
            if (callbackYesNo != null) {
                if (v.equals(btn_yes))
                    callbackYesNo.onClickYes();
                else if (v.equals(btn_no))
                    callbackYesNo.onClickNo();

                dialog.dismiss();
            }
        };
        btn_yes.setOnClickListener(clickListener);
        btn_no.setOnClickListener(clickListener);

        dialog.show();
    }

    public void showDoubleDialog(String first, String second) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_double_buttons);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        final Button btn_first = dialog.findViewById(R.id.btn_first);
        btn_first.setText(first);
        final Button btn_second = dialog.findViewById(R.id.btn_second);
        final Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_second.setText(second);
        View.OnClickListener clickListener = v -> {
            if (callDoubleButtons != null) {
                if (v.equals(btn_first))
                    callDoubleButtons.onClickFirst();
                else if (v.equals(btn_second))
                    callDoubleButtons.onClickSecond();

                dialog.dismiss();
            }
        };
        btn_first.setOnClickListener(clickListener);
        btn_second.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(clickListener);
        dialog.show();
    }

    public void showBirthdayEnterText(Employee employee, Picasso picasso) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_enter_text);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final MaterialEditText et_msg = dialog.findViewById(R.id.et_msg);
        final Button btn_send_comment = dialog.findViewById(R.id.btn_send_comment);
        final ImageView img_avatar = dialog.findViewById(R.id.img_avatar);
        PicassoUtils.showAvatar(picasso, img_avatar, getProfilePicture(employee.getCode()), R.drawable.ic_user);

        final ImageView img_close = dialog.findViewById(R.id.img_close);
        View.OnClickListener clickListener = v -> {
            if (callbackEnterMsgBtn != null) {
                if (v.equals(btn_send_comment)) {
                    if (isStringOk(et_msg.getText().toString())) {
                        callbackEnterMsgBtn.onClickAction(et_msg.getText().toString());
                        dialog.dismiss();
                    } else {
                        et_msg.setError(context.getString(R.string.field_error));
                    }
                } else if (v.equals(img_close)) {
                    dialog.dismiss();
                }
            }
        };
        btn_send_comment.setOnClickListener(clickListener);
        img_close.setOnClickListener(clickListener);
        dialog.show();
    }

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

    public interface CallbackYesNo {
        void onClickYes();

        void onClickNo();
    }

    public interface CallbackDouble {
        void onClickFirst();

        void onClickSecond();
    }

    public interface CallbackEnterMsgBtn {
        void onClickAction(String content);
    }
}
