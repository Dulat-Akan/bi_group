package bi.bigroup.life.data.models.feed.questionnaire;

import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Variants {
    public String id;
    public String text;
    public String variantText;
    public Integer percentage;
    public transient boolean isVariantChecked;

    public int getPercentage() {
        return getOkInt(percentage);
    }

    public String getVariantText() {
        return replaceNull(variantText);
    }

    public String getId() {
        return replaceNull(id);
    }

    public String getText() {
        return replaceNull(text);
    }

    public boolean isVariantChecked() {
        return isOkBoolean(isVariantChecked);
    }

    public void setVariantChecked(boolean variantChecked) {
        isVariantChecked = variantChecked;
    }
}
