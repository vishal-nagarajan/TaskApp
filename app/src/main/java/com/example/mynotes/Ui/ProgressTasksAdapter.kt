package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.Ui.ProgressTasksAdapter.*
import com.example.mynotes.db.Note
import com.example.mynotes.db.TaskWithTimelog
import kotlinx.android.synthetic.main.grid_progress_task.view.*
import java.text.SimpleDateFormat
import java.util.*


class ProgressTasksAdapter(private val notes: List<Note>, private val time: List<TaskWithTimelog>): RecyclerView.Adapter<ProgressTasksViewHolder>() {
    class ProgressTasksViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressTasksViewHolder {

        return ProgressTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_progress_task, parent, false)
        )
    }
    override fun getItemCount() = notes.size
    fun findDaysLeft(date: String, enddate: String):String {
         val sdf:SimpleDateFormat= SimpleDateFormat("dd-MM-yyyy")
        try {
            val d1=sdf.parse(date)
            val d2=sdf.parse(enddate)
            val difference=d2.time-d1.time
            val days: Long =(difference/ (1000 * 60 * 60 * 24))% 365
            if(days<0){
                return "Over Due"
            }
            return days.toString()

        }catch (e: Exception){
            Log.ERROR;
        }
        return "NULL"
    }
    fun hoursWorked(id: Int):String{
        val sdf:SimpleDateFormat= SimpleDateFormat("HH-mm")
        var Hours:Long=0
        var Mins:Long=0
        for (t in time){
            if(t.task.id==id){
                for(h in t.timelogs){
                    val t1=sdf.parse(h.fromtimeHour.toString() + "-" + h.fromtimeMin)
                    val t2=sdf.parse(h.totimeHour.toString() + "-" + h.totimeMin)
                    val difference=t2.time-t1.time
                    val hours: Long = ((difference
                            / (1000 * 60 * 60))
                            % 24)
                    val min:Long= ((difference
                            / (1000 * 60))
                            % 60)
                    Hours+=hours
                    Mins+=min
                }
                return "$Hours:$Mins"
            }
        }
        return "NULL";
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProgressTasksViewHolder, position: Int) {
            holder.view.progress_task.setBackgroundResource(R.drawable.table_title)
            holder.view.progress_sno.text = notes[position].id.toString()
            holder.view.progress_task.text = notes[position].task
            val daysL:String=findDaysLeft(
                notes[position].day.toString() + "-" + notes[position].mon.toString() + "-" + notes[position].year.toString(),
                notes[position].endDay.toString() + "-" + notes[position].endMon.toString() + "-" + notes[position].endYear.toString()
            )
            if(daysL.equals("Over Due")){
                holder.view.setBackgroundResource(R.drawable.over_due_theme)
                holder.view.progress_days_left.text = daysL
            }
            else if(daysL.equals("1")||daysL.equals("0")){
                holder.view.setBackgroundResource(R.drawable.one_day_left)
                holder.view.progress_days_left.text = daysL + "day left"
            }
            else {
                holder.view.setBackgroundResource(R.drawable.more_days_left)
                holder.view.progress_days_left.text = daysL + "days left"
            }
            holder.view.progress_hours.text = hoursWorked(notes[position].id)+"Hours Worked"

    }


}