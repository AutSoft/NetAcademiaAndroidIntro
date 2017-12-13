package hu.netacademia.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import hu.netacademia.todoapp.adapter.TodoAdapter;
import hu.netacademia.todoapp.model.Todo;

public class TodoActivity extends AppCompatActivity {

    private List<Todo> todos;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todos = new ArrayList<>();
        todos.add(new Todo("Karácsonyi ajándékok","Megvenni az ajándékokat","Én"));
        todos.add(new Todo("Előadás","Előadást tartani","Péter"));
        todos.add(new Todo("Virágok","Meglocsolni a virágokat","Kertész"));

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new TodoAdapter(this, todos);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_todo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.removeAll) {
             todos.clear();
             adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
