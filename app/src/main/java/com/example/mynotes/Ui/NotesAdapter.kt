package com.example.mynotes.Ui

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val notes : List<Note>):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    class NoteViewHolder(val view : View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount()=notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.text_view_task.text = notes[position].task
        holder.view.text_view_description.text = notes[position].description
        holder.view.text_view_client.text = notes[position].client
        holder.view.text_view_project.text = notes[position].project
        holder.view.text_view_currDate.text=notes[position].day.toString()+"-"+notes[position].mon.toString()+"-"+notes[position].year.toString()
        holder.view.text_view_startDate.text = notes[position].startDay.toString()+"-"+notes[position].startMon.toString()+"-"+notes[position].startYear.toString()
        holder.view.text_view_endDate.text= notes[position].endDay.toString()+"-"+notes[position].endMon.toString()+"-"+notes[position].endYear.toString()
        holder.view.setOnClickListener{
            val action  = HomeFragmentDirections.actionAddNote()
            action.note=notes[position]
            Navigation.findNavController(it).navigate(action)

        }


    }
}