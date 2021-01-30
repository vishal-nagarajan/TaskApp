package com.example.mynotes.Ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.DueOverAdapter.*
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.grid_due_over.view.*
import kotlinx.android.synthetic.main.grid_progress_task.view.*


class DueOverAdapter(private val notes: List<Note>): RecyclerView.Adapter<DueOverViewHolder>() {
    class DueOverViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DueOverViewHolder {

        return DueOverViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_due_over, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: DueOverViewHolder, position: Int) {
        holder.view.due_over_sno.text = notes[position].id.toString()
        holder.view.over_due_task.text = notes[position].task
        holder.view.over_due_date.text = "few"
//        holder.view.progress_hours.text = "0"
    }
}