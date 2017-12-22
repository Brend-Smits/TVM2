package activitymain.mainactivity;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Rubbertjuh on 12/22/2017.
 */

public class WebService {

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    public String getData(){
        StrictMode.setThreadPolicy(policy);
        try {
            String data = URLEncoder.encode("Element1","UTF-8")+"="+ URLEncoder.encode("Value 1", "UTF-8");
            data += URLEncoder.encode("Element2", "UTF-8")+"="+ URLEncoder.encode("Value 2", "UTF-8");
            data += URLEncoder.encode("Element3", "UTF-8")+"="+ URLEncoder.encode("Value 3", "UTF-8");
            URL url = new URL("http://i381936.hera.fhict.nl/vraagid.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getData();
    }


}
