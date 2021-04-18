package com.example.mynotes.Ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.DueOverAdapter.*
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.grid_due_over.view.*
import kotlinx.android.synthetic.main.grid_progress_task.view.*
import java.text.SimpleDateFormat


class DueOverAdapter(private val notes: List<Note>): RecyclerView.Adapter<DueOverViewHolder>() {
    class DueOverViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DueOverViewHolder {

        return DueOverViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_due_over, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    fun findDaysLeft(date: String, enddate: String):String {
        val sdf: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        try {
            val d1=sdf.parse(date)
            val d2=sdf.parse(enddate)
            val difference=d2.time-d1.time
            val days: Long =(difference/ (1000 * 60 * 60 * 24))% 365
            return days.toString()
//            if(days<0){
//                return "Over Due"
//            }
//            return days.toString()

        }catch (e: Exception){
            Log.ERROR;
        }
        return "NULL"
    }
    override fun onBindViewHolder(holder: DueOverViewHolder, position: Int) {
        holder.view.setBackgroundResource(R.drawable.over_due_theme)
        holder.view.due_over_sno.text = notes[position].id.toString()
        holder.view.over_due_task.text = notes[position].task
        holder.view.over_due_date.text = "Task Ended "+findDaysLeft(notes[position].endDay.toString() + "-" + notes[position].endMon.toString() + "-" + notes[position].endYear.toString(),notes[position].day.toString() + "-" + notes[position].mon.toString() + "-" + notes[position].year.toString())+" Days ago!"
//        holder.view.progress_hours.text = "0"
    }
}