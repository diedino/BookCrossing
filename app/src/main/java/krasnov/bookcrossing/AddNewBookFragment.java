package krasnov.bookcrossing;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addnewbook, container, false);
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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                /*
                transaction.setCustomAnimations(R.anim.exit_from_right,
                        R.anim.enter_from_right,
                        R.anim.exit_from_right,
                        R.anim.enter_from_right);
                transaction.addToBackStack(null);
                */
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });
        Spinner spinner = getView().findViewById(R.id.genre_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.genre_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(0x00000000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                ((TextView) parentView.getChildAt(0)).setTextColor(0x00000000);
            }

        });
    }
}
