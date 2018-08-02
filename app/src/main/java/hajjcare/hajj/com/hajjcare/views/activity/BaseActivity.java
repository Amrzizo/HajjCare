package hajjcare.hajj.com.hajjcare.views.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hajjcare.hajj.com.hajjcare.R;
import hajjcare.hajj.com.hajjcare.control.Constants;
import hajjcare.hajj.com.hajjcare.control.HajjCareApplicationClass;
import hajjcare.hajj.com.hajjcare.control.UIController;
import hajjcare.hajj.com.hajjcare.views.fragments.BaseFragment;


/**
 * Created by amra on 1/8/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private FrameLayout containerFrameLayout;
    private TextView titleTextView;
    private List<BaseFragment> fragmentList;
    private final int DRAWER_CLOSE_DELAY = 300;
    private Toolbar toolbar;
    private ImageView backActionView, menuActionView;
    private DrawerLayout drawerLayout;
    private boolean isMenuClicked = false;
    protected TextView userName;
    protected ImageView userAvatar;
    protected LinearLayout aboutUs;
    protected LinearLayout ourOffers;
    protected LinearLayout serviceLinear;
    protected LinearLayout knowYourDoctor;
    protected LinearLayout beforeAndAfter;
    protected LinearLayout messages;
    protected LinearLayout booking;
    protected LinearLayout events;
    protected LinearLayout settings;
    protected LinearLayout logout;
    SharedPreferences languagepref;
    String language;
    public static String LANGUAGE_TO_LOAD = "languageToLoad";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    UIController.getInstance().doCall(BaseActivity.this);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        languagepref = getSharedPreferences("language", MODE_PRIVATE);
        language = languagepref.getString(LANGUAGE_TO_LOAD, Locale.getDefault().getDisplayLanguage());

        fragmentList = new ArrayList<>();
        setContentView(R.layout.activity_base_with_shadowed_toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                menuActionView.setBackground(ContextCompat.getDrawable(BaseActivity.this, R.drawable.ic_tracing));

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                menuActionView.setBackground(null);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        customizeActionBarActions();
        removeToolbarShadow();

        if (isMenuButtonEnabled()) {
            customizeSideMenu();
        }

        containerFrameLayout = (FrameLayout) findViewById(R.id.container_frame_layout);

    }

    @Override
    protected void onPause() {
        super.onPause();
        HajjCareApplicationClass.getInstance().setAppInBackground(true);
    }

    protected boolean isMenuButtonEnabled() {
        return true;
    }


    protected boolean isTitleShown() {
        return true;
    }

    public void addFragmentToView(BaseFragment fragment) {
        if (fragment != null) {
            if (fragment.isFragmentAdded()) {
                View currentView = null;
                if (getCurrentFragment() != null) {
                    currentView = getCurrentFragment().getView();
                }
                if (currentView != null) {
                    currentView.setVisibility(View.GONE);
                }
                getSupportFragmentManager().beginTransaction().add(containerFrameLayout.getId(), fragment).commit();
            } else
                getSupportFragmentManager().beginTransaction().replace(containerFrameLayout.getId(), fragment).commitAllowingStateLoss();

            fragmentList.add(fragment);
        }
    }

    public BaseFragment getCurrentFragment() {
        if (fragmentList.size() > 0) {
            return fragmentList.get(fragmentList.size() - 1);
        } else {
            return null;
        }
    }


    protected boolean isBackEnabled() {
        return false;
    }

    protected boolean isHeaderEnabled() {
        return true;
    }

    @Override
    protected void onResume() {

        HajjCareApplicationClass.getInstance().setCurrentShowenActivity(this);
        super.onResume();

        String oldLanguage = language;
        language = languagepref.getString(LANGUAGE_TO_LOAD, Locale.getDefault().getDisplayLanguage());
        if (!oldLanguage.equals(language)) {
            finish();
            startActivity(getIntent());
        }
    }

    public void removeToolbarShadow() {
        findViewById(R.id.appbar).bringToFront();
    }


    private void customizeActionBarActions() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTextView = findViewById(R.id.textView_title);
        if (isHeaderEnabled()) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setContentInsetsAbsolute(0, 0);
            backActionView = (ImageView) findViewById(R.id.action_back);

            if (!isTitleShown()) {
                titleTextView.setVisibility(View.INVISIBLE);
            }

            if (!isBackEnabled())
                if (isMenuButtonEnabled())
                    backActionView.setVisibility(View.GONE);
                else
                    backActionView.setVisibility(View.INVISIBLE);
            else
                backActionView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleBackAction();
                    }
                });


        } else {
            toolbar.setVisibility(View.GONE);
        }

        menuActionView = (ImageView) findViewById(R.id.action_menu);

        if (isMenuButtonEnabled()) {
            menuActionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMenuClicked) {
                        isMenuClicked = false;
                        menuActionView.setBackground(null);
                    } else {
                        menuActionView.setBackground(ContextCompat.getDrawable(BaseActivity.this, R.drawable.ic_tracing));
                        isMenuClicked = true;
                    }
                    handleMenuAction();
                }
            });

        } else {
            menuActionView.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }


    private void handleMenuAction() {
        toggleDrawer(false);
    }

    public void toggleDrawer(boolean isCloseDelayed) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(Constants.LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        } else {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
    }


    public void handleBackAction() {
        if (backActionView != null && (backActionView.getVisibility() == View.GONE || backActionView.getVisibility() == View.INVISIBLE))
            return;
        if (getCurrentFragment() != null) {
            proceedWithBack();
        } else {
            proceedWithBack();
        }
    }

    public void proceedWithBack() {
        if (fragmentList.size() > 1) {

            if (fragmentList.get(fragmentList.size() - 1).isFragmentAdded()) {
                getSupportFragmentManager().beginTransaction().remove(fragmentList.get(fragmentList.size() - 1)).commit();
                fragmentList.remove(fragmentList.size() - 1);
                if (getCurrentFragment() != null) {
                    if (getCurrentFragment().getView() != null) {
                        getCurrentFragment().getView().setVisibility(View.VISIBLE);
                    }
                }
            } else {
                fragmentList.remove(fragmentList.size() - 1);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frame_layout, fragmentList.get(fragmentList.size() - 1)).commit();
            }

        } else {

            if (this instanceof LoginActivity) {
                moveTaskToBack(true);
            } else if (this instanceof HomeActivity) {
                UIController.getInstance().showLogOutDialog(this, false);

            } else {
                super.onBackPressed();
            }

        }
    }

    protected void customizeSideMenu() {

        userName = (TextView) findViewById(R.id.user_name_txt);
        userAvatar = (ImageView) findViewById(R.id.user_photo);
        serviceLinear = (LinearLayout) findViewById(R.id.our_services_linear);
        aboutUs = (LinearLayout) findViewById(R.id.about_us_linear);
        knowYourDoctor = (LinearLayout) findViewById(R.id.know_your_doctor_Linear);
        beforeAndAfter = (LinearLayout) findViewById(R.id.befor_and_after_linear);
        messages = (LinearLayout) findViewById(R.id.notification_linear);
        booking = (LinearLayout) findViewById(R.id.booking_Linear);
        events = (LinearLayout) findViewById(R.id.events_Linear);
        logout = (LinearLayout) findViewById(R.id.logout_Linear);
        settings = (LinearLayout) findViewById(R.id.setting_Linear);

//        ProfileResponse profileResponse = StarSmilesApplicationClass.getUserProfile();
//        if (profileResponse != null) {
//            Controller.setUser(profileResponse.getUser());
//            userName.setText(profileResponse.getUser().getName());
//            titleTextView.setText(profileResponse.getUser().getName());
//            Picasso.with(this).load(StarSmilesApplicationClass.getInstance().getBaseURL() + profileResponse.getUser().getAvatar())
//                    .placeholder(R.drawable.progress_animation)
//                    .error(R.mipmap.name_icon).into(((BaseActivity) this).getUserAvatar());
//        }


        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, AboutActivity.class));
            }
        });

        serviceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, ServicesActivity.class));
            }
        });

        knowYourDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, KnowYourDoctorActivity.class));
            }
        });

        beforeAndAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, BeforeAndAfterActivity.class));
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, messagesActivity.class));
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, BookingActivity.class));
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                startActivity(new Intent(BaseActivity.this, EventsActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//          UIController.getInstance().showChangLanguageDialog(BaseActivity.this, true);
//                startActivity(new Intent(BaseActivity.this, SettingActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer(false);
//                UIController.getInstance().showLogOutDialog(BaseActivity.this, true);
            }
        });
    }

    public TextView getUserName() {
        return userName;
    }

    public void setUserName(TextView userName) {
        this.userName = userName;
    }

    public ImageView getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(ImageView userAvatar) {
        this.userAvatar = userAvatar;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    @Override
    public void onBackPressed() {
        if (this instanceof HomeActivity) {
            UIController.getInstance().showLogOutDialog(this, true);
        } else {
            handleBackAction();
        }
    }
}
