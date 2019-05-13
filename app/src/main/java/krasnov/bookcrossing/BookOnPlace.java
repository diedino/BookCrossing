package krasnov.bookcrossing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BookOnPlace extends AppCompatActivity {
    private List<Book> books = new ArrayList();
    ListView booksList;
    private String placename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_on_place);
        Intent intent = getIntent();
        placename = intent.getStringExtra("placename");
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
        booksList = findViewById(R.id.booksOnPlace);
        ShelfAdapter shelfAdapter = new ShelfAdapter(getApplicationContext(), R.layout.book_on_shelf, books);
        booksList.setAdapter(shelfAdapter);
        AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
            Book selectedState = (Book) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), "I was here", Toast.LENGTH_LONG).show();
        };
        booksList.setOnItemClickListener(itemClickListener);
    }

    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String testURL = "http://192.168.1.46:8080/findbooksbyemail?placename="+placename.replaceAll(" ","%20");
                System.out.println(testURL);
                URLConnection connection = new URL("http://192.168.1.46:8080/findbooksbyplace?placename="+placename.replaceAll(" ","%20")).openConnection();
                String author="", title="", description="", isbn="";
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
                    isbn = jsonobject.getString("isbn");
                    System.out.println(isbn);
                    books.add(new Book(author, title, isbn, description));
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
