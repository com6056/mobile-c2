package csec467.bot;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {

    String TAG = "abc123";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (remoteMessage.getData().get("corgis") != null) {
                Log.d(TAG, "corgis: " + remoteMessage.getData().get("corgis"));
                Intent bonus = new Intent("csec467.bot.BONUS_CLICKS");
                bonus.putExtra("corgis", remoteMessage.getData().get("corgis"));
                sendBroadcast(bonus);
            }

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}