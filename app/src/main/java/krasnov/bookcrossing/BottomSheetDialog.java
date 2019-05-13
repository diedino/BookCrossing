package krasnov.bookcrossing;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private Button showBooks, addBook;
    private EditText editText;
    private TextView name, address;
    private String email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.place_bottom_sheet, container, false);
        editText = v.findViewById(R.id.editCode);
        showBooks = v.findViewById(R.id.btnShowbooks);
        addBook = v.findViewById(R.id.btnAddbook_bottom);
        Bundle bundle = getArguments();
        email = bundle.getString("email");
        showBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomSheetDialog.this.getActivity(), BookOnPlace.class);
                Bundle bundle = getArguments();
                intent.putExtra("email", bundle.getString("email"));
                intent.putExtra("placename", bundle.getString("name"));
                startActivity(intent);
            }
        });
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getActivity(), BottomBarActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (TextView) view.findViewById(R.id.nameplace);
        address = (TextView) view.findViewById(R.id.shortadress);
        name.setText(getArguments().getString("name").trim());
        address.setText(getArguments().getString("address").trim());
    }

    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String placename = name.getText().toString().replaceAll(" ", "%20");
                String code = editText.getText().toString().toUpperCase();
                String testURL = String.format("http://192.168.1.46:8080/addbookplace?" +
                        "email=%s&" +
                        "code=%s&" +
                        "placename=%s", email, code, placename);
                URLConnection connection = new URL(String.format("http://192.168.1.46:8080/addbookplace?" +
                        "email=%s&" +
                        "code=%s&" +
                        "placename=%s", email, code, placename)).openConnection();
                double latitude=0, longitude=0;
                String name="", address="";
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();
                System.out.println(testURL);
                System.out.println(sb);

            }
            catch (IOException e) {
                System.out.println("EXCEPTION");
            }
            return null;
        }
    }
}
