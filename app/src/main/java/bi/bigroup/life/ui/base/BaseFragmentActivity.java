package bi.bigroup.life.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import bi.bigroup.life.R;

public abstract class BaseFragmentActivity extends BaseActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack, String tag, boolean showFeedFab,
                                   boolean showBiOfficeFab, boolean hideToolbar) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        if (tag != null) {
            ft.replace(R.id.fragment_container, fragment, tag);
        } else {
            ft.replace(R.id.fragment_container, fragment);
        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    protected Fragment findFragmentById() {
        return findFragmentById(R.id.fragment_container);
    }

    protected Fragment findFragmentById(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }
}