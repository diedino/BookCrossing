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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

public class AddNewBookFragment extends Fragment {

    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnewbook, container, false);

        Spinner spinner = view.findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genre_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.GRAY);
            }

        });

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
                ft.setCustomAnimations(R.anim.slide_in_left,
                        R.anim.slide_in_right);
                ft.addToBackStack(null);
                ft.add(R.id.fragment_container, fragment).commit();
            }
        });
    }
}
