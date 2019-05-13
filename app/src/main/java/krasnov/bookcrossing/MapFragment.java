package krasnov.bookcrossing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    private List<Place> places= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        AsyncCall task = new AsyncCall();
        task.execute();
        try {
            task.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);

        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap = googleMap;

        Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape, getContext().getTheme());
        BitmapDescriptor icon = getMarkerIconFromDrawable(circleDrawable);

        System.out.println("Array places size: "+places.size());
        for (Place place:places
             ) {
            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.getLatitude(),place.getLongitude()))
                    .title(place.getName())
                    .icon(icon));
            marker.setTag(place);
        }

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Place place = (Place) marker.getTag();
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                Bundle args = new Bundle();
                Bundle bundle = getArguments();
                args.putString("name", place.getName());
                args.putString("address", place.getAddress());
                args.putString("email", bundle.getString("email"));
                bottomSheetDialog.setArguments(args);
                bottomSheetDialog.show(getFragmentManager(), "bottomSheet");
                return false;
            }
        });

        CameraPosition moscow = CameraPosition.builder().target(new LatLng(55.751498, 37.618767)).zoom(9.8f).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(moscow));
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL("http://192.168.1.46:8080/allplaces").openConnection();
                double latitude=0, longitude=0;
                String name="", address="";
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();

                JSONArray jsonarray = new JSONArray(sb.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    name = jsonobject.getString("name");
                    System.out.println(name);
                    address = jsonobject.getString("address");
                    System.out.println(address);
                    latitude = Double.valueOf(jsonobject.getString("latitude"));
                    longitude = Double.valueOf(jsonobject.getString("longitude"));
                    places.add(new Place(latitude, longitude, name, address));
                }
                System.out.println(sb);

            }
            catch (IOException e) {
                System.out.println("EXCEPTION");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
