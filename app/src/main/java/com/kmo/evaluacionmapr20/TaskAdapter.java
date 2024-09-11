package com.kmo.evaluacionmapr20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmo.evaluacionmapr20.R;
import com.kmo.evaluacionmapr20.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewName.setText(task.getName());
        holder.textViewDescription.setText(task.getDescription());
        holder.textViewDate.setText(task.getDate());

        // Calcula el tiempo restante
        long timeRemaining = calculateTimeRemaining(task.getDate());
        holder.textViewTimeRemaining.setText("Tiempo restante: " + timeRemaining + " días");
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private long calculateTimeRemaining(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date taskDate = sdf.parse(date);
            Date currentDate = new Date();

            if (taskDate != null) {
                long diffInMillis = taskDate.getTime() - currentDate.getTime();
                // Asegúrate de que el número de días no sea negativo
                long diffInDays = Math.max(0, TimeUnit.MILLISECONDS.toDays(diffInMillis));

                // Imprime las fechas y la diferencia en milisegundos para depuración
                System.out.println("Task Date: " + taskDate);
                System.out.println("Current Date: " + currentDate);
                System.out.println("Difference in Millis: " + diffInMillis);
                System.out.println("Difference in Days: " + diffInDays);

                return diffInDays;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;
        public TextView textViewDate;
        public TextView textViewTimeRemaining;

        public TaskViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTimeRemaining = itemView.findViewById(R.id.textViewTimeRemaining);
        }
    }
}
