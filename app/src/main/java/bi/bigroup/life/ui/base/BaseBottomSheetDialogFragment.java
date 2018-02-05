package bi.bigroup.life.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.preferences.Preferences;

public class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    protected DataLayer dataLayer;
    protected Preferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLayer = DataLayer.getInstance(getContext());
        preferences = dataLayer.getPreferences();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    protected void setBottomSheetCallback(View v) {
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(((View) v.getParent()));
        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
//        ((View) v.getParent()).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_dialog));
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    protected void setActivityResult(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }
}