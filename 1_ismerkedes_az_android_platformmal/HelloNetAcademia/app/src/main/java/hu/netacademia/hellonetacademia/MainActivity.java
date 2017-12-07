package hu.netacademia.hellonetacademia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import hu.netacademia.hellonetacademia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnTime = findViewById(R.id.btnTime);
        final TextView tvData = findViewById(R.id.tvData);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String time = "Az aktuális idő: "+
                        new Date(System.currentTimeMillis()).toString();


                Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();


                tvData.setText(time);

            }
        });
    }
}
