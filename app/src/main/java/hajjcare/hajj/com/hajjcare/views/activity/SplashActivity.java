package hajjcare.hajj.com.hajjcare.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import hajjcare.hajj.com.hajjcare.R;


public class SplashActivity extends AppCompatActivity {

    private View mContentView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

//        SharedPreferences preferences = getSharedPreferences("star_smiles", Context.MODE_PRIVATE);
//        if (preferences.getString("user", null) == null &&
//                preferences.getString("pass", null) == null ||(
//                preferences.getString("user", null).equals("") &&
//                preferences.getString("pass", null).equals(""))) {
            setContentView(R.layout.activity_splash);


            mContentView = findViewById(R.id.fullscreen_content_controls);


            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            scheduleSplashScreen();
//        } else {
//            HajjCareApplicationClass.setToken(preferences.getString("token", null));
//            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//            finish();
//        }
    }


    public void scheduleSplashScreen() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        }, 1000);
    }
}
