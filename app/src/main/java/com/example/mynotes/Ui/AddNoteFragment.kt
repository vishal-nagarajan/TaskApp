package com. example.mynotes.Ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.Ui.AddNoteFragmentArgs
import com.example.mynotes.Ui.AddNoteFragmentDirections
import com.example.mynotes.Ui.BaseFragment
import com.example.mynotes.Ui.toast
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.db.client
import com.example.mynotes.db.project
import kotlinx.android.synthetic.main.dialog_client.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.grid_tentative_todo.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class AddNoteFragment : BaseFragment() {
    private var note: Note? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//client droop down
        fun clientupdate() {
    launch {
        context?.let {
            val array = NoteDatabase(it).getNoteDao().getAllClients()
            val spinner = spinnerclient
            var clientset: String?
            val adapter =
//                    context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_dropdown_item_1line,
                    array
                )
            spinner.adapter = adapter
            arguments.let {
                clientset = note?.client
                val index = adapter?.getPosition(clientset)
                spinner.setSelection(index!!.toInt())
            }
        }
    }
}
        //project
        fun projectupdate() {
        launch {
            context?.let {
                val array2 = NoteDatabase(it).getNoteDao().getAllProjects()
                val spinner2 = spinnerProject
                var projectset: String?
                val adapter2 =
//                    context?.let {
                    ArrayAdapter<String>(
                        it,
                        android.R.layout.simple_dropdown_item_1line,
                        array2
                    )
                spinner2.adapter = adapter2
                arguments.let {
                    projectset = note?.project
                    val index2 = adapter2?.getPosition(projectset)
                    spinner2.setSelection(index2!!.toInt())
                }
            }
        }
    }
        clientupdate()
        projectupdate()
        fun clientcall(field: String) {
            val noteClient = field
            val client = client(noteClient)
            launch {
                context?.let {
                    if (field.isEmpty()) {
                        it.toast("Empty Client name cannot be saved!")
                    } else {
                        NoteDatabase(it).getNoteDao().addClient(client)
                        it.toast("new client saved")
//                        activity?.recreate()
                        clientupdate()
                    }
                }
            }
        }
        fun projectcall(field: String){
            val noteProject = field
//            var confirm:Boolean = false
            val project = project(noteProject)
            launch {
                context?.let {
                    if (field.isEmpty()) {
                        it.toast("Empty Project name cannot be saved!")
//                        confirm = false
                    } else {
                        NoteDatabase(it).getNoteDao().addProject(project)
                        it.toast("new Project saved")
//                        confirm = true
                        projectupdate()

                    }
                }
            }
//            return confirm
        }
        button.setOnClickListener {
            context?.let {
                context?.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = layoutInflater.inflate(R.layout.dialog_client, null)
                    builder.setPositiveButton(R.string.add_new_project,
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    builder.setView(inflater)
                    val dial = builder.create()
                    dial.show()
                    dial.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val r = inflater.findViewById<EditText>(R.id.dialog_client_name)
                        val s = r.text.toString()
                        if (s.isEmpty()) {
                            r.error = "enterClient"
                            r.requestFocus()
                        } else {
                            clientcall(s)
                            dial.dismiss()
                        }
                    }
                }
            }
        }


            button2.setOnClickListener {
                context?.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = layoutInflater.inflate(R.layout.dialog_project, null)
                    builder.setPositiveButton(R.string.add_new_project,
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                    builder.setView(inflater)
                    val dial = builder.create()
                    dial.show()
                    dial.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val r = inflater.findViewById<EditText>(R.id.dialog_project_name)
                        val s = r.text.toString()
                        if (s.isEmpty()) {
                            r.error = "Enter Project"
                            r.requestFocus()
                        } else {
                            projectcall(s)
                            dial.dismiss()
                        }

                    }
                }
            }
//        var noteUEndDate: List<String> = listOf()
            val date = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = date.format(Date())
            val todayDate = currentDate.split("/")
            val day = Integer.parseInt(todayDate.elementAt(0))
            val mon = Integer.parseInt(todayDate.elementAt(1))
            val Yr = Integer.parseInt(todayDate.elementAt(2))
            var stDate: String = ""
            var edDate: String = ""

            arguments?.let {
                note = AddNoteFragmentArgs.fromBundle(it).note
                edit_task.setText(note?.task)
                edit_description.setText(note?.description)
            }
