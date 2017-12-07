package hu.netacademia.highlowgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randGen = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        generateRandomNumber();
    }

    private void initUI() {
        final EditText etGuess = findViewById(R.id.etGuess);
        Button btnGuess = findViewById(R.id.btnGuess);
        Button btnNewGame = findViewById(R.id.btnNewGame);
        final TextView tvStatus = findViewById(R.id.tvStatus);

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess(etGuess, tvStatus);
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumber();
                tvStatus.setText("New game started");
            }
        });
    }

    private void checkGuess(EditText etGuess, TextView tvStatus) {
        try {
            int guess = Integer.parseInt(etGuess.getText().toString());

            if (guess == randGen) {
                tvStatus.setText(R.string.text_win);
            } else if (guess < randGen) {
                tvStatus.setText("A szám nagyobb!");
            } else if (guess > randGen) {
                tvStatus.setText("A szám alacsonyabb!");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            etGuess.setError("Hé ide számot írj, pl 4");
        }
    }

    private void generateRandomNumber() {
        randGen = new Random(System.currentTimeMillis()).nextInt(10);
    }
}
