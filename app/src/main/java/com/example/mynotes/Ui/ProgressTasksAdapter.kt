package com.example.mynotes.Ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.ProgressTasksAdapter.*
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.grid_progress_task.view.*


class ProgressTasksAdapter(private val notes: List<Note>): RecyclerView.Adapter<ProgressTasksViewHolder>() {
    class ProgressTasksViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressTasksViewHolder {

        return ProgressTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_progress_task, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: ProgressTasksViewHolder, position: Int  ) {
//        var a = position
        if (holder.adapterPosition%2==0){
            holder.view.progress_sno.setBackgroundResource(R.drawable.table_title)
            holder.view.progress_task.setBackgroundResource(R.drawable.table_title)
            holder.view.progress_days_left.setBackgroundResource(R.drawable.table_title)
            holder.view.progress_hours.setBackgroundResource(R.drawable.table_title)
//            holder.view.progress_sno.text = notes[position].id.toString()
//            holder.view.progress_task.text = notes[position].task
//            holder.view.progress_days_left.text = "5 Days"
//            holder.view.progress_hours.text = "0"
//            val id:Int =notes[position].id
//           holder.view.verticalScrollbarPosition=1

        }
        else if(holder.adapterPosition==1){
            holder.view.progress_sno.text = notes[0].id.toString()
            holder.view.progress_task.text = notes[0].task
            holder.view.progress_days_left.text = "5 Days"
            holder.view.progress_hours.text = "position.toString()"
        }
//        else{
            holder.view.progress_sno.text = notes[position].id.toString()
            holder.view.progress_task.text = notes[position].task
            holder.view.progress_days_left.text = "5 Days"
            holder.view.progress_hours.text ="position.toString()"
//        }

    }


}