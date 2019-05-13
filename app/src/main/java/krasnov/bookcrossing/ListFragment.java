package krasnov.bookcrossing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ListFragment extends Fragment {

    private FragmentTransaction ft;
    FloatingActionButton fab;
    private List<Book> books = new ArrayList();
    ListView booksList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        AsyncCall task = new AsyncCall();
        task.execute();
        try {
            task.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        booksList = view.findViewById(R.id.booksList);
        ShelfAdapter shelfAdapter = new ShelfAdapter(getContext(), R.layout.book_on_shelf, books);
        booksList.setAdapter(shelfAdapter);
        AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
            Book selectedState = (Book) parent.getItemAtPosition(position);
            Toast.makeText(getContext(), "I was here", Toast.LENGTH_LONG).show();
        };
        booksList.setOnItemClickListener(itemClickListener);
        System.out.println("I was here earlier");

       /* books.add(new Book("Иван Тургенев",
                "Отцы и Дети",
                "123-3123",
                "Книга просто великоплепная, про что-то интересное",
                R.drawable.otsiideti));
        books.add(new Book("Говард Лавкрафт",
                "Зов Ктулху",
                "534534-34",
                "Про что-то интересное, всем советую",
                R.drawable.zovktulhu));*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = getView().findViewById(R.id.fab);
        super.onCreate(savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.hide();
                AddNewBookFragment fragment = new AddNewBookFragment();
                Bundle args = new Bundle();
                Bundle bundle = getArguments();
                args.putString("email", bundle.getString("email"));
                fragment.setArguments(args);
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        });

    }


    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Bundle bundle = getArguments();
                String email = bundle.getString("email");
                URLConnection connection = new URL("http://192.168.1.46:8080/findbooksbyemail?email=" + email).openConnection();
                String title = "", author = "", description = "", code = "";
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
                    author = jsonobject.getString("author");
                    System.out.println(author);
                    title = jsonobject.getString("title");
                    System.out.println(title);
                    description = jsonobject.getString("description");
                    System.out.println(description);
                    code = jsonobject.getString("code");
                    System.out.println(code);
                    books.add(new Book(author, title, description, code));
                }
                System.out.println(sb);

            } catch (IOException e) {
                System.out.println("EXCEPTION");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
