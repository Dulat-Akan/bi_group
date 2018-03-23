package bi.bigroup.life.ui.main.bioffice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.bioffice.BiOfficePresenter;
import bi.bigroup.life.mvp.main.bioffice.BiOfficeView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.main.biboard.HotBoardViewPager;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.TasksSdeskActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_sdesk.AddSdeskActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task.AddTaskActivity;
import bi.bigroup.life.ui.main.feed.news.NewsDetailActivity;
import bi.bigroup.life.utils.view_pager.ParallaxPageTransformer;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import bi.bigroup.life.views.dialogs.CommonDialog;
import butterknife.BindView;

import static bi.bigroup.life.utils.DateUtils.getTodaysDate;

public class BiOfficeFragment extends BaseFragment implements BiOfficeView {
    @InjectPresenter
    BiOfficePresenter mvpPresenter;
    @BindView(R.id.lv_office) ListView lv_office;
    private BiOfficeAdapter adapter;
    private HotBoardViewPager sliderAdapter;
    private CommonDialog commonDialog;

    public static BiOfficeFragment newInstance() {
        return new BiOfficeFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_bioffice;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        commonDialog = new CommonDialog(getContext());
        configureListView();
    }

    private void configureListView() {
        // Add header
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_bioffice_header, lv_office, false);
        TextView tv_date = header.findViewById(R.id.tv_date);
        tv_date.setText((getTodaysDate(getResources().getStringArray(R.array.months_array_long))).toLowerCase());
        configureViewPager(header);
        lv_office.addHeaderView(header, null, false);
        adapter = new BiOfficeAdapter(getContext());
        adapter.setCallback(new BiOfficeAdapter.Callback() {
            @Override
            public void openTasksSdeskActivity() {
                startActivity(TasksSdeskActivity.getIntent(getContext()));
            }

            @Override
            public void onAddServicesTasks() {
                commonDialog.setCallDoubleButtons(new CommonDialog.CallbackDouble() {
                    @Override
                    public void onClickFirst() {
                        startActivity(AddSdeskActivity.getIntent(getContext()));
                    }

                    @Override
                    public void onClickSecond() {
                        startActivity(AddTaskActivity.getIntent(getContext()));
                    }
                });
                commonDialog.showDoubleDialog(getString(R.string.new_sdesk), getString(R.string.new_task));
            }

            @Override
            public void onItemClick() {

            }
        });
        lv_office.setAdapter(adapter);
    }

    private void configureViewPager(ViewGroup header) {
        ViewPager vp_images = header.findViewById(R.id.vp_images);
        CirclePageIndicator ci_images = header.findViewById(R.id.ci_images);
        sliderAdapter = new HotBoardViewPager(getContext(), dataLayer.getPicasso());
        sliderAdapter.setCallback(newsId -> startActivity(NewsDetailActivity.getIntent(getContext(), newsId)));

        vp_images.setAdapter(sliderAdapter);
        vp_images.setPageTransformer(true, new ParallaxPageTransformer());
        ci_images.setViewPager(vp_images);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BiOfficeView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setCombinedServiceTask(CombineServiceTask object) {
        adapter.clearData();
        adapter.addItem(new BiOffice(
                R.string.zayavki_i_zadachi,
                R.string.label_all,
                R.string.row_prin,
                R.string.row_pros,
                object));
//        adapter.addItem(new BiOffice(R.string.kpi_proekty, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
//        adapter.addItem(new BiOffice(R.string.sandb, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
//        adapter.addItem(new BiOffice(R.string.idp, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
    }

    @Override
    public void setPopularNews(List<News> list) {
        sliderAdapter.addList(list);
    }
}