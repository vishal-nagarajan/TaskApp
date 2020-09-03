package com.example.mynotes.Ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        var stDate : String =""
        var edDate : String=""
        super.onActivityCreated(savedInstanceState)
        val cal = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
//        var textview_date: TextView? = null
//        textview_date = this.display_start_date
         //create an OnDateSetListener
         fun updateStartDate(): String {
             val myFormat = "dd/MM/yyyy" // mention the format you need
             val sdf = SimpleDateFormat(myFormat, Locale.US)
             return sdf.format(cal.getTime())
         }
        fun updateEndDate(): String {
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val edf = SimpleDateFormat(myFormat, Locale.US)
            return edf.format(cal2.getTime())
        }
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    stDate=updateStartDate()
                }

            // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
            start_date_pick.setOnClickListener {
                context?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()

                }
            }
        val dateSetListener2 =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal2.set(Calendar.YEAR, year)
                cal2.set(Calendar.MONTH, monthOfYear)
                cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                edDate=updateEndDate()

            }

        end_date_pick.setOnClickListener {
                    context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener2,
                            // set DatePickerDialog to point to today's date when it loads up
                            cal2.get(Calendar.YEAR),
                            cal2.get(Calendar.MONTH),
                            cal2.get(Calendar.DAY_OF_MONTH)).show()

                    }
            }



        button_save.setOnClickListener{
            val noteTask = edit_task.text.toString().trim()
            val noteDescription = edit_description.text.toString().trim()
            val noteClient = edit_client.text.toString().trim()
            val noteProject = edit_project.text.toString().trim()
            val noteStartDate = stDate
            val noteEndDate = edDate
            if(noteTask.isEmpty()){
                edit_task.error = "task required"
                edit_task.requestFocus()
                return@setOnClickListener
            }
            if(noteDescription.isEmpty()){
                edit_description.error = "description required"
                edit_description.requestFocus()
                return@setOnClickListener
            }
            if(noteClient.isEmpty()){
                edit_client.error = "client required"
                edit_client.requestFocus()
                return@setOnClickListener
            }
            if(noteProject.isEmpty()){
                edit_project.error = "project requaired"
                edit_project.requestFocus()
                return@setOnClickListener
            }
//            if(noteEndDate==0){
//                edit_end_date.error = "enddate Required"
//                edit_end_date.requestFocus()
//                return@setOnClickListener
//            }
            launch {
                val note = Note(noteTask,noteDescription,noteClient,noteProject,noteStartDate,noteEndDate)
                context?.let {
                    NoteDatabase(it).getNoteDao().addNote(note)
                    it.toast("Note Saved")
                }
            }
            //val note = Note(noteTask,noteDescription,noteClient,noteProject,noteStartDate,noteEndDate)
           // NoteDatabase(requireActivity()).getNoteDao().addNote(note)
//            saveNote(note)
            val action = AddNoteFragmentDirections.actionSaveNote()
            Navigation.findNavController(it).navigate(action)
        }
    }
//    private fun saveNote(note:Note){bui
//        class SaveNote : AsyncTask<Void, Void, Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
//                return  null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(activity, "Note saved",Toast.LENGTH_LONG).show()
//            }
//
//        }
//        SaveNote().execute()
//    }
}

//private fun Int.isEmpty(): Boolean {
//
//}
