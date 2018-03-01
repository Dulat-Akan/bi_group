package bi.bigroup.life.ui.main.bioffice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.bioffice.HotOffice;
import bi.bigroup.life.mvp.main.bioffice.BiOfficePresenter;
import bi.bigroup.life.mvp.main.bioffice.BiOfficeView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.TasksSdeskActivity;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;

public class BiOfficeFragment extends BaseFragment implements BiOfficeView {
    @InjectPresenter
    BiOfficePresenter mvpPresenter;
    @BindView(R.id.lv_office) ListView lv_office;

    public static BiOfficeFragment newInstance() {
        return new BiOfficeFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_bioffice;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        configureListView();
    }

    private void configureListView() {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        // Add header
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_bioffice_header, lv_office, false);
        configureViewPager(header);
        lv_office.addHeaderView(header, null, false);

        BiOfficeAdapter adapter = new BiOfficeAdapter(getContext());
        adapter.setCallback(new BiOfficeAdapter.Callback() {
            @Override
            public void openTasksSdeskActivity() {
                startActivity(TasksSdeskActivity.getIntent(getContext()));
            }

            @Override
            public void onItemClick() {

            }
        });
        lv_office.setAdapter(adapter);

        List<BiOffice> biOffices = new ArrayList<>();
        biOffices.add(new BiOffice(getString(R.string.zayavki_i_zadachi)));
        biOffices.add(new BiOffice(getString(R.string.kpi_proekty)));
        biOffices.add(new BiOffice(getString(R.string.sandb)));
        biOffices.add(new BiOffice(getString(R.string.idp)));
        adapter.addList(biOffices);
    }

    private void configureViewPager(ViewGroup header) {
        ViewPager vp_images = header.findViewById(R.id.vp_images);
        CirclePageIndicator ci_images = header.findViewById(R.id.ci_images);
        HotOfficeViewPager adapter = new HotOfficeViewPager(getContext());
        List<HotOffice> list = new ArrayList<>();
        list.add(new HotOffice());
        list.add(new HotOffice());
        list.add(new HotOffice());
        adapter.addList(list);
        vp_images.setAdapter(adapter);
        ci_images.setViewPager(vp_images);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BiOfficeView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}