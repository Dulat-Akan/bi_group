package bi.bigroup.life.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;

import bi.bigroup.life.R;

public class EditTextWatcher implements TextWatcher {

    private Context context;
    private TextInputLayout textInputLayout;
    private String errorText;

    private final ForegroundColorSpan normalTextAppearance = new ForegroundColorSpan(Color.GRAY);
    private final AlignmentSpan alignmentSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
    private final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

    public EditTextWatcher(Context context, TextInputLayout textInputLayout) {
        this.context = context;
        this.textInputLayout = textInputLayout;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    private void updateErrorText() {
        if (!hasValidLength()) {
            if (errorText != null
                    && !errorText.isEmpty()) {
                textInputLayout.setError(getSpannableStringBuilderForError(errorText));
            }
        } else {
            hideErrorText();
        }
    }

    private boolean hasValidLength() {
        int length = 0;
        if (textInputLayout.getEditText() != null) {
            length = textInputLayout.getEditText().length();
        }

        return length > 0;
    }

    public void showFieldError(String errorText) {
        if (errorText != null
                && !errorText.isEmpty()) {
            textInputLayout.setError(getSpannableStringBuilderForError(errorText));
        }
    }

    private SpannableStringBuilder getSpannableStringBuilderForError(String errorText) {
        spannableStringBuilder.clear();
        spannableStringBuilder.clearSpans();
        spannableStringBuilder.append(errorText);
        spannableStringBuilder.append(" ");

        CenteredImageSpan centeredImageSpan = new CenteredImageSpan(context, R.drawable.warning_icn);

        spannableStringBuilder.setSpan(alignmentSpan, 0, spannableStringBuilder.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(centeredImageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableStringBuilder;
    }

    private void hideErrorText() {
        spannableStringBuilder.clear();
        spannableStringBuilder.clearSpans();
        spannableStringBuilder.setSpan(normalTextAppearance, 0, spannableStringBuilder.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textInputLayout.setError(spannableStringBuilder);
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        if (textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setError(null);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateErrorText();
    }

}