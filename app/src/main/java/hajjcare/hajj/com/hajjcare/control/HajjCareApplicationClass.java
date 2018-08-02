package hajjcare.hajj.com.hajjcare.control;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;


import java.lang.reflect.Field;
import java.util.Locale;

import hajjcare.hajj.com.hajjcare.BuildConfig;

/**
 * Created by amra on 2/2/2018.
 */

public class HajjCareApplicationClass extends Application implements Thread.UncaughtExceptionHandler {


    private static Context context;
    private static HajjCareApplicationClass instance;
    private Activity currentShowenActivity;
    private boolean appInBackground = false;
    private static String token;
//    public static ProfileResponse userProfile;


    public String getBaseURL() {

//        return "http://dev.starssmiledc.com";   // first URL provided by document
//        return "http://tillthefit.com";
        return "http://10.1.3.83:1223";
    }


    public static HajjCareApplicationClass getInstance() {
        return instance;
    }

    public void onCreate() {

        super.onCreate();
        setDefaultFont("GE_Dinar_One_Light_1.otf", "GE Dinar Two Medium.otf");
        instance = this;
        Log.i(HajjCareApplicationClass.class.getName(), "StarSmilesApplication onCreate");
        HajjCareApplicationClass.context = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    protected void setDefaultFont(String fontName, String fontNameBold) {

        final Typeface bold = Typeface.createFromAsset(getAssets(), fontNameBold);
        final Typeface italic = Typeface.createFromAsset(getAssets(), fontName);
        final Typeface boldItalic = Typeface.createFromAsset(getAssets(), fontNameBold);
        final Typeface regular = Typeface.createFromAsset(getAssets(), fontName);

//		if (Build.VERSION.SDK_INT >= 21) { // Build.VERSION_CODES.LOLLIPOP
//            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
//            newMap.put("serif", regular);
//            newMap.put("monospace", regular);
//            newMap.put("normal", regular);
//            newMap.put("sans", regular);
//            try {
//                final Field staticField = Typeface.class
//                        .getDeclaredField("sSystemFontMap");
//                staticField.setAccessible(true);
//                staticField.set(null, newMap);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        } else {
//
//        }

        Field DEFAULT;
        try {
            DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            DEFAULT.setAccessible(true);
            DEFAULT.set(null, regular);
        } catch (NoSuchFieldException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }

        Field DEFAULT_BOLD;
        try {
            DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            DEFAULT_BOLD.setAccessible(true);
            DEFAULT_BOLD.set(null, bold);
        } catch (NoSuchFieldException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }

        Field sDefaults;
        try {
            sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{regular, bold, italic, boldItalic});
        } catch (NoSuchFieldException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }

        Field monoField;
        try {
            monoField = Typeface.class.getDeclaredField("MONOSPACE");
            monoField.setAccessible(true);
            monoField.set(null, regular);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }

        Field SERIF;
        try {
            SERIF = Typeface.class.getDeclaredField("SERIF");
            SERIF.setAccessible(true);
            SERIF.set(null, regular);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }

        Field SANS_SERIF;
        try {
            SANS_SERIF = Typeface.class.getDeclaredField("SANS_SERIF");
            SANS_SERIF.setAccessible(true);
            SANS_SERIF.set(null, regular);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }


    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }

    public Activity getCurrentShowenActivity() {
        return currentShowenActivity;
    }

    public void setCurrentShowenActivity(Activity currentShowenActivity) {
        this.currentShowenActivity = currentShowenActivity;
    }

    public static Context getStarSmilesAppContext() {
        return HajjCareApplicationClass.context;
    }

    /* Added by amra @30-01-2018
   to enable mocking or not in retrofit dynamic layer
    */
    public boolean isEnabledMockingData() {
        return false;
    }

    public boolean isAppInBackground() {
        return appInBackground;
    }

    public void setAppInBackground(boolean appInBackground) {
        this.appInBackground = appInBackground;
    }

    public static String getToken() {

        return token;
    }

    public static void setToken(String token) {
        HajjCareApplicationClass.token = token;
    }


    public String getLnaguage() {

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        String language;
        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(Constants.LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {

            language = Constants.LANGUAGE_AR.toLowerCase();


        } else {

            language = Constants.LANGUAGE_EN.toLowerCase();
        }
        return language;
    }

//    public static ProfileResponse getUserProfile() {
//        return userProfile;
//    }
//
//    public static void setUserProfile(ProfileResponse userProfile) {
//        HajjCareApplicationClass.userProfile = userProfile;
//    }
}
