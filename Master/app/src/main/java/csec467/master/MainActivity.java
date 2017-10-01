package csec467.master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    PNConfiguration pnConfiguration = new PNConfiguration();

    protected void publish() {
        pnConfiguration.setSubscribeKey("SubscribeKey");
        pnConfiguration.setPublishKey("PublishKey");
        pnConfiguration.setSecure(false);

        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.subscribe()
                .channels(Arrays.asList("my_channel")) // subscribe to channels
                .execute();

        pubnub.publish()
                .message(Arrays.asList("hello", "there"))
                .channel("my_channel")
                .shouldStore(true)
                .usePOST(true)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (status.isError()) {
                            // something bad happened.
                            System.out.println("error happened while publishing: " + status.toString());
                        } else {
                            System.out.println("publish worked! timetoken: " + result.getTimetoken());
                        }
                    }
                });
    }


}
