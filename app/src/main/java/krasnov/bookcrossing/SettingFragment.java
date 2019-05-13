package krasnov.bookcrossing;

import android.content.Context;
import android.net.Uri;
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

public class SettingFragment extends Fragment {

    private TextView mEmail, mNewPassword, mRepNewPassword;
    private Button btnChange;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.rEmail);
        mNewPassword = view.findViewById(R.id.rNewPass);
        mRepNewPassword = view.findViewById(R.id.rRepNewPass);
        btnChange = view.findViewById(R.id.btnChangePass);

        Toolbar toolbar = (Toolbar)getView().findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonFragment fragment = new PersonFragment();
                Bundle args = new Bundle();
                Bundle bundle = getArguments();
                args.putString("email", bundle.getString("email"));
                args.putString("name", bundle.getString("name"));
                fragment.setArguments(args);
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        });

        btnChange.setOnClickListener(this::onChangeClick);
    }

    private void onChangeClick(View v) {
        if (isFilled(mEmail) && isFilled(mNewPassword) &&
                isFilled(mRepNewPassword) && (mNewPassword.getText() == mRepNewPassword.getText())) {
            PersonFragment fragment = new PersonFragment();
            Bundle args = new Bundle();
            Bundle bundle = getArguments();
            args.putString("email", bundle.getString("email"));
            fragment.setArguments(args);
            ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment).commit();
            Toast toast = Toast.makeText(getContext(), "Пароль сменен", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean isFilled(TextView textView) {
        if (textView.getTextSize() == 0)
            return false;
        return true;
    }
}
