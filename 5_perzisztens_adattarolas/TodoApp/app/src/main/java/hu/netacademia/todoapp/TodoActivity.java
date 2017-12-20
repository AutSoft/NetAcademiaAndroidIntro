package hu.netacademia.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import hu.netacademia.todoapp.adapter.TodoAdapter;
import hu.netacademia.todoapp.model.Todo;
import hu.netacademia.todoapp.repository.Repository;
import hu.netacademia.todoapp.repository.sqlite.SQLiteRepository;

public class TodoActivity extends AppCompatActivity implements TodoAdapter.OnItemLongClickListener {

    public static final int REQUEST_CODE = 111;
    private List<Todo> todos;
    private TodoAdapter adapter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        repository= new SQLiteRepository();

        todos = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new TodoAdapter(this, todos, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(decoration);
        loadTodosAsync();

    }

    private void loadTodosAsync() {
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(TodoActivity.this);
                final List<Todo> todosLoaded = repository.getAllTodo();
                repository.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        todos.clear();
                        todos.addAll(todosLoaded);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        loadThread.start();
    }


    private void saveTodosAsync(final Todo todo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(TodoActivity.this);
                repository.saveTodo(todo);
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTodosAsync();
                    }
                });
            }
        });
        thread.start();
    }

    private void deleteTodoAsync(final Todo todo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(TodoActivity.this);
                repository.deleteTodo(todo);
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTodosAsync();
                    }
                });
            }
        });
        thread.start();
    }

    private void deleteAllAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                repository.open(TodoActivity.this);
                repository.deleteAllTodo();
                repository.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTodosAsync();
                    }
                });
            }
        });
        thread.start();
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
            deleteAllAsync();


        } else if (item.getItemId() == R.id.createTodoMenu) {
            Intent intent = new Intent(this, CreateTodoActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
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
                saveTodosAsync(todo);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemLongClicked(int position) {
        Todo todo = todos.get(position);
        deleteTodoAsync(todo);
    }
}
