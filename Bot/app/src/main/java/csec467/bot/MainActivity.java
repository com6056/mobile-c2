package csec467.bot;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button increment = (Button)findViewById(R.id.increment);
        View.OnClickListener ev = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView corgi_text = (TextView)findViewById(R.id.corgis_text);
                String text = corgi_text.getText().toString();
                String[] text_array = text.split(" ");
                Integer count = Integer.parseInt(text_array[0]);
                Integer new_value = count + 1;
                String newString = new_value.toString() + " " + text_array[1];
                corgi_text.setText(newString);
            }
        };
        increment.setOnClickListener(ev);
    }


}
