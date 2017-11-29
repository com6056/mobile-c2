package csec467.bot;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {

            if (!Objects.equals(remoteMessage.getData().get("corgis"), "0")) {
                Intent bonus = new Intent("csec467.bot.BONUS_CLICKS");
                bonus.putExtra("corgis", remoteMessage.getData().get("corgis"));
                sendBroadcast(bonus);
            }

            if (remoteMessage.getData().get("action") != null) {
                if (Objects.equals(remoteMessage.getData().get("action"), "url")) {
                    Intent openURL = new Intent("csec467.bot.URL");
                    openURL.putExtra("url", remoteMessage.getData().get("url"));
                    openURL.putExtra("url_count", remoteMessage.getData().get("url_count"));
                    sendBroadcast(openURL);
                }
            }
        }
    }
}