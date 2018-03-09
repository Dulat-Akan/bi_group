package bi.bigroup.life.ui.main.feed.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.mvp.main.feed.news.AddNewsPresenter;
import bi.bigroup.life.mvp.main.feed.news.AddNewsView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.utils.ToastUtils;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.BindView;
import butterknife.OnClick;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

import static bi.bigroup.life.utils.Constants.LIMIT_FILES;
import static bi.bigroup.life.utils.Constants.LIMIT_SINGLE_FILE;
import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;
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
    @BindView(R.id.et_title) MaterialEditText et_title;
    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindView(R.id.et_tags) AutoCompleteTextView et_tags;
    @BindView(R.id.cb_popular_event) CheckBox cb_popular_event;

    @BindView(R.id.tags_layout) TagFlowLayout tags_layout;
    private TagAdapter<Tags> selectedTagsAdapter;
    private List<Tags> selectedTagsList;

    private CommonDialog commonDialog;
    private boolean isSingleImage;

    // Images
    private Image imageSingle;
    private ArrayList<Image> imagesMultiple;

    // Tags
    private List<Tags> tagsList;
    private TagsAdapter tagsAdapter;

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

        tagsList = new ArrayList<>();
        tagsAdapter = new TagsAdapter(this, R.layout.adapter_tags_list);
        tagsAdapter.setCallback(tag -> {
            addTag(tag);
        });
        et_tags.setThreshold(1);
        et_tags.setAdapter(tagsAdapter);

        configureTagsLayout();
    }

    private void configureTagsLayout() {
        selectedTagsList = new ArrayList<>();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        tags_layout.setMaxSelectCount(5);
        tags_layout.setAdapter(selectedTagsAdapter = new TagAdapter<Tags>(selectedTagsList) {
            @Override
            public View getView(FlowLayout parent, int position, Tags tags) {
                View tagsLayout = mInflater.inflate(R.layout.tags_layout, tags_layout, false);
                TextView tv_name = tagsLayout.findViewById(R.id.tv_name);
                tv_name.setText(tags.getName());
                return tagsLayout;
            }
        });
        tags_layout.setOnTagClickListener((view, position, parent) -> {
            selectedTagsList.remove(position);
            selectedTagsAdapter.notifyDataChanged();
            return true;
        });
    }

    void addTag(Tags tag) {
        selectedTagsList.add(tag);
        selectedTagsAdapter.notifyDataChanged();
        et_tags.setText(EMPTY_STR);
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.btn_add_new_tag)
    void onAddNewTag() {
        String tagStr = et_tags.getText().toString();
        if (isStringOk(tagStr)) {
            List<Tags> tagsDuplicates = tagsAdapter.getData();
            boolean isAlreadyExist = false;
            for (int i = 0; i < tagsDuplicates.size(); i++) {
                if (tagStr.equals(tagsDuplicates.get(i).getName())) {
                    isAlreadyExist = true;
                    break;
                }
            }

            if (isAlreadyExist) {
                ToastUtils.showCenteredToast(this, R.string.tag_already_exist, 1000);
            } else {
                addTag(new Tags(null, tagStr));
            }
        } else {
            ToastUtils.showCenteredToast(this, R.string.field_error, 1000);
        }
    }

    @OnClick({R.id.img_single, R.id.img_multiple})
    void onImageSelect(LinearLayout layout) {
        isSingleImage = layout.getId() == R.id.img_single;
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
            if (isSingleImage) {
                if (images.size() > 0) {
                    imageSingle = images.get(0);
                }
            } else {
                imagesMultiple = new ArrayList<>(images);
                for (int i = 0; i < images.size(); i++) {
                    Uri uri = Uri.fromFile(new File(images.get(i).path));
                }
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
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT,
                isSingleImage ? LIMIT_SINGLE_FILE : LIMIT_FILES);
        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);
    }

    @Override
    public void setNewsTags(List<Tags> object) {
        tagsAdapter.addData(object);
    }
}