package hajjcare.hajj.com.hajjcare.control;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;

//import hajjcare.hajj.com.hajjcare.BuildConfig;
//import hajjcare.hajj.com.hajjcare.managers.LoginManager;
//import hajjcare.hajj.com.hajjcare.managers.OperationManager;
//import hajjcare.hajj.com.hajjcare.models.User;
//import hajjcare.hajj.com.hajjcare.models.requests.AddReplayRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.AskDoctorRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.BookingRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.InsuranceRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.LoginRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.NotifyRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.RegisterRequest;
//import hajjcare.hajj.com.hajjcare.models.requests.UpdateProfileRequest;
//import hajjcare.hajj.com.hajjcare.models.responses.LoginResponse;
//import hajjcare.hajj.com.hajjcare.models.responses.RegisterResponse;
//import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;
//import hajjcare.hajj.com.hajjcare.utils.LocaleHelper;
//import hajjcare.hajj.com.hajjcare.views.activities.BaseActivity;
//import hajjcare.hajj.com.hajjcare.views.activities.LoginActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

import hajjcare.hajj.com.hajjcare.managers.LoginManager;
import hajjcare.hajj.com.hajjcare.managers.OperationManager;
import hajjcare.hajj.com.hajjcare.models.LoginRequest;
import hajjcare.hajj.com.hajjcare.models.LoginResponse;
import hajjcare.hajj.com.hajjcare.models.network.HelpMeRequest;
import hajjcare.hajj.com.hajjcare.networklayer.OnCallComplete;

/**
 * Created by amra on 02/03/2018.
 */

public class Controller {

    private static Controller instance;
    private static String MY_ID;
    private static LocationManager locationManager;
    private static boolean isGPSEnabled;
    private static boolean isNetworkEnabled;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static boolean mLocationPermissionGranted;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // The minimum distance to change Updates in meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    public  static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void login(final OnCallComplete onCallComplete, LoginRequest loginRequest) {

        LoginManager.getInstance().doLogin(new OnCallComplete() {
            @Override
            public void onSuccess(Object object) {

                onCallComplete.onSuccess((object));
            }

            @Override
            public void onFailure(Exception appException) {

                onCallComplete.onFailure(appException);
            }
        }, loginRequest);

    }

    public void helpMe(final OnCallComplete onCallComplete, HelpMeRequest helpMeRequest) {

        OperationManager.getInstance().helpMe(new OnCallComplete() {
            @Override
            public void onSuccess(Object object) {

                onCallComplete.onSuccess((object));
            }

            @Override
            public void onFailure(Exception appException) {

                onCallComplete.onFailure(appException);
            }
        }, helpMeRequest);

    }

    public static Location getLocation(Context context, GoogleApiClient googleApiClient) {
        Location location = null;
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled) {

                showSettingsAlert(context);
            } else {
//                context.canGetLocation = true;
                // First get location from Network Provider
                if (isGPSEnabled) {
                    if (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;


//                                locationManager.requestLocationUpdates(
//                                        LocationManager.GPS_PROVIDER,
//                                        MIN_TIME_BW_UPDATES,
//                                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) context);
//
//                                Log.d("GPS Enabled", "GPS Enabled");
//                                if (locationManager != null) {
//                                    Criteria criteria = new Criteria();
//                                    location = locationManager
//                                            .getLastKnownLocation(      locationManager.getBestProvider(criteria, false));

                        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                        }
                    }

                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }

            }

            // if GPS Enabled get lat/long using GPS Services



        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public static void showSettingsAlert(final Context context){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
//
//    public void signUp(final OnCallComplete onCallComplete, RegisterRequest registerRequest) {
//
//        LoginManager.getInstance().signUp(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                setLoginToken(((RegisterResponse) object).getToken());
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, registerRequest);
//
//    }
//
//    public void getProfileData(final OnCallComplete onCallComplete) {
//
//        LoginManager.getInstance().getProfileData(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        });
//
//    }
//
//    public void changeLanguage(BaseActivity activity) {
//
//        Resources res = activity.getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//
//        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(Constants.LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {
//            LocaleHelper.setLocale(activity, Constants.LANGUAGE_EN.toLowerCase());
//            conf.setLocale(new Locale(Constants.LANGUAGE_EN.toLowerCase()));
//            res.updateConfiguration(conf, dm);
//            activity.createConfigurationContext(conf);
//
//
//        } else {
//            LocaleHelper.setLocale(activity, Constants.LANGUAGE_AR.toLowerCase());
//            conf.setLocale(new Locale(Constants.LANGUAGE_AR.toLowerCase()));
//            res.updateConfiguration(conf, dm);
//            activity.createConfigurationContext(conf);
//        }
//        activity.recreate();
//
////        Intent startIntent = new Intent((activity), SplashActivity.class);
////        activity.finish();
//        try {
////            activity.startActivity(startIntent);
//        } catch (Throwable throwable) {
//            if (BuildConfig.DEBUG)
//                throwable.printStackTrace();
//        }
//    }
//
//    public void getPosts(final OnCallComplete onCallComplete, String language, int page) {
//
//        LoginManager.getInstance().getPosts(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, language, page);
//
//    }
//
//    public void getServices(final OnCallComplete onCallComplete, int page) {
//
//        OperationManager.getInstance().getServices(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage(), page);
//
//    }
//