//        val binding = ActivityAwesomeBinding.inflate(layoutInflater)
            val cal = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
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
            //startDatePick
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    stDate = updateStartDate()
                    start_date_display.text = stDate
                }

            // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
            if (note == null) {
                start_date_pick.setOnClickListener {
                    context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener, // set DatePickerDialog to point to today's date when it loads up
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                }
            } else {
                start_date_pick.setOnClickListener {
                    context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener,
                            note!!.startYear,
                            note!!.startMon - 1,
                            note!!.startDay
                        ).show()

                    }
                }
            }

            //endDatePick
            val dateSetListener2 =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal2.set(Calendar.YEAR, year)
                    cal2.set(Calendar.MONTH, monthOfYear)
                    cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    edDate = updateEndDate()
                    end_date_display.text = edDate


                }
            if (note == null) {
                end_date_pick.setOnClickListener {
                    context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener2,
                            // set DatePickerDialog to point to today's date when it loads up
                            cal2.get(Calendar.YEAR),
                            cal2.get(Calendar.MONTH),
                            cal2.get(Calendar.DAY_OF_MONTH)
                        ).show()

                    }
                }
            } else {
                end_date_pick.setOnClickListener {
                    context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener2,
                            note!!.endYear,
                            note!!.endMon - 1,
                            note!!.endDay
                        ).show()

                    }
                }
            }
            //

            button_save.setOnClickListener {
                val noteTask = edit_task.text.toString().trim()
                val noteDescription = edit_description.text.toString().trim()
                val noteClient = spinnerclient.selectedItem.toString()
                val noteProject = spinnerProject.selectedItem.toString()
                val noteStartDate = stDate.split("/")
                val noteEndDate = edDate.split("/")
                var noteStartDay: Int;
                var noteEndDay: Int;
                var noteStartMon: Int;
                var noteEndMon: Int;
                var noteEndYear: Int;
                var noteStartYear: Int;
                if (noteTask.isEmpty()) {
                    edit_task.error = "task required"
                    edit_task.requestFocus()
                    return@setOnClickListener
                }
                if (noteDescription.isEmpty()) {
                    edit_description.error = "description required"
                    edit_description.requestFocus()
                    return@setOnClickListener
                }
                if (noteClient.isEmpty()) {
                    val errorText = spinnerclient.selectedView as TextView
                    errorText.error = "client required"
                    errorText.requestFocus()
                    return@setOnClickListener
                }
                if (noteProject.isEmpty()) {
                    val errorText = spinnerProject.selectedView as TextView
                    errorText.error = "project required"
                    errorText.requestFocus()
                    return@setOnClickListener
                }
                if (stDate.isEmpty() || edDate.isEmpty()) {
                    if (stDate.isEmpty()) {
                        start_date_display.error = "start date Required"
                        start_date_display.requestFocus()
                        return@setOnClickListener
                    }
                    if (edDate.isEmpty()) {
                        end_date_display.error = "end date Required"
                        end_date_display.requestFocus()
                        return@setOnClickListener
                    }
                    return@setOnClickListener
                } else {
                    noteStartDay = Integer.parseInt(noteStartDate.elementAt(0))
                    noteStartMon = Integer.parseInt(noteStartDate.elementAt(1))
                    noteStartYear = Integer.parseInt(noteStartDate.elementAt(2))
                    noteEndDay = Integer.parseInt(noteEndDate.elementAt(0))
                    noteEndMon = Integer.parseInt(noteEndDate.elementAt(1))
                    noteEndYear = Integer.parseInt(noteEndDate.elementAt(2))
                }



                launch {
//               val note = Note(noteTask,noteDescription,noteClient,noteProject,noteStartDay,noteStartMon,noteStartYear,noteEndDay,noteEndMon,noteEndYear)
                    context?.let {
                        val mNote = Note(
                            noteTask,
                            noteDescription,
                            noteClient,
                            noteProject,
                            day,
                            mon,
                            Yr,
                            noteStartDay,
                            noteStartMon,
                            noteStartYear,
                            noteEndDay,
                            noteEndMon,
                            noteEndYear,
                            false,
                            0,0,0,0,0,"a","a","a"
                        )
                        if (note == null) {
                            NoteDatabase(it).getNoteDao().addNote(mNote)
                            it.toast("Note Saved")
                        } else {
                            mNote.id = note!!.id
                            NoteDatabase(it).getNoteDao().updateNote(mNote)
                            it.toast("Note Updated")
                        }

                    }
                }
                //back navigation code
                var Backto:Int=1
                arguments?.let {
                    Backto = AddNoteFragmentArgs.fromBundle(it).pager
                }
                if(Backto==1) {
                    val action = AddNoteFragmentDirections.actionTTSaveNote()
                    Navigation.findNavController(it).navigate(action)
                }
                if(Backto==2) {
                    val action = AddNoteFragmentDirections.actionUSaveNote()
                    Navigation.findNavController(it).navigate(action)
                }
                if(Backto==3) {
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }



