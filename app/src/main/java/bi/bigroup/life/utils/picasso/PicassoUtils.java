package bi.bigroup.life.utils.picasso;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class PicassoUtils {
    public static void showAvatar(Picasso picasso, ImageView view, String url, int placeholder) {
        if (isStringOk(url)) {
            picasso.load(url)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fit().centerCrop()
                    .into(view);
        }
    }

    public static void showNewsImage(Picasso picasso, ImageView view, String url) {
        if (isStringOk(url)) {
            picasso.load(url)
                    .into(view);
        }
    }
}