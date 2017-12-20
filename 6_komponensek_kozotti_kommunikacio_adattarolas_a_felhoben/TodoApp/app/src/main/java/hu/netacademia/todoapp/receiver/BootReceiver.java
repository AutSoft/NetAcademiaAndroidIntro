package hu.netacademia.todoapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hu.netacademia.todoapp.WelcomeActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         Intent welcomeIntent=new Intent(context, WelcomeActivity.class);
         welcomeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(welcomeIntent);
    }
}
