package krasnov.bookcrossing;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class AddNewBookFragment extends Fragment {

    private FragmentTransaction ft;
    private Button addBook;
    private TextView mName, mBookname, mIsbn, mDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnewbook, container, false);

        mName = view.findViewById(R.id.rName);
        mBookname = view.findViewById(R.id.rBookname);
        mIsbn = view.findViewById(R.id.rISBN);
        mDescription = view.findViewById(R.id.rDescription);

        return view;
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
        addBook = getView().findViewById(R.id.btnAddBook);
        addBook.setOnClickListener(this::onAddClick);
    }
    private void onAddClick(View v) {
        if (isFilled(mName) && isFilled(mBookname) &&
                isFilled(mIsbn) && isFilled(mDescription)) {
            //Book book = new Book ();
            ListFragment fragment = new ListFragment();
            Bundle args = new Bundle();
            Bundle bundle = getArguments();
            args.putString("email", bundle.getString("email"));
            fragment.setArguments(args);
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
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment).commit();
            Toast toast = Toast.makeText(getContext(), "Книга добавлена, ожидайте", Toast.LENGTH_SHORT);
            toast.show();
        }
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
                Bundle bundle = getArguments();
                String email = bundle.getString("email");
                String author = mName.getText().toString().replaceAll(" ", "%20");
                String title = mBookname.getText().toString().replaceAll(" ", "%20");
                String description = mDescription.getText().toString().replaceAll(" ", "%20");
                URLConnection connection = new URL(String.format("http://192.168.1.46:8080/addnewbook?" +
                        "email=%s&" +
                        "author=%s&" +
                        "title=%s&" +
                        "description=%s", email, author, title, description)).openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();

                System.out.println(sb);

            }
            catch (IOException e) {
                System.out.println("EXCEPTION");
            }
            return null;
        }
    }
}