//    public void postInsurance(final OnCallComplete onCallComplete, InsuranceRequest insuranceRequest) {
//
//        OperationManager.getInstance().postInsurance(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, insuranceRequest);
//
//    }

//
//    public void getBranches(final OnCallComplete onCallComplete) {
//
//        OperationManager.getInstance().getBranches(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage());
//
//    }

//    public void getOffers(final OnCallComplete onCallComplete) {
//
//        OperationManager.getInstance().getOffers(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage());
//
//    }

//    public void book(final OnCallComplete onCallComplete, BookingRequest bookingRequest) {
//
//        OperationManager.getInstance().book(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, bookingRequest);
//
//    }

//    public void getEvents(final OnCallComplete onCallComplete, int page) {
//
//        OperationManager.getInstance().getEvents(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage(), page);
//
//    }

//    public void getBranchById(final OnCallComplete onCallComplete, int id) {
//
//        OperationManager.getInstance().getBranchById(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage(), id);
//
//    }


//    public void knowYourDoctor(final OnCallComplete onCallComplete) {
//
//        OperationManager.getInstance().knowYourDoctor(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage());
//
//    }
//
//    public void beforeAndAfter(final OnCallComplete onCallComplete) {
//
//        OperationManager.getInstance().beforeAndAfter(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, HajjCareApplicationClass.getInstance().getLnaguage());
//
//    }
//
//    public String getLoginToken() {
//        return loginToken;
//    }
//
//    public void setLoginToken(String loginToken) {
//        this.loginToken = loginToken;
//    }

//    public void logout() {
//        HajjCareApplicationClass.getInstance().getCurrentShowenActivity().startActivity(new Intent(HajjCareApplicationClass.getInstance().getCurrentShowenActivity(), LoginActivity.class));
//    }

//    public void addReplay(final OnCallComplete onCallComplete, AddReplayRequest addReplayRequest, int id) {
//
//        OperationManager.getInstance().addReplay(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, addReplayRequest, id);
//
//    }

//
//    public void askDoctor(final OnCallComplete onCallComplete, AskDoctorRequest askDoctorRequest, int doctorId) {
//
//        OperationManager.getInstance().askDoctor(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, askDoctorRequest, doctorId);
//
//    }

//
//    public void getMessages(final OnCallComplete onCallComplete, int page) {
//
//        OperationManager.getInstance().getMessages(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, page, HajjCareApplicationClass.getInstance().getLnaguage());
//
//    }
//

//
//    public void getReplies(final OnCallComplete onCallComplete, int page, int chatId) {
//
//        OperationManager.getInstance().getReplies(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, page, HajjCareApplicationClass.getInstance().getLnaguage(), chatId);
//
//    }
//    public void UpdateProfile(final OnCallComplete onCallComplete, UpdateProfileRequest updateProfileRequest) {
//
//        OperationManager.getInstance().UpdateProfile(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, updateProfileRequest);
//
//    }
//
//    public void notify(final OnCallComplete onCallComplete, NotifyRequest notifyRequest) {
//
//        OperationManager.getInstance().notify(new OnCallComplete() {
//            @Override
//            public void onSuccess(Object object) {
//                onCallComplete.onSuccess((object));
//            }
//
//            @Override
//            public void onFailure(Exception appException) {
//
//                onCallComplete.onFailure(appException);
//            }
//        }, notifyRequest);
//
//    }
//
//    public static User getUser() {
//        return user;
//    }
//
//    public static void setUser(User user) {
//        Controller.user = user;
//    }


    public static String getMyId() {
        return MY_ID;
    }

    public static void setMyId(String myId) {
        MY_ID = myId;
    }
}
