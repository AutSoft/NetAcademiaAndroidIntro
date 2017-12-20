package hu.netacademia.todoapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity{
    private ProgressDialog progressDialog;

    public void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Betöltés...");
        }

        progressDialog.show();
    }

    public void hideProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

}
