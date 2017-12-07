package hu.netacademia.uidemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lifecycle","onCreate called");

        Button navigationButton=findViewById(R.id.navigationButton);
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, RelativeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle","onDestroy called");
        super.onDestroy();
    }
}
