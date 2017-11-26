package csec467.master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner action = findViewById(R.id.actionSpin);
        List<String> actions = Arrays.asList("None", "URL");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, actions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        action.setAdapter(adapter);

        Button button = findViewById(R.id.submitBtn);

        View.OnClickListener ev = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendCommand = new Intent("csec467.master.SEND_COMMAND");

                CheckBox notify = findViewById(R.id.notifyBox);
                if (notify.isChecked()) {
                    EditText title = findViewById(R.id.titleTxt);
                    EditText message = findViewById(R.id.messageTxt);
                    sendCommand.putExtra("title", title.getText().toString());
                    sendCommand.putExtra("message", message.getText().toString());
                }
                else {
                    sendCommand.putExtra("title", (String) null);
                    sendCommand.putExtra("message", (String) null);
                }

                String selectedAction = action.getSelectedItem().toString();
                if (Objects.equals(selectedAction, "URL")) {
                    sendCommand.putExtra("action", "url");
                    EditText actionData1 = findViewById(R.id.actionData1Txt);
                    EditText actionData2 = findViewById(R.id.actionData2Txt);
                    sendCommand.putExtra("action_data_1", actionData1.getText().toString());
                    sendCommand.putExtra("action_data_2", actionData2.getText().toString());
                }

                EditText corgis = findViewById(R.id.corgisTxt);
                if (corgis != null) {
                    sendCommand.putExtra("corgis", corgis.getText().toString());
                }

                sendBroadcast(sendCommand);
            }
        };

        button.setOnClickListener(ev);
    }
}
