package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.fragment_tentative_to_do.*
import kotlinx.android.synthetic.main.grid_tentative_todo.view.*
import kotlinx.android.synthetic.main.note_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class TentativeAdapter(private val notes : List<Note>):RecyclerView.Adapter<TentativeAdapter.TentativeViewHolder>() {
    val date = SimpleDateFormat("dd/MM/yyyy")
    val currentDate=date.format(Date())
    val todayDate=currentDate.split("/")
    val day=Integer.parseInt(todayDate.elementAt(0))
    val mon=Integer.parseInt(todayDate.elementAt(1))
    val year=Integer.parseInt(todayDate.elementAt(2))



    class TentativeViewHolder(val view : View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TentativeViewHolder {
        return TentativeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_tentative_todo,parent,false)
        )
    }

    override fun getItemCount()=notes.size

    override fun onBindViewHolder(holder: TentativeViewHolder, position: Int) {

//        holder.view.text_view_task.text = notes[position].task
//        holder.view.text_view_description.text = notes[position].description
//        holder.view.text_view_client.text = notes[position].client
//        holder.view.text_view_project.text = notes[position].project
//        holder.view.text_view_startDate.text = notes[position].startDate
//        holder.view.text_view_endDate.text= notes[position].endDate
//        if(notes[position].startDay==day) {
            holder.view.tentative_task.text = notes[position].task
            holder.view.sno.text = (notes.size - notes[position].id + 1).toString()
//        }



    }
}