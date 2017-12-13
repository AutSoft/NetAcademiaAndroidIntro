package hu.netacademia.todoapp.model;

public class Todo {
    private String title;
    private String description;
    private String assignee;

    public Todo(String title, String description, String assignee) {
        this.title = title;
        this.description = description;
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
