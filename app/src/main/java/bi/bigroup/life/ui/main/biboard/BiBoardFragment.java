package bi.bigroup.life.ui.main.biboard;

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

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.biboard.BiBoardPresenter;
import bi.bigroup.life.mvp.main.biboard.BiBoardView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.main.PageSwapCallback;
import bi.bigroup.life.ui.main.biboard.top_questions.TopQuestionsActivity;
import bi.bigroup.life.ui.main.employees.EmployeePageActivity;
import bi.bigroup.life.ui.main.feed.suggestions.NewSuggestionActivity;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;

public class BiBoardFragment extends BaseFragment implements BiBoardView {
    @InjectPresenter
    BiBoardPresenter mvpPresenter;
    @BindView(R.id.lv_board) ListView lv_board;
    private HotBoardViewPager adapter;
    private BiBoardAdapter biBoardAdapter;
    private PageSwapCallback callback;
    private BricksTop7Adapter top7Adapter;

    public static BiBoardFragment newInstance() {
        return new BiBoardFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_biboard;
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
        // ========== Add footer ==============
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.adapter_biboard_footer, lv_board, false);
        RecyclerView recycler_view = footer.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(layoutManager);
        top7Adapter = new BricksTop7Adapter(getContext(), dataLayer.getPicasso());
        recycler_view.setAdapter(top7Adapter);

        footer.findViewById(R.id.ll_open_top_question)
                .setOnClickListener(view -> startActivity(TopQuestionsActivity.getIntent(getContext())));
        lv_board.addFooterView(footer, null, false);

        // ========== Add header ==============
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_biboard_header, lv_board, false);
        configureViewPager(header);
        lv_board.addHeaderView(header, null, false);
        biBoardAdapter = new BiBoardAdapter(getContext());
        biBoardAdapter.setCallback(new BiBoardAdapter.Callback() {
            @Override
            public void onItemClick(BiOffice biOffice) {

            }

            @Override
            public void openEmployeePage(String code) {
                startActivity(EmployeePageActivity.getIntent(getContext(), code));
            }

            @Override
            public void selectEmployeesTab() {
                if (callback != null) {
                    callback.onEmployeesTabsSelect();
                }
            }

            @Override
            public void openNewSuggestionActivity() {
                startActivity(NewSuggestionActivity.getIntent(getContext()));
            }
        });
        lv_board.setAdapter(biBoardAdapter);
    }

    private void configureViewPager(ViewGroup header) {
        ViewPager vp_images = header.findViewById(R.id.vp_images);
        CirclePageIndicator ci_images = header.findViewById(R.id.ci_images);
        adapter = new HotBoardViewPager(getContext(), dataLayer.getPicasso());
        adapter.setCallback(() -> {
            if (callback != null) {
                callback.onFeedTabsSelect();
            }
        });
        vp_images.setAdapter(adapter);
        ci_images.setViewPager(vp_images);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BiBoardView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    @Override
    public void setPopularNews(List<News> list) {
        adapter.addList(list);
    }

    @Override
    public void setBiBoardData(BiBoard biBoard, int position) {
        biBoardAdapter.addItem(biBoard, position);
    }

    @Override
    public void setTopQuestions(List<TopQuestions> list) {
        top7Adapter.addList(list);
    }
}
/*
    @OnClick(R.id.btn_new_publication)
    void onNewPublication() {
        startActivity(PublicationActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_sdesk)
    void onNewSdesk() {
        startActivity(AddSdeskActivity.getIntent(getContext()));
    }
*/