package krasnov.bookcrossing;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {

    private FragmentTransaction ft;
    FloatingActionButton fab;
    private ArrayList<Book> books = new ArrayList();
    ListView booksList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

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
        booksList = view.findViewById(R.id.booksList);
        ShelfAdapter shelfAdapter = new ShelfAdapter(getContext(), R.layout.book_on_shelf, books);
        booksList.setAdapter(shelfAdapter);
        /*AdapterView.OnItemClickListener itemClickListener = (parent, v, position, id) -> {
            Book selectedState = (Book)parent.getItemAtPosition(position);
        };
        booksList.setOnItemClickListener(itemClickListener);*/
        //Toast.makeText(getContext(), "I was here", Toast.LENGTH_LONG).show();

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
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        });

    }

}
