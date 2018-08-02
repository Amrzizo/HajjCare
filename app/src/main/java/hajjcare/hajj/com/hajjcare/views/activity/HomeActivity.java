package hajjcare.hajj.com.hajjcare.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import hajjcare.hajj.com.hajjcare.control.Controller;
import hajjcare.hajj.com.hajjcare.views.fragments.HomeFragment;


/**
 * Created by amra on 2/5/2018.
 */

public class HomeActivity extends BaseActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private HomeFragment homeFragment;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        homeFragment = new HomeFragment();
        addFragmentToView(homeFragment);
    }



    @Override
    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
//        homeFragment.setLocation(location);
        if (homeFragment.isHelpRequestSent()) {
            homeFragment.sendLocation();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public Location getLocation(){
       mLocation =  Controller.getLocation(this, googleApiClient);
       return mLocation;
    }
}
