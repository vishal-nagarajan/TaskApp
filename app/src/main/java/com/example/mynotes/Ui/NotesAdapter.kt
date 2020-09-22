package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.text_view_task.text = notes[position].task
//        holder.view.text_view_description.text = notes[position].description
//        holder.view.text_view_client.text = notes[position].client
//        holder.view.text_view_project.text = notes[position].project
//        holder.view.text_view_currDate.text =
//            notes[position].day.toString() + "-" + notes[position].mon.toString() + "-" + notes[position].year.toString()
//        holder.view.text_view_startDate.text =
//            notes[position].startDay.toString() + "-" + notes[position].startMon.toString() + "-" + notes[position].startYear.toString()
//        holder.view.text_view_endDate.text =
//            notes[position].endDay.toString() + "-" + notes[position].endMon.toString() + "-" + notes[position].endYear.toString()
        holder.view.edit_icon.setOnClickListener {
            val action  = HomeFragmentDirections.actionAddNote()
            action.note=notes[position]
            Navigation.findNavController(it).navigate(action)
         }
        holder.view.view_icon.setOnClickListener {
            holder.view.context?.let {
                val builder = AlertDialog.Builder(it)
                val inflater = LayoutInflater.from(holder.view.context).inflate(R.layout.dialog_view_task,null)
//                builder.setTitle(notes[position].task)
//                val discription=
                    inflater.findViewById<TextView>(R.id.task_view_edit_tab).text=notes[position].task
                    inflater.findViewById<TextView>(R.id.text_view_description).text=notes[position].description
//                val client=
                    inflater.findViewById<TextView>(R.id.text_view_client).text=notes[position].client
//                val project=
                    inflater.findViewById<TextView>(R.id.text_view_project).text=notes[position].project

                    inflater.findViewById<TextView>(R.id.text_view_startDate).text=notes[position].startDay.toString() + "/" + notes[position].startMon.toString() + "/" + notes[position].startYear.toString()
                    inflater.findViewById<TextView>(R.id.text_view_endDate).text=notes[position].endDay.toString() + "/" + notes[position].endMon.toString() + "/" + notes[position].endYear.toString()
                builder.setView(inflater)
                val dial = builder.create()
                dial.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dial.show()
                inflater.findViewById<ImageButton>(R.id.close_view_edit_tab).setOnClickListener {
                    dial.dismiss()
                }
            }
        }
//        holder.view.setOnClickListener{
//            val action  = HomeFragmentDirections.actionAddNote()
//            action.note=notes[position]
//            Navigation.findNavController(it).navigate(action)
//
//        }


    }
}