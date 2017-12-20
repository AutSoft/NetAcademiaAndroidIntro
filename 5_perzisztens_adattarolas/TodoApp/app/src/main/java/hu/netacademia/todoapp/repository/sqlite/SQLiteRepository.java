package hu.netacademia.todoapp.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.netacademia.todoapp.model.Todo;
import hu.netacademia.todoapp.repository.Repository;

public class SQLiteRepository implements Repository {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public void saveTodo(Todo todo) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.TODO_MODEL.TITLE, todo.getTitle());
        values.put(DBConstants.TODO_MODEL.DESCRIPTION, todo.getDescription());
        values.put(DBConstants.TODO_MODEL.ASSIGNEE, todo.getAssignee());

        db.insert(DBConstants.TABLE_TODO, null, values);
    }

    @Override
    public List<Todo> getAllTodo() {

        Cursor cursor = db.query(DBConstants.TABLE_TODO,
                new String[]{
                        DBConstants.TODO_MODEL.ID,
                        DBConstants.TODO_MODEL.TITLE,
                        DBConstants.TODO_MODEL.DESCRIPTION,
                        DBConstants.TODO_MODEL.ASSIGNEE
                }, null, null, null, null, DBConstants.TODO_MODEL.ID);

        List<Todo> todosLoaded = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBConstants.TODO_MODEL.ID));
            String title = cursor.getString(cursor.getColumnIndex(DBConstants.TODO_MODEL.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(DBConstants.TODO_MODEL.DESCRIPTION));
            String assignee = cursor.getString(cursor.getColumnIndex(DBConstants.TODO_MODEL.ASSIGNEE));
            Todo todo = new Todo(id, title, description, assignee);
            todosLoaded.add(todo);

        }

        cursor.close();
        return todosLoaded;
    }

    @Override
    public void deleteTodo(Todo todo) {
        Long id = todo.getId();
        if (id != null) {
            db.delete(DBConstants.TABLE_TODO, DBConstants.TODO_MODEL.ID + "=" + todo.getId(), null);
        }
    }

    @Override
    public void deleteAllTodo() {
        db.execSQL(DBConstants.DROP_TABLE);
    }

    @Override
    public void open(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
    }

    @Override
    public void close() {
        dbHelper.close();
    }
}
