package extensibleapps.vandy.mooc.locationtracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Service that logs the device's location in a database when a client requests
 * it.
 */
public class LocationLogService extends Service {

    private LocationManager mLocationManager;

    // Binder given to clients to reference service
    private final IBinder mBinder = new LocalBinder();

    // Binder class used to access the service
    public class LocalBinder extends Binder {
        LocationLogService getService() {
            return LocationLogService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Tag used for class identification during logging.
     */
    protected final static String TAG =
            LocationLogService.class.getSimpleName();

    /**
     * Constant key used to store and access the description provided by the
     * client in the Intent used to start this service.
     */
    public static final String DESCRIPTION_KEY = "descKey";

    /**
     * Manager that handles writing location info in a database.
     */
    private LocLogDBManager mDBManager = null;

    public LocationLogService() {
    }

    /**
     * Method called by the Android started service framework whenever a client
     * starts this service with an intent.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v(TAG, "Service Started");

        // TODO | Perform any start up actions, gather the required data, and store said data in the database using the LocLogDBManger.

        // Check if the required permissions are granted before proceeding
        if (checkLocationPermissions()) {
            // Get the description provided by the client
            String description = intent.getStringExtra(DESCRIPTION_KEY);

            // Get the last known location from the LocationManager
            Location lastKnownLocation = getLastKnownLocation();

            // Store the location data in the database using the LocLogDBManager
            if (lastKnownLocation != null) {
                storeLocationData(lastKnownLocation, description);
            }
        }

        // Stop the service once the logging is complete
        stopSelf(startId);

        // Returning START_NOT_STICKY means that in the event that the
        // service is killed while started (after returning from this method)
        // it won't be started again unless it is started explicitly.
        return START_NOT_STICKY;
    }

    /**
     * Perform one-time setup procedures.
     */
    @Override
    public void onCreate() {
        // Create an instance of the location database manager
        mDBManager = new LocLogDBManager(getApplicationContext());
    }

    /**
     * Clean up resources
     */
    @Override
    public void onDestroy() {
        // Nothing to clean up.
    }

    /**
     * Check if the required location permissions are granted.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkLocationPermissions() {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Get the last known location from the LocationManager.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation = null;

        // Check if the required permissions are granted before requesting location updates
        if (checkLocationPermissions()) {
            List<String> providers = mLocationManager.getProviders(true);
            for (String provider : providers) {
                @SuppressLint("MissingPermission")
                Location location = mLocationManager.getLastKnownLocation(provider);
                if (location != null) {
                    if (lastKnownLocation == null || location.getTime() > lastKnownLocation.getTime()) {
                        lastKnownLocation = location;
                    }
                }
            }
        }

        return lastKnownLocation;
    }

    /**
     * Store the location data in the database using the LocLogDBManager.
     */
    private void storeLocationData(Location location, String description) {
        String time = String.valueOf(System.currentTimeMillis());
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        mDBManager.storeLocationData(time, latitude, longitude, description);
    }
}
