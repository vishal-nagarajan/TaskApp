package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.grid_tentative_todo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch
import java.lang.Integer.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.MONTH

class TentativeAdapter(private val notes: List<Note>):RecyclerView.Adapter<TentativeAdapter.TentativeViewHolder>() {
    val datet = SimpleDateFormat("dd/MM/yyyy")
    val currentDatet = datet.format(Date())
    val todayDatet = currentDatet.split("/")
    var  day :Int=1
    var mon:Int=1
    var yearw:Int=1

    class TentativeViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TentativeViewHolder {

        return TentativeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_tentative_todo, parent, false)
        )
    }

    override fun getItemCount() = notes.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TentativeViewHolder, position: Int) {


        holder.view.tentative_task.text = notes[position].task
        holder.view.sno.text = (notes.size - notes[position].id + 1).toString()
        holder.view.modify_button.setOnClickListener {

            holder.view.context?.let {

                val builder = AlertDialog.Builder(it)
                val inflater = LayoutInflater.from(holder.view.context).inflate(
                    R.layout.dialog_modify_tentativetodo,
                    null
                )
                builder.setTitle("Modify Tentative To do status")


                val PID  = inflater.findViewById<TextView>(R.id.postpone_picked_date)
                //postpone custom date
                inflater.findViewById<Button>(R.id.buttonPostpone).setOnClickListener {
//                    var finalpostDate:String="nul"
                    val cal = Calendar.getInstance()
                    var ptDate:String
                    fun updatepost(): String {
                        val myFormat = "dd/MM/yyyy" // mention the format you need
                        val sdf = SimpleDateFormat(myFormat, Locale.US)
                        return sdf.format(cal.getTime())
                    }
                    val dateSetListener =
                        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            ptDate = updatepost()
                            PID.text = ptDate
                            val noteStartDate = ptDate.split("/")
                            day = parseInt(noteStartDate.elementAt(0))
                            mon = parseInt(noteStartDate.elementAt(1))
                            yearw = parseInt(noteStartDate.elementAt(2))
                        }
                    holder.view.context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener, // set DatePickerDialog to point to today's date when it loads up
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                }
                builder.setPositiveButton(R.string.save,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
                builder.setView(inflater)
                val dial = builder.create()
                dial.show()

                dial.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
//                    fun DialogInterface.OnClickListener(){
                    if(yearw==1){
                        PID.error = "pick date"
                        PID.requestFocus()
                    }else {
                        CoroutineScope(Dispatchers.Main + Job()).launch {
                            val id = notes[position].id
                            NoteDatabase(holder.view.context).getNoteDao()
                                .updateMDate(id, day, mon, yearw)
                            holder.view.context.toast("Task Modified")
                            dial.dismiss()
                        }

                    }
                }
                //tomorow
                inflater.findViewById<Button>(R.id.button_drop_today).setOnClickListener {
                    val cal3 = Calendar.getInstance()
                    cal3.add(Calendar.DAY_OF_YEAR, 1) //Adds a day //Goes to previous day
                    val myFormat = "dd/MM/yyyy" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.US)
                    val nxtdate = sdf.format(cal3.getTime())
                    val noteStartDate = nxtdate.split("/")
                    day = parseInt(noteStartDate.elementAt(0))
                    mon = parseInt(noteStartDate.elementAt(1))
                    yearw = parseInt(noteStartDate.elementAt(2))
                    PID.text = nxtdate
                }
            }

        }
    }
}


