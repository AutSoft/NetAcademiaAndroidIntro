package hu.netacademia.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTodoActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_DESCRIPTION = "DESCRIPTION";
    public static final String KEY_ASSIGNEE = "ASSIGNEE";
    private EditText createTitleET;
    private EditText createDescriptionET;
    private EditText createAssigneeET;
    private Button createTodoBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        createTitleET = findViewById(R.id.createTitleET);
        createDescriptionET = findViewById(R.id.createDecscriptionET);
        createAssigneeET = findViewById(R.id.createAssigneeET);
        createTodoBTN = findViewById(R.id.createTodoBTN);

        createTodoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=createTitleET.getText().toString();
                String description=createDescriptionET.getText().toString();
                String assignee=createAssigneeET.getText().toString();

                Intent intent=new Intent();
                intent.putExtra(KEY_TITLE,title);
                intent.putExtra(KEY_DESCRIPTION,description);
                intent.putExtra(KEY_ASSIGNEE,assignee);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
