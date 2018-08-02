package hajjcare.hajj.com.hajjcare.views.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.views.fragments.LoginFragment;


/**
 * Created by amra on 2/3/2018.
 */

public class LoginActivity extends BaseActivity {
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.hide();
        }
        loginFragment = new LoginFragment();
        addFragmentToView(loginFragment);
    }

    @Override
    protected boolean isMenuButtonEnabled() {
        return false;
    }


}
