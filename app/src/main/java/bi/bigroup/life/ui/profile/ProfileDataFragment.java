package bi.bigroup.life.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.cachapa.expandablelayout.ExpandableLayout;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.profile.ProfileDataPresenter;
import bi.bigroup.life.mvp.profile.ProfileDataView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.utils.LOTimber;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.GlideUtils.showAvatar;

public class ProfileDataFragment extends BaseFragment implements ProfileDataView {
    @InjectPresenter
    ProfileDataPresenter mvpPresenter;
    @BindView(R.id.ll_hidden_block) LinearLayout ll_hidden_block;
    @BindView(R.id.exp_layout) ExpandableLayout exp_layout;
    @BindView(R.id.tv_hide_show) TextView tv_hide_show;

    @BindView(R.id.img_avatar) RoundedImageView img_avatar;
    @BindView(R.id.tv_surname) TextView tv_surname;
    @BindView(R.id.tv_specialty) TextView tv_specialty;
    @BindView(R.id.tv_iin) TextView tv_iin;
    @BindView(R.id.tv_date_of_birth) TextView tv_date_of_birth;
    @BindView(R.id.tv_family_status) TextView tv_family_status;
    @BindView(R.id.tv_gender) TextView tv_gender;
    @BindView(R.id.tv_childs) TextView tv_childs;
    @BindView(R.id.tv_clothes_size) TextView tv_clothes_size;
    @BindView(R.id.tv_experience) TextView tv_experience;
    @BindView(R.id.tv_coorp_experience) TextView tv_coorp_experience;

    private RowViewHolder v1, v2, v3, v4;

    public static ProfileDataFragment newInstance() {
        return new ProfileDataFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_profile_data;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        v1 = new RowViewHolder(view.findViewById(R.id.v1));
        v1.setCallback(() -> LOTimber.d("asldkasjd clicked 1"));

        v2 = new RowViewHolder(view.findViewById(R.id.v2));
        v2.setCallback(() -> LOTimber.d("asldkasjd clicked 2"));

        v3 = new RowViewHolder(view.findViewById(R.id.v3));
        v3.setCallback(() -> LOTimber.d("asldkasjd clicked 3"));

        v4 = new RowViewHolder(view.findViewById(R.id.v4));
        v4.setCallback(() -> LOTimber.d("asldkasjd clicked 4"));

    }

    @OnClick(R.id.ll_extra_info)
    void onExtraInfoClick() {
        if (exp_layout.isExpanded()) {
            tv_hide_show.setText(getString(R.string.prof_data_extra_info));
            exp_layout.collapse();
        } else {
            tv_hide_show.setText(getString(R.string.prof_data_hide_extra_info));
            exp_layout.expand();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileDataView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showUserInfo(User user) {
        v1.bindHolder(user.getCompany(), R.drawable.domain);
        v2.bindHolder(user.getMobilePhoneNumber(), R.drawable.mobile);
        v3.bindHolder(user.getEmail(), R.drawable.mail);
        v4.bindHolder(user.getWorkPhoneNumber(), R.drawable.phone_inactive);
        showAvatar(getContext(), img_avatar, getProfilePicture(user.getCode()), R.drawable.ic_avatar);
        tv_surname.setText(user.getFullname());
        tv_specialty.setText(user.getJobPosition());
        tv_iin.setText(user.getIin());
        tv_date_of_birth.setText(user.getBirthDate());
        tv_family_status.setText(user.getFamilyStatus());
        tv_gender.setText(user.getGender());
        tv_childs.setText(user.getChildrenQuantity());
        tv_clothes_size.setText(user.getClothingSize());
        tv_experience.setText(user.getTotalExperience());
        tv_coorp_experience.setText(user.getCorporateExperience());
    }
}