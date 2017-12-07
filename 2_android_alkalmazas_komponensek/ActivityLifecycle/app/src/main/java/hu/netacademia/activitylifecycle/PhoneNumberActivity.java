package hu.netacademia.activitylifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static hu.netacademia.activitylifecycle.MainActivity.KEY_USERNAME;

public class PhoneNumberActivity extends AppCompatActivity {

    public static final String KEY_PHONENUMBER = "PHONENUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Intent caller = getIntent();
        String username = caller.getStringExtra(KEY_USERNAME);
        TextView username_felirat = findViewById(R.id.username_felirat);
        username_felirat.setText("Kedves " + username + ", kérlek add meg a telefonszámod!");
        Log.d("TelefonszamKepernyo", "onCreate called");

        final EditText telefonszam_bevitel = findViewById(R.id.telefonszam_bevitel);
        Button ok_button = findViewById(R.id.ok_gomb);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telefonszam = telefonszam_bevitel.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_PHONENUMBER, telefonszam);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TelefonszamKepernyo", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TelefonszamKepernyo", "onResume called");
    }

    @Override
    protected void onPause() {
        Log.d("TelefonszamKepernyo", "onPause called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("TelefonszamKepernyo", "onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("TelefonszamKepernyo", "onDestroy called");
        super.onDestroy();
    }
}
