package krasnov.bookcrossing;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PersonFragment extends Fragment {

    private static final int SELECTED_PICTURE = 1;
    ImageView imgClick;
    private TextView mEmail, mName;
    private String name, email;
    private Button mSetting;
    private FragmentTransaction ft;
    private String taken, given;
    private TextView mTaken, mGiven;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        System.out.println("CreateView name: "+name);
        email = bundle.getString("email");
        System.out.println("CreateView email: "+email);
        mTaken = v.findViewById(R.id.taken);
        mGiven = v.findViewById(R.id.given);
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
        mTaken.setText(taken);
        System.out.println("Count taken books: "+taken);
        mGiven.setText(given);
        System.out.println("Count given books: "+given);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgClick = (ImageView) getView().findViewById(R.id.img);
        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECTED_PICTURE);
            }
        });
        mSetting = getView().findViewById(R.id.settingBtn);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment fragment = new SettingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("name", name);
                fragment.setArguments(bundle);
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, fragment).commit();
            }
        });
        System.out.println("I'm in Created");
        System.out.println("Created name: "+name);
        mEmail = getView().findViewById(R.id.tv_email);
        mName = getView().findViewById(R.id.tv_name);
        mEmail.setText(email);
        mName.setText(name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgClick.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getContext(), "Вы не выбрали изображения", Toast.LENGTH_LONG).show();
        }
    }
    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL("http://192.168.1.46:8080/findbyemail?email="+email).openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();

                JSONObject jsonobject = new JSONObject(sb.toString());
                    taken = jsonobject.getString("taken");
                    System.out.println(taken);
                    given = jsonobject.getString("given");
                    System.out.println(given);
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
