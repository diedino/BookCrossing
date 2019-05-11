package krasnov.bookcrossing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<Book> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        /*books.add(new Book(new Author("Иван", "Тургенев", "Сергеевич"),
                "Отцы и Дети",
                "123-3123",
                "Rofl",
                "Книга просто великоплепная, про что-то интересное",
                R.drawable.otsiideti));
        books.add(new Book(new Author("Говард", "Лавкрафт", ""),
                "Зов Ктулху",
                "534534-34",
                "Rofl",
                "Про что-то интересное, всем советую",
                R.drawable.zovktulhu));
        books.add(new Book(new Author("Иван", "Тургенев", "Сергеевич"),
                "Отцы и Дети",
                "123-3123",
                "Rofl",
                "Книга просто великоплепная, про что-то интересное",
                R.drawable.otsiideti));
        books.add(new Book(new Author("Говард", "Лавкрафт", ""),
                "Зов Ктулху",
                "534534-34",
                "Rofl",
                "Про что-то интересное, всем советую",
                R.drawable.zovktulhu));

        RecyclerView myrv = (RecyclerView)view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),books);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));
        myrv.setAdapter(myAdapter);*/

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }
}
