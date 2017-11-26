package csec467.botnet_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);

        View.OnClickListener ev = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText command = (EditText)findViewById(R.id.command);
                String command_text = command.getText().toString();
                Intent myIntent = new Intent(MainActivity.this, send_push.class);
                myIntent.putExtra("command", command_text);
                startActivity(myIntent);
                finish();
            }
        };

        button.setOnClickListener(ev);
    }
}
