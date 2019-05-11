package krasnov.bookcrossing;

import android.graphics.Color;
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
import android.widget.Toolbar;

public class AddNewBookFragment extends Fragment {

    private FragmentTransaction ft;
    private Button addBook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnewbook, container, false);


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
        ListFragment fragment = new ListFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment).commit();
    }
}
