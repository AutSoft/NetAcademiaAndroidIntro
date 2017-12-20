package hu.netacademia.todoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends BaseActivity {

    private EditText emailET;
    private EditText passwordET;
    private Button loginBTN;
    private Button registerBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBTN = findViewById(R.id.loginBTN);
        registerBTN = findViewById(R.id.registerBTN);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFormValid()) {
                    return;
                }

                showProgressDialog();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        emailET.getText().toString(),
                        passwordET.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideProgressDialog();
                                if (task.isSuccessful()) {
                                    FirebaseUser user=task.getResult().getUser();
                                    user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(userNameFromEmail(user.getEmail())).build());

                                    Toast.makeText(LoginActivity.this,"Regisztráció sikeres!",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this,"Regisztrációs hiba történt!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(LoginActivity.this, "Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                ;
            }
        });


        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFormValid()) {
                    return;
                }

                showProgressDialog();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        emailET.getText().toString(),
                        passwordET.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideProgressDialog();
                                if (task.isSuccessful()) {
                                    Intent intent=new Intent(LoginActivity.this, TodoActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"Belépési hiba történt!",Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideProgressDialog();
                                Toast.makeText(LoginActivity.this, "Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

    }


    public boolean isFormValid(){
        if (TextUtils.isEmpty(emailET.getText().toString())) {
            emailET.setError("Kötelező");
            return false;
        }

        if (TextUtils.isEmpty(passwordET.getText().toString())) {
            passwordET.setError("Kötelező");
            return false;
        }
        return  true;
    }


    public String userNameFromEmail(String email){
        if (email.contains("@")) {
            return email.split("@")[0];
        }else{
            return email;
        }
    }
}
