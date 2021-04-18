package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.YetDueAdapter.*
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.grid_due_over.view.*
import kotlinx.android.synthetic.main.grid_progress_task.view.*
import java.text.SimpleDateFormat


class YetDueAdapter(private val notes: List<Note>): RecyclerView.Adapter<YetDueViewHolder>() {
    class YetDueViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YetDueViewHolder {

        return YetDueViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_due_over, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    @SuppressLint("SimpleDateFormat")
    fun findDaysLeft(date: String, enddate: String):String {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        try {
            val d1=sdf.parse(date)
            val d2=sdf.parse(enddate)
            val difference=d2.time-d1.time
            val days: Long =-1*(difference/ (1000 * 60 * 60 * 24))% 365
            if(days == 0.toLong()){
                return "Today is the Last Day"
            }else if (days == 1.toLong()){
                return "Tomorrow is the Last Day"
            }else {
                return "$days Days left"
            }

        }catch (e: Exception){
            Log.ERROR;
        }
        return "NULL"
    }
    override fun onBindViewHolder(holder: YetDueViewHolder, position: Int) {
        holder.view.due_over_sno.text = notes[position].id.toString()
        holder.view.over_due_task.text = notes[position].task
        holder.view.over_due_date.text = findDaysLeft(notes[position].endDay.toString() + "-" + notes[position].endMon.toString() + "-" + notes[position].endYear.toString(),notes[position].day.toString() + "-" + notes[position].mon.toString() + "-" + notes[position].year.toString())
    }
}