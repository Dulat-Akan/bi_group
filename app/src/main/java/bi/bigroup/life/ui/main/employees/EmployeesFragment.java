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
import bi.bigroup.life.ui.main.BottomNavigationTabFragment;
import butterknife.BindView;

public class EmployeesFragment extends BaseFragment implements EmployeesView, BottomNavigationTabFragment {
    @InjectPresenter
    EmployeesPresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    private AllEmployeesFragment allEmployeesFragment;
    private AllEmployeesFragment dateOfBirthFragment;
    private VacanciesFragment vacanciesFragment;

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        tabs.setupWithViewPager(viewPager);
        allEmployeesFragment = AllEmployeesFragment.newInstance(false);
        dateOfBirthFragment = AllEmployeesFragment.newInstance(true);
        vacanciesFragment = VacanciesFragment.newInstance();

        adapter.addFrag(allEmployeesFragment, getString(R.string.employees_all));
        adapter.addFrag(dateOfBirthFragment, getString(R.string.employees_dob));
        adapter.addFrag(vacanciesFragment, getString(R.string.employees_vacancies));
        viewPager.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // EmployeesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // BottomNavigationTabFragment implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onBottomNavigationTabReselected() {
        if (viewPager.getCurrentItem() == 0) {
            if (allEmployeesFragment != null)
                allEmployeesFragment.onBottomNavigationTabReselected();
        } else if (viewPager.getCurrentItem() == 1) {
            if (dateOfBirthFragment != null)
                dateOfBirthFragment.onBottomNavigationTabReselected();
        } else if (viewPager.getCurrentItem() == 2) {
            if (vacanciesFragment != null)
                vacanciesFragment.onBottomNavigationTabReselected();
        }
    }
}