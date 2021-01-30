package com.example.mynotes.Ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.ProgressTasksAdapter.*
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.grid_completed_tasks.view.*
import kotlinx.android.synthetic.main.grid_progress_task.view.*


class ProgressTasksAdapter2(private val notes: List<Note>): RecyclerView.Adapter<ProgressTasksAdapter2.ProgressTasksViewHolder2>() {
    class ProgressTasksViewHolder2(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressTasksViewHolder2 {

        return ProgressTasksViewHolder2(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_completed_tasks, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: ProgressTasksViewHolder2, position: Int) {
        holder.view.completed_sno.text = notes[position].id.toString()
        holder.view.completed_task.text = notes[position].task
        holder.view.completed_date.text = "few"
        holder.view.completed_time_taken.text = "0"
    }
}