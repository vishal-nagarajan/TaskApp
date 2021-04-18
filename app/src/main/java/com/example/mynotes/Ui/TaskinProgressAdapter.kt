package com.example.mynotes.Ui


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.fragment_tentative_to_do.*
import kotlinx.android.synthetic.main.grid_taskinprogress.view.*
import kotlinx.android.synthetic.main.grid_tentative_todo.view.*
import kotlinx.android.synthetic.main.grid_tentative_todo.view.sno
import kotlinx.android.synthetic.main.note_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskinProgressAdapter(private val notes : List<Note>):RecyclerView.Adapter<TaskinProgressAdapter.TaskinProgressViewHolder>() {
    val date = SimpleDateFormat("dd/MM/yyyy")
    val currentDate=date.format(Date())
    val todayDate=currentDate.split("/")
    val day=Integer.parseInt(todayDate.elementAt(0))
    val mon=Integer.parseInt(todayDate.elementAt(1))
    val year=Integer.parseInt(todayDate.elementAt(2))



    class TaskinProgressViewHolder(val view : View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskinProgressViewHolder {
        return TaskinProgressViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_taskinprogress,parent,false)
        )
    }

    override fun getItemCount()=notes.size

    override fun onBindViewHolder(holder: TaskinProgressViewHolder, position: Int) {

        holder.view.tip_task.text = notes[position].task
        holder.view.sno.text = (notes.size - notes[position].id + 1).toString()
        holder.view.update_button.setOnClickListener{
            val action  = TaskinProgressDirections.actionStatusChange()
            action.note=notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
}