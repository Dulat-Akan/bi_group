package bi.bigroup.life.utils.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AvatarAnimation {
    private RelativeLayout user_photo_container;
    private ImageView img_expanded;
    private ImageView iv_prof_img;

    public AvatarAnimation(Context context, RelativeLayout user_photo_container, ImageView img_expanded,
                           ImageView iv_prof_img) {
        this.user_photo_container = user_photo_container;
        this.img_expanded = img_expanded;
        this.iv_prof_img = iv_prof_img;
        mShortAnimationDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    private Animator mCurrentAnimator;
    private Rect startBounds;
    private float startScaleFinal;
    private int mShortAnimationDuration;
    private Direction direction = Direction.NONE;
    private int previousFingerPositionY;
    private int previousFingerPositionX;
    private int baseLayoutPosition;

    private enum Direction {
        UP_DOWN,
        NONE
    }

    public void onOpenUserPhoto() {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        iv_prof_img.getGlobalVisibleRect(startBounds);
        user_photo_container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, globalOffset.y * 3);
        finalBounds.offset(-globalOffset.x, globalOffset.y * 3);

        final float startScale;
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;

        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height());
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;

        }

        user_photo_container.setVisibility(View.VISIBLE);
        img_expanded.setVisibility(View.VISIBLE);
        img_expanded.setPivotX(0f);
        img_expanded.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(img_expanded, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(img_expanded, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(img_expanded, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(img_expanded, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        startScaleFinal = startScale;
        img_expanded.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                final int y = (int) ev.getRawY();
                final int x = (int) ev.getRawX();

                if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    previousFingerPositionX = x;
                    previousFingerPositionY = y;
                    baseLayoutPosition = (int) v.getY();

                } else if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    int diffY = y - previousFingerPositionY;
                    int diffX = x - previousFingerPositionX;

                    if (direction == Direction.NONE) {
                        if (Math.abs(diffX) < Math.abs(diffY)) {
                            direction = Direction.UP_DOWN;
                        } else {
                            direction = Direction.NONE;
                        }
                    }

                    if (direction == Direction.UP_DOWN) {
                        v.setY(baseLayoutPosition + diffY);
                        v.requestLayout();
                        return true;
                    }

                } else if (ev.getActionMasked() == MotionEvent.ACTION_UP) {
                    if (direction == Direction.UP_DOWN) {
                        int height = v.getHeight();
                        if (Math.abs(v.getY()) > (height / 6))
                            closeHighlightImage(startBounds, startScaleFinal);
                        else
                            v.setY(baseLayoutPosition);

                        direction = Direction.NONE;
                        return true;
                    } else {
                        closeHighlightImage(startBounds, startScaleFinal);
                    }
                    direction = Direction.NONE;
                }

                return true;
            }
        });
    }

    public void closeImage() {
        closeHighlightImage(startBounds, startScaleFinal);
    }

    private void closeHighlightImage(Rect startBounds, float startScaleFinal) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(img_expanded, View.X, startBounds.left))
                .with(ObjectAnimator.ofFloat(img_expanded, View.Y, startBounds.top))
                .with(ObjectAnimator.ofFloat(img_expanded, View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator.ofFloat(img_expanded, View.SCALE_Y, startScaleFinal));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationDone();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animationDone();
            }

            private void animationDone() {
                user_photo_container.setVisibility(View.GONE);
                img_expanded.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }
}
