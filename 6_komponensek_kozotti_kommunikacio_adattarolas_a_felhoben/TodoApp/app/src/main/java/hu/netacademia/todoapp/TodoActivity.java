package hu.netacademia.todoapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.netacademia.todoapp.adapter.TodoAdapter;
import hu.netacademia.todoapp.model.Todo;
import hu.netacademia.todoapp.receiver.BatteryReceiver;

public class TodoActivity extends AppCompatActivity implements TodoAdapter.OnItemLongClickListener {

    public static final int REQUEST_CODE = 111;
    private List<Todo> todos;
    private TodoAdapter adapter;
    private BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todos = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new TodoAdapter(this, todos, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(decoration);
        loadTodos();

        batteryReceiver = new BatteryReceiver();

    }

    private void loadTodos() {
       FirebaseDatabase.getInstance().getReference().child("todos")
               .addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                       Todo todo=dataSnapshot.getValue(Todo.class);
                       todos.add(todo);
                       adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                   }

                   @Override
                   public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Todo todo=dataSnapshot.getValue(Todo.class);
                        todos.remove(todo);
                        adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
    }


    private void saveTodo(final Todo todo) {
      String key= FirebaseDatabase.getInstance().getReference().child("todos").push().getKey();
      todo.setKey(key);
      FirebaseDatabase.getInstance().getReference()
              .child("todos")
              .child(key)
              .setValue(todo)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Firebase","Todo mentése sikeres");
                  }
              });


      FirebaseDatabase.getInstance().getReference().child("todos").child(key)
              .setValue(todo);
    }

    private void deleteTodo(final Todo todo) {
       FirebaseDatabase.getInstance().getReference().child("todos").child(todo.getKey())
               .removeValue(new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                       Log.d("Firebase","Törlés sikeres");
                   }
               });
    }

    private void deleteAll() {
        FirebaseDatabase.getInstance().getReference().child("todos").removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.d("Firebase","Összes elem törlése sikeres");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.removeAll) {
            deleteAll();


        } else if (item.getItemId() == R.id.createTodoMenu) {
            Intent intent = new Intent(this, CreateTodoActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }else if (item.getItemId() == R.id.logoutMenu) {
            FirebaseAuth.getInstance().signOut();
            Intent intent= new Intent(TodoActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra(CreateTodoActivity.KEY_TITLE);
                String description = data.getStringExtra(CreateTodoActivity.KEY_DESCRIPTION);
                String assignee = data.getStringExtra(CreateTodoActivity.KEY_ASSIGNEE);
                Todo todo = new Todo(title, description, assignee);
                saveTodo(todo);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClicked(int position) {
        Todo todo = todos.get(position);
        deleteTodo(todo);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.BATTERY_OKAY");
        registerReceiver(batteryReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(batteryReceiver);
        super.onPause();
    }


}
