package hu.netacademia.todoapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class DemoActivity extends AppCompatActivity {

    public static final String COUNTER = "COUNTER";
    public static final String DEMO_PREFERENCES = "DEMO_PREFERENCES";
    public static final String IS_ALREADY_STARTED = "IS_ALREADY_STARTED";
    private Button animateButton;
    private Button popupButton;
    private Toolbar toolbar;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        if (savedInstanceState != null) {
            counter=savedInstanceState.getInt(COUNTER);
        }else{
            counter=0;
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animateButton = findViewById(R.id.animateBTN);
        popupButton = findViewById(R.id.popupBTN);
        popupButton.setText("Megnyomták:"+counter);

        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation buttonAnimation = AnimationUtils.loadAnimation(DemoActivity.this, R.anim.anim_button);
                toolbar.startAnimation(buttonAnimation);
                counter++;
                popupButton.setText("Megnyomták:"+counter);

            }
        });

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        if (!isAlreadyStarted()) {
            showWelcomePopup();
            setAlreadyStarted(true);
        }

    }

    private void showWelcomePopup() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Üdvözlet!");
        builder.setMessage("Most használod először az alkalmazást!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_demo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.animateMenu) {
            Animation buttonAnimation = AnimationUtils.loadAnimation(DemoActivity.this, R.anim.anim_button);
            popupButton.startAnimation(buttonAnimation);
        }else if(item.getItemId()==R.id.popupMenu){
            showAlertDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPopupWindow() {
            View view=getLayoutInflater().inflate(R.layout.popup_demo,null);
            final PopupWindow popup=new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Button closeButton=view.findViewById(R.id.closeBTN);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popup.dismiss();
                }
            });

            popup.showAsDropDown(popupButton,50,50);

    }

    private void showAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage("Megnyomták a gombot");
        builder.setPositiveButton("Rendben", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNTER,counter);
        super.onSaveInstanceState(outState);
    }

    private void setAlreadyStarted(boolean isStarted){
        SharedPreferences sharedPreferences=getSharedPreferences(DEMO_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_ALREADY_STARTED,isStarted);
        editor.commit();
    }

    private boolean isAlreadyStarted(){
        SharedPreferences sharedPreferences=getSharedPreferences(DEMO_PREFERENCES,MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_ALREADY_STARTED,false);
    }
}

