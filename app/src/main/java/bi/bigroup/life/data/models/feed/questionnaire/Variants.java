package bi.bigroup.life.data.models.feed.questionnaire;

import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Variants {
    public String id;
    public String text;
    public String variantText;
    public Integer percentage;

    public int getPercentage() {
        return getOkInt(percentage);
    }

    public String getVariantText() {
        return replaceNull(variantText);
    }

}
