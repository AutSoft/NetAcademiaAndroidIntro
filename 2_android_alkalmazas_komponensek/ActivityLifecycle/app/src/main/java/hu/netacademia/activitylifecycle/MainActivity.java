package hu.netacademia.activitylifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static hu.netacademia.activitylifecycle.PhoneNumberActivity.KEY_PHONENUMBER;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 101;
    public static final String KEY_USERNAME = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("FoKepernyo","onCreate called");

        final TextView felirat=findViewById(R.id.felirat);
        final EditText bevitel=findViewById(R.id.bevitel);


        Button navigacio=findViewById(R.id.navigacio);
        navigacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PhoneNumberActivity.class);
                intent.putExtra(KEY_USERNAME,bevitel.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE) {
            if (resultCode==RESULT_OK) {
                String phoneNumber=data.getStringExtra(KEY_PHONENUMBER);
                TextView telefonszam=findViewById(R.id.telefonszam);
                telefonszam.setText("Megadott telefonszám:"+phoneNumber);
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("FoKepernyo","onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("FoKepernyo","onResume called");
    }

    @Override
    protected void onPause() {
        Log.d("FoKepernyo","onPause called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("FoKepernyo","onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("FoKepernyo","onDestroy called");
        super.onDestroy();
    }
}
