package bi.bigroup.life.ui.main.feed.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.File;
import java.util.ArrayList;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.feed.news.AddNewsPresenter;
import bi.bigroup.life.mvp.main.feed.news.AddNewsView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.LOTimber;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.OnClick;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

import static bi.bigroup.life.utils.Constants.LIMIT_FILES;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERMISSIONS_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.PERM_WRITE_EXTERNAL_STORAGE;
import static bi.bigroup.life.utils.permission.PermissionRequestCodes.STORAGE_PERMISSION_CODE;
import static bi.bigroup.life.utils.permission.PermissionUtils.isPermissionGranted;
import static bi.bigroup.life.utils.permission.PermissionUtils.reqPermissions;
import static bi.bigroup.life.utils.permission.PermissionUtils.shouldShowRequestPermission;
import static bi.bigroup.life.utils.permission.PermissionUtils.verifyPermissions;

public class AddNewsActivity extends BaseActivity implements AddNewsView {
    @InjectPresenter
    AddNewsPresenter mvpPresenter;

    private CommonDialog commonDialog;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddNewsActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        commonDialog = new CommonDialog(this);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.img_single)
    void onSingleImage() {
    }

    @OnClick(R.id.img_multiple)
    void onMultipleImagesClick() {
        if (Build.VERSION.SDK_INT >= 23) {
            mvpPresenter.onSelectMultipleImages(
                    isPermissionGranted(AddNewsActivity.this, PERM_WRITE_EXTERNAL_STORAGE),
                    shouldShowRequestPermission(AddNewsActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
        } else {
            mvpPresenter.selectMultipleImages();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            for (int i = 0; i < images.size(); i++) {
                Uri uri = Uri.fromFile(new File(images.get(i).path));
                LOTimber.d("sakldjaslkda uriPATH=" + uri.getPath());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (verifyPermissions(grantResults)) {
                mvpPresenter.selectMultipleImages();
            } else {
                mvpPresenter.onPermissionNotGranted(
                        shouldShowRequestPermission(AddNewsActivity.this, PERM_WRITE_EXTERNAL_STORAGE));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddNewsView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showRequestPermissionDialog(boolean isRequestPermission) {
        commonDialog.showDialogYesNo(getString(R.string.rationale_storage));
        commonDialog.setCallback(new CommonDialog.CallbackYesNo() {
            @Override
            public void onClickYes() {
                if (isRequestPermission) {
                    reqPermissions(AddNewsActivity.this, PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
                } else {
                    // Open Settings activity for allowing permission
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                }
            }

            @Override
            public void onClickNo() {
            }
        });
    }

    @Override
    public void selectMultipleImages() {
        Intent intent = new Intent(this, AlbumSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, LIMIT_FILES);
        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
    }
}