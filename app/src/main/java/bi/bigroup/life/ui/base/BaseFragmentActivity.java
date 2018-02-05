package bi.bigroup.life.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

public abstract class BaseFragmentActivity extends BaseActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }

//        ft.replace(R.id.frgm_container, fragment, fragment.getClass().getName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

//    protected Fragment findFragmentById() {
//        return findFragmentById(R.id.frgm_container);
//    }

    protected Fragment findFragmentById(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }
}