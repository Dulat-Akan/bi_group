package bi.bigroup.life.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtils {
    public static void showAvatar(Context context, ImageView view, String url, int placeholder) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .error(placeholder)
                .placeholder(placeholder)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public static void showImg(Context context, ImageView view, String url) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .dontAnimate()
                .into(view);
    }

    public static void showNewsImage(Context context, ImageView view, String url) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .dontAnimate()
                .fitCenter()
                .dontTransform()
                .into(view);
    }
}