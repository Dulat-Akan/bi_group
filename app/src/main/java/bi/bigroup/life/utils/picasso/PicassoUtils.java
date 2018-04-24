package bi.bigroup.life.utils.picasso;

import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
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

    public static void showExpandedAvatar(Picasso picasso, ImageView view, String url, int placeholder) {
        if (isStringOk(url)) {
            picasso.load(url)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fit().centerInside()
                    .into(view);
        }
    }

    public static void showNewsImage(Picasso picasso, ImageView view, String url) {
        if (isStringOk(url)) {
            picasso.load(url)
                    .into(view);
        }
    }

    public static void showNewsImageTransformation(Picasso picasso, ImageView view, String url) {
        if (isStringOk(url)) {
            int MAX_WIDTH = 1024;
            int MAX_HEIGHT = 768;
            int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
            picasso.load(url)
                    .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(size, size)
                    .into(view);
        }
    }
}