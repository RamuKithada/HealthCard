package com.prachatech.appointment.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import com.google.android.gms.maps.model.LatLng;

/**
 * Created by VOLIVE on 11/17/2017.
 */

public class MapUtil {

    @SuppressLint("LongLogTag")
    public static String getLatLongToAddress(double latitude, double longitude, Context context) {
        Geocoder geocoder;
        List<Address> addresses;
        String strAdd = "";
        StringBuilder strReturnedAddress = new StringBuilder("");
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            strReturnedAddress.append(address + "\n").append(city + "\n").append(state + "\n").append(country + "\n").append(postalCode + "\n").append(knownName + "\n");
            Log.e("My Current loction address", strAdd);
            return strReturnedAddress.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return strReturnedAddress.toString();
    }

    private String getAddress(double latitude, double longitude, Context context) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

  /*  public static LatLng reverseGeocoding(Context context, String locationName) {

        Geocoder geocoder;
        Department<Address> addresses;
        geocoder = new Geocoder(context);

        Log.e("reverseGeocoding", "reverseGeocoding");

        try {
            addresses = geocoder.getFromLocationName(locationName, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Log.e("My Current location address", "");
            return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

        } catch (IOException e) {
            Log.e("IOException", e.toString());
            e.printStackTrace();
        }
        return null;
    }*/
}
