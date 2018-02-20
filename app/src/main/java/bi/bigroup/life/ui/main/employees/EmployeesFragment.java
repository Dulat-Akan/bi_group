package bi.bigroup.life.ui.main.employees;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.employees.EmployeesPresenter;
import bi.bigroup.life.mvp.main.employees.EmployeesView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import butterknife.BindView;

public class EmployeesFragment extends BaseFragment implements EmployeesView {
    @InjectPresenter
    EmployeesPresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public static EmployeesFragment newInstance() {
        return new EmployeesFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_employees;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        configureViewPager();
    }

    private void configureViewPager() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        tabs.setupWithViewPager(viewPager);
        adapter.addFrag(AllEmployeesFragment.newInstance(false), getString(R.string.employees_all));
        adapter.addFrag(AllEmployeesFragment.newInstance(true), getString(R.string.employees_dob));
        adapter.addFrag(VacanciesFragment.newInstance(), getString(R.string.employees_vacancies));
        viewPager.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EmployeesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}