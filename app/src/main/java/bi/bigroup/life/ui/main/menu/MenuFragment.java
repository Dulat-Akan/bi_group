package bi.bigroup.life.ui.main.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.main.menu.MenuPresenter;
import bi.bigroup.life.mvp.main.menu.MenuView;
import bi.bigroup.life.ui.auth.AuthActivity;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.profile.ProfileActivity;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.getProfilePicture;

public class MenuFragment extends BaseFragment implements MenuView {
    @InjectPresenter
    MenuPresenter mvpPresenter;
    @BindView(R.id.img_avatar) ImageView img_avatar;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_username) TextView tv_username;
    private CommonDialog commonDialog;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_menu;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        commonDialog = new CommonDialog(getContext());

        RowViewHolder v1 = new RowViewHolder(view.findViewById(R.id.v1), false);
        v1.bindHolder(R.string.menu_title_1, R.string.menu_desc_1);
        v1.setCallback(() -> startActivity(DevelopingStageActivity.getIntent(getContext())));

        RowViewHolder v2 = new RowViewHolder(view.findViewById(R.id.v2), false);
        v2.bindHolder(R.string.menu_title_2, R.string.menu_desc_2);
        v2.setCallback(() -> startActivity(DevelopingStageActivity.getIntent(getContext())));

        RowViewHolder v3 = new RowViewHolder(view.findViewById(R.id.v3), false);
        v3.bindHolder(R.string.menu_title_3, R.string.menu_desc_3);
        v3.setCallback(() -> startActivity(DevelopingStageActivity.getIntent(getContext())));

//        RowViewHolder v4 = new RowViewHolder(view.findViewById(R.id.v4), true);
//        v4.bindHolder(R.string.menu_title_4, R.string.menu_desc_4);
//        v4.setCallback(() -> startActivity(TopQuestionsActivity.getIntent(getContext())));
    }

    @OnClick(R.id.ll_profile)
    void onProfileClick() {
        startActivity(ProfileActivity.getIntent(getContext()));
    }

    @OnClick(R.id.tv_logout)
    void onLogoutClick() {
        commonDialog.showDialogYesNo(getString(R.string.logout_confirm));
        commonDialog.setCallback(new CommonDialog.CallbackYesNo() {
            @Override
            public void onClickYes() {
                dataLayer.wipeOut();
                startActivity(AuthActivity.newLogoutIntent(getContext()));
            }

            @Override
            public void onClickNo() {
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////
    // MenuView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    @Override
    public void showUserInfo(User localUser) {
        tv_name.setText(localUser.getFullname());
        tv_username.setText(localUser.getAdLogin());
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(localUser.getCode()), R.drawable.ic_user);
    }
}