package krasnov.bookcrossing;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class AddBookByCodeFragment extends Fragment {
    private TextView code;
    private Button button;
    private FragmentTransaction ft;
    private String email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book_by_code, container, false);
        code = view.findViewById(R.id.rCode);
        Bundle bundle = getArguments();
        email = bundle.getString("email");
        System.out.println("Email: "+email);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilled(code)) {
                    ListFragment fragment = new ListFragment();
                    Bundle args = new Bundle();
                    Bundle bundle = getArguments();
                    args.putString("email", bundle.getString("email"));
                    fragment.setArguments(args);
                    AsyncCall task = new AsyncCall();
                    task.execute();
                    try {
                        task.get(3000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment).commit();
                    Toast toast = Toast.makeText(getContext(), "Книга добавлена, ожидайте", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar)getView().findViewById(R.id.toolbar);
        toolbar.setTitle("Регистрация новой книги");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragment fragment = new ListFragment();
                Bundle args = new Bundle();
                Bundle bundle = getArguments();
                args.putString("email", bundle.getString("email"));
                fragment.setArguments(args);
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        });
    }
    private boolean isFilled(TextView textView) {
        if (textView.getTextSize() == 0)
            return false;
        return true;
    }

    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                String codeforURL = code.getText().toString().toUpperCase();
                String testURL = String.format("http://192.168.1.46:8080/addbookbycode?" +
                        "email=%s&" +
                        "code=%s", email, codeforURL);
                System.out.println(testURL);
                URLConnection connection = new URL(String.format("http://192.168.1.46:8080/addbookbycode?" +
                        "email=%s&" +
                        "code=%s", email, codeforURL)).openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();

                System.out.println(sb);

            } catch (IOException e) {
                System.out.println("EXCEPTION");
                return null;
            }
            return null;
        }

    }
}
