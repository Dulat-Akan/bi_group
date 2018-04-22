package bi.bigroup.life.ui.main.bioffice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.bioffice.BiOfficePresenter;
import bi.bigroup.life.mvp.main.bioffice.BiOfficeView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.main.PageSwapCallback;
import bi.bigroup.life.ui.main.biboard.BricksTop7Adapter;
import bi.bigroup.life.ui.main.biboard.top_questions.TopQuestionsActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.TasksSdeskActivity;
import bi.bigroup.life.ui.main.feed.news.NewsDetailActivity;
import bi.bigroup.life.ui.main.feed.suggestions.SuggestionDetailActivity;
import bi.bigroup.life.utils.view_pager.ParallaxPageTransformer;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;

import static bi.bigroup.life.ui.main.bioffice.BiOfficeAdapter.TYPE_TASKS_SERVICES;
import static bi.bigroup.life.utils.DateUtils.getTodaysDate;

public class BiOfficeFragment extends BaseFragment implements BiOfficeView {
    @InjectPresenter
    BiOfficePresenter mvpPresenter;
    @BindView(R.id.lv_office) ListView lv_office;
    private BiOfficeAdapter adapter;
    private HotBoardViewPager sliderAdapter;
    private BricksTop7Adapter top7Adapter;
    private PageSwapCallback callback;

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
        configureListView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (PageSwapCallback) context;
        } catch (ClassCastException castException) {
            castException.printStackTrace();
        }
    }

    private void configureListView() {
        assert getContext() != null;
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        // ========== Add footer ==============
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.adapter_biboard_footer, lv_office, false);
        RecyclerView recycler_view = footer.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(layoutManager);
        top7Adapter = new BricksTop7Adapter(getContext(), dataLayer.getPicasso());
        recycler_view.setAdapter(top7Adapter);

        footer.findViewById(R.id.ll_open_top_question)
                .setOnClickListener(view -> startActivity(TopQuestionsActivity.getIntent(getContext())));
//        lv_board.addFooterView(footer, null, false);

        // Add header
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
            public void onItemClick() {

            }
        });

        adapter.setCallbackBiBoard(new BiOfficeAdapter.CallbackBiBoard() {
            @Override
            public void onSuggestionClick() {
                if (callback != null) {
                    callback.onSuggestionClick();
                }
            }

            @Override
            public void onQuestionnaireClick() {
                if (callback != null) {
                    callback.onQuestionnaireClick();
                }
            }

            @Override
            public void selectEmployeesTab() {
                if (callback != null) {
                    callback.onEmployeesTabsSelect();
                }
            }

            @Override
            public void openSuggestionDetail(String id) {
                startActivity(SuggestionDetailActivity.getIntent(getContext(), id));
            }

            @Override
            public void openQuestionnaireDetail(String id) {

            }
        });
        lv_office.setAdapter(adapter);
    }

    private void configureViewPager(ViewGroup header) {
        ViewPager vp_images = header.findViewById(R.id.vp_images);
        CirclePageIndicator ci_images = header.findViewById(R.id.ci_images);
        sliderAdapter = new HotBoardViewPager(getContext(), dataLayer.getPicasso(), R.layout.adapter_biboard_view_pager);
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
        adapter.setItem(new BiOffice(
                R.string.zayavki_i_zadachi,
                R.string.label_all,
                R.string.row_prin,
                R.string.row_pros,
                object), TYPE_TASKS_SERVICES);
//        adapter.addItem(new BiOffice(R.string.kpi_proekty, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
//        adapter.addItem(new BiOffice(R.string.sandb, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
//        adapter.addItem(new BiOffice(R.string.idp, R.string.empty_str, R.string.empty_str, R.string.empty_str, null));
    }

    @Override
    public void setPopularNews(List<News> list) {
        sliderAdapter.addList(list);
    }

    @Override
    public void setBiBoardData(BiBoard biBoard, int position) {
        adapter.setItem(biBoard, position);
    }

    @Override
    public void setTopQuestions(List<TopQuestions> list) {
        top7Adapter.addList(list);
    }
}