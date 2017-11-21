package csec467.master;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class Send_push extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_push);
        make_call(this);
    }

    public void make_call(Context v) {
        String command_text = getIntent().getStringExtra("command");
        Toast.makeText(v, "Sending command:  " + command_text, Toast.LENGTH_LONG).show();

        Boolean success = true;
        if (success) {
            Toast.makeText(v, "Successful submission!", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Send_push.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        }
    }
    public void auth() throws FileNotFoundException {

        FileInputStream serviceAccount = new FileInputStream("this file doesn't exist yet");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://mobile-c2.firebaseio.com/")
            .build();

        FirebaseApp.initializeApp(options);
    }


}
