package com.androidtutorialpoint.googlemapsnearbyplaces;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by avipatil on 3/1/2017.
 */

public class AnotationFetch extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {
    static List<HashMap<String, String>> nearByResult;
    GoogleMap mMap;
    MapsActivity mapActivity;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //ShowNearbyPlaces(nearByResult);
       // Log.d("onPostExecute",nearByResult.toString());

    }
    public void ShowNearbyPlaces(List<HashMap<String, String>> nearByResult) {
        for (int i = 0; i < nearByResult.size(); i++) {
            Log.d("onPostExecute","Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace =nearByResult.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng EndP = new LatLng(lat, lng);
            //Intent it=new Intent();

            double lati= mapActivity.latitude;
            double lagi=mapActivity.longitude;
            LatLng  StartP=new LatLng(lati,lagi);
            float dist= CalculationByDistance(StartP,EndP);

            markerOptions.position(EndP);
            markerOptions.title(placeName+"::"+vicinity );
            markerOptions.snippet(String.valueOf(dist*1000)+"Meter");
            //markerOptions.getInfoWindowAnchorU();


            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(EndP));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            // mMap.addPolygon(new PolygonOptions().add(StartP,EndP).fillColor(Color.WHITE).strokeColor(Color.BLACK).strokeWidth(7));


            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(lati, lagi))
                    .radius(1000); // In meters

// Get back the mutable Circle
            Circle circle = mMap.addCircle(circleOptions);


            mMap.addPolyline(new PolylineOptions().add(StartP,EndP).width(5).color(Color.BLUE).geodesic(true).clickable(true));
            //line.setJointType(JointType.ROUND);


        }
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                                            @Override
                                            public void onPolylineClick(Polyline polyline) {
                                                polyline.setColor(polyline.getColor() ^ 0x00ffffff);
                                            }
                                        }
        );

        // mMap.addPolygon(new PolygonOptions().add(StartP,EndP))
    }

    public float CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return (float) (Radius * c);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        String name=marker.getTitle();
//       String  Dist=marker.getSnippet();
//        //DetailsActivity.details=name+""+Dist;
//        // Intent it= new Intent(this,DetailsActivity.class)
//        //Intent myIntent = new Intent(GetNearbyPlacesData.this.getActivity(), MyClass.class);
//        //Intent myIntent = new Intent(GetNearbyPlacesData.this,DetailsActivity.class);
//
//        return false;
//    }
}
