package hu.netacademia.todoapp.repository.sqlite;

import static hu.netacademia.todoapp.repository.sqlite.DBConstants.TODO_MODEL.ASSIGNEE;
import static hu.netacademia.todoapp.repository.sqlite.DBConstants.TODO_MODEL.DESCRIPTION;
import static hu.netacademia.todoapp.repository.sqlite.DBConstants.TODO_MODEL.ID;
import static hu.netacademia.todoapp.repository.sqlite.DBConstants.TODO_MODEL.TITLE;

public class DBConstants {

    public static final String DATABASE_NAME= "todo.db";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_TODO = "todo";

    public static class TODO_MODEL{
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String ASSIGNEE = "assignee";
    }

    public static final String CREATE_TABLE=
            "CREATE TABLE IF NOT EXISTS " + TABLE_TODO + " " +
                    "(" +
                    ID + " integer primary key autoincrement," +
                    TITLE + " text not null," +
                    DESCRIPTION + " text," +
                    ASSIGNEE + " text" +
                    ");";

    public static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_TODO+" ;";

}
