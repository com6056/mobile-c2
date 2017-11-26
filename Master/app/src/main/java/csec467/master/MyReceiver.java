package csec467.master;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Objects.equals(action, "csec467.master.SEND_COMMAND")) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            String corgis = intent.getStringExtra("corgis");
            String selected_action = intent.getStringExtra("action");

            if (Objects.equals(selected_action, "url")) {
                String url = intent.getStringExtra("action_data_1");
                String url_count = intent.getStringExtra("action_data_2");
                new sendCommand().execute(title, message, corgis, selected_action, url, url_count, null);
            }
            else {
                new sendCommand().execute(title, message, corgis, null, null, null, null);
            }
        }
    }

    static private class sendCommand extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String[] params)
        {
            try {
                URL u = null;
                try {
                    u = new URL("https://fcm.googleapis.com/fcm/send");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                JSONObject message = new JSONObject();
                JSONObject notification = new JSONObject();
                JSONObject data = new JSONObject();
                try {
                    message.put("condition", "'corgi' in topics");
                    if (params[0] != null) {
                        notification.put("title", params[0]);
                    }
                    if (params[1] != null) {
                        notification.put("body", params[1]);
                    }
                    message.put("notification", notification);
                    if (params[2] != null) {
                        data.put("corgis", params[2]);
                    }
                    if (Objects.equals(params[3], "url")) {
                        data.put("action", "url");
                        data.put("url", params[4]);
                        data.put("url_count", params[5]);
                    }
                    message.put("data", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key=");
                OutputStream os = conn.getOutputStream();
                os.write(message.toString().getBytes());
                os.close();
                Log.d("response", String.format("%s\n%d", conn.getResponseMessage(), conn.getResponseCode()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
