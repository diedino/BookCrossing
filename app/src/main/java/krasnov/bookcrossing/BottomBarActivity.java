package krasnov.bookcrossing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class BottomBarActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private String mEmail;
    private String mName;
    private List<Book> books;
    private String name, email;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        personData();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        MapFragment fragment = new MapFragment();
        args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.bottombaritem_map:
                            selectedFragment = new MapFragment();
                            args = new Bundle();
                            args.putString("email", email);
                            selectedFragment.setArguments(args);
                            break;
                        case R.id.bottombaritem_search:
                            selectedFragment = new SearchFragment();
                            //selectedFragment = new TestFragment();
                            break;
                        case R.id.bottombaritem_list:
                            selectedFragment = new ListFragment();
                            args = new Bundle();
                            args.putString("email", email);
                            args.putString("name", name);
                            selectedFragment.setArguments(args);
                            break;
                        case R.id.bottombaritem_person:
                            selectedFragment = new PersonFragment();
                            args = new Bundle();
                            args.putString("email", email);
                            args.putString("name", name);
                            selectedFragment.setArguments(args);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                });


    }

    private void personData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("I'm in personData()");
                    Intent intent = getIntent();
                    String emailstr = intent.getStringExtra("email");
                    URLConnection connection = new URL("http://192.168.1.46:8080/findbyemail?email=" + emailstr).openConnection();
                    InputStream is = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);

                    char[] buffer = new char[256];
                    int rc;

                    StringBuilder sb = new StringBuilder();

                    while ((rc = reader.read(buffer)) != -1)
                        sb.append(buffer, 0, rc);

                    reader.close();

                    JSONObject jsonObject = new JSONObject(sb.toString());

                    System.out.println("NAME:" +jsonObject.getString("name"));
                    name = jsonObject.getString("name");
                    System.out.println("Name: "+name);
                    email = jsonObject.getString("email");
                    System.out.println("Email: "+email);
                    System.out.println(sb);

                } catch (IOException e) {
                    System.out.println("EXCEPTION");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}