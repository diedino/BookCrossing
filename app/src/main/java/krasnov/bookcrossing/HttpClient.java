package krasnov.bookcrossing;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    // Sorry
    public String request(String uri){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            String ans = buf.toString().substring(0, buf.toString().length() - 1);
            Log.i("Bookcrossing", "ans:" + ans);
            return ans;

//            try {
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                BufferedReader bin = new BufferedReader(new InputStreamReader(in));
//                // temporary string to hold each line read from the reader.
//                String inputLine;
//                while ((inputLine = bin.readLine()) != null) {
//                    sb.append(inputLine);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                // regardless of success or failure, we will disconnect from the URLConnection.
//                urlConnection.disconnect();
//            }
        }
        catch (IOException ex) {
            Log.e("Bookcrossing", ex.getMessage());
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            urlConnection.disconnect();
        }
        return "Error";
    }
}