package bi.bigroup.life.ui.main.biboard;

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
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.biboard.HotBoard;
import bi.bigroup.life.mvp.main.biboard.BiBoardPresenter;
import bi.bigroup.life.mvp.main.biboard.BiBoardView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;

public class BiBoardFragment extends BaseFragment implements BiBoardView {
    @InjectPresenter
    BiBoardPresenter mvpPresenter;
    @BindView(R.id.lv_board) ListView lv_board;

    public static BiBoardFragment newInstance() {
        return new BiBoardFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_biboard;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        configureListView();
    }

    private void configureListView() {
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
//        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.lv_bustickets_footer, lv_bustickets, false);
//        btn_buy = footer.findViewById(R.id.btn_buy);
//        btn_buy.setOnClickListener(v -> {
//            if (card != null) {
//                presenter.passActivate(card.cardNumber);
//            }
//        });
//        lv_bustickets.addFooterView(footer, null, false);

        // Add header
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_biboard_header, lv_board, false);
        configureViewPager(header);
        lv_board.addHeaderView(header, null, false);

        BiBoardAdapter adapter = new BiBoardAdapter(getContext());
//        adapter.setCallback(busTicketItem -> busTicket = busTicketItem);
        lv_board.setAdapter(adapter);

        List<BiBoard> biOffices = new ArrayList<>();
        biOffices.add(new BiBoard(getString(R.string.predlojeniya), "new", "new", "new"));
        biOffices.add(new BiBoard(getString(R.string.oprosnik), "new", "new", "new"));
        biOffices.add(new BiBoard(getString(R.string.employees), getString(R.string.novyh),
                getString(R.string.dni_rojdenya), getString(R.string.vacancii)));
        adapter.addList(biOffices);
    }

    private void configureViewPager(ViewGroup header) {
        ViewPager vp_images = header.findViewById(R.id.vp_images);
        CirclePageIndicator ci_images = header.findViewById(R.id.ci_images);
        HotBoardViewPager adapter = new HotBoardViewPager(getContext());
        List<HotBoard> list = new ArrayList<>();
        list.add(new HotBoard());
        list.add(new HotBoard());
        list.add(new HotBoard());
        adapter.addList(list);
        vp_images.setAdapter(adapter);
        ci_images.setViewPager(vp_images);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BiBoardView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}
/*

    @OnClick(R.id.btn_tasks_sdesk)
    void onClick() {
        startActivity(TasksSdeskActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_add_question)
    void onAddClick() {
        startActivity(AddQuestionActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_suggestion)
    void onNewSuggestionClick() {
        startActivity(NewSuggestionActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_publication)
    void onNewPublication() {
        startActivity(PublicationActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_add_news)
    void onAddNews() {
        startActivity(AddNewsActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_sdesk)
    void onNewSdesk() {
        startActivity(AddSdeskActivity.getIntent(getContext()));
    }
*/