package hu.netacademia.todoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hu.netacademia.todoapp.R;
import hu.netacademia.todoapp.model.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{


    private final Context context;
    private final List<Todo> todos;
    private final LayoutInflater inflater;

    public TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
        this.inflater= LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.li_todo,parent,false);
       ViewHolder holder=new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo=todos.get(position);
        holder.titleTV.setText(todo.getTitle());
        holder.descriptionTV.setText(todo.getDescription());
        holder.assigneeTV.setText("Felelős: "+todo.getAssignee());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView titleTV;
        public final TextView descriptionTV;
        public final TextView assigneeTV;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTV= itemView.findViewById(R.id.titleTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            assigneeTV = itemView.findViewById(R.id.assigneeTV);

        }
    }

}
