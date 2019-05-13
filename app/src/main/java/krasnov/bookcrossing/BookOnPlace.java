package krasnov.bookcrossing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookOnPlace extends AppCompatActivity {
    private List<Book> books = new ArrayList();
    ListView booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_on_place);

         books.add(new Book("Иван Тургенев",
                "Отцы и Дети",
                "123-3123",
                "Книга просто великоплепная, про что-то интересное",
                R.drawable.otsiideti));
        books.add(new Book("Говард Лавкрафт",
                "Зов Ктулху",
                "534534-34",
                "Про что-то интересное, всем советую",
                R.drawable.zovktulhu));
        booksList = findViewById(R.id.booksOnPlace);
        ShelfAdapter shelfAdapter = new ShelfAdapter(getApplicationContext(), R.layout.book_on_shelf, books);
        booksList.setAdapter(shelfAdapter);
        AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
            Book selectedState = (Book) parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(), "I was here", Toast.LENGTH_LONG).show();
        };
        booksList.setOnItemClickListener(itemClickListener);
    }
}
