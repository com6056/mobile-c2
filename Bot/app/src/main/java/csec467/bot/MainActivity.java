package csec467.bot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences("corgi", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        String corgi_counter = sharedPref.getString("corgi_counter", null);

        Button corgiBtn = (Button) findViewById(R.id.corgiBtn);
        final TextView corgiTxt = (TextView) findViewById(R.id.corgiTxt);

        if (corgi_counter == null) {
            corgi_counter = "0 Corgis!";
            corgiTxt.setText(corgi_counter);
        } else {
            corgiTxt.setText(corgi_counter);
        }

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("corgis") != null) {
                addBonus(getIntent());
            }
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Toast.makeText(this, "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
//            }
        }

        View.OnClickListener corgi_clicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer cur_count = Integer.parseInt(corgiTxt.getText().toString().split(" ")[0].replace(",", ""));
                Integer new_count = cur_count + 1;
                if (new_count == 1) {
                    String new_text = NumberFormat.getNumberInstance(Locale.US).format(new_count) + " Corgi!";
                    corgiTxt.setText(new_text);
                    editor.putString("corgi_counter", new_text).apply();
                } else {
                    String new_text = NumberFormat.getNumberInstance(Locale.US).format(new_count) + " Corgis!";
                    corgiTxt.setText(new_text);
                    editor.putString("corgi_counter", new_text).apply();
                }
            }
        };
        corgiBtn.setOnClickListener(corgi_clicker);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.getExtras().getString("corgis") != null) {
                    addBonus(intent);
                }
            }
        }
    };

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("csec467.bot.BONUS_CLICKS");
        registerReceiver(receiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    public void addBonus(Intent intent) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("corgi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String corgi_counter = sharedPref.getString("corgi_counter", null);
        TextView corgiTxt = (TextView) findViewById(R.id.corgiTxt);

        if (corgi_counter == null) {
            corgi_counter = "0 Corgis!";
            corgiTxt.setText(corgi_counter);
        } else {
            corgiTxt.setText(corgi_counter);
        }

        Integer cur_count = Integer.parseInt(corgi_counter.split(" ")[0].replace(",", ""));
        Integer bonus_count = Integer.parseInt(intent.getExtras().getString("corgis"));
        Integer new_count = cur_count + bonus_count;
        String new_text = NumberFormat.getNumberInstance(Locale.US).format(new_count) + " Corgis!";
        corgiTxt.setText(new_text);
        editor.putString("corgi_counter", new_text).apply();
        Toast.makeText(getApplicationContext(), "Congrats! You have earned " + NumberFormat.getNumberInstance(Locale.US).format(bonus_count) + " Corgi Clicks!", Toast.LENGTH_LONG).show();
    }
}


