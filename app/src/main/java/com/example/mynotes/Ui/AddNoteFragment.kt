package com. example.mynotes.Ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_update_status.*
import kotlinx.android.synthetic.main.grid_tentative_todo.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess
//This Fragment is invoked from TentativeToDoFragment,HomeFragment,MainMenu
class AddNoteFragment : BaseFragment() {
    //to store the argument when called for updating
    private var note: Note? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //client drop down set/update
        fun clientupdate() {
            launch {
                context?.let {
                    val array = NoteDatabase(it).getNoteDao().getAllClients()
                    val spinner = spinnerclient
                    var clientset: String?
                    val adapter =
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
        //project drop down set/update
        fun projectupdate() {
            launch {
                context?.let {
                    val array2 = NoteDatabase(it).getNoteDao().getAllProjects()
                    val spinner2 = spinnerProject
                    var projectset: String?
                    val adapter2 =
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
        //sets the values of client an project on activity creation
        clientupdate()
        projectupdate()
        //fun to add new client to the existing list
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
                        clientupdate()
                    }
                }
            }
        }
        //fun to add new project to the existing list
        fun projectcall(field: String){
            val noteProject = field
            val project = project(noteProject)
            launch {
                context?.let {
                    if (field.isEmpty()) {
                        it.toast("Empty Project name cannot be saved!")
                    } else {
                        NoteDatabase(it).getNoteDao().addProject(project)
                        it.toast("new Project saved")
                        projectupdate()
                    }
                }
            }
        }
        // button to add new client to list
        add_client_button.setOnClickListener {
            context?.let {
                context?.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = layoutInflater.inflate(R.layout.dialog_client, null)
                    builder.setPositiveButton(R.string.add_new_client,
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
                            r.error = "Enter Client name"
                            r.requestFocus()
                        } else {
                            clientcall(s)
                            dial.dismiss()
                        }
                    }
                }
            }
        }
        // button to add new project to list
        add_project_button.setOnClickListener {
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
                        r.error = "Enter Project name"
                        r.requestFocus()
                    } else {
                        projectcall(s)
                        dial.dismiss()
                    }
                }
            }
        }
        val date = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = date.format(Date())
        val todayDate = currentDate.split("/")
        val day = Integer.parseInt(todayDate.elementAt(0))
        val mon = Integer.parseInt(todayDate.elementAt(1))
        val Yr = Integer.parseInt(todayDate.elementAt(2))

        //if called for updating a Note argument is passed to bundle
        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            edit_task.setText(note?.task)
            edit_description.setText(note?.description)
            if (note!=null) {
                start_date_display.setText(note?.startDay.toString() + "/" + note?.startMon.toString() + "/" + note?.startYear.toString())
                end_date_display.setText(note?.endDay.toString() + "/" + note?.endMon.toString() + "/" + note?.endYear.toString())
            }
        }
        val cal = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        fun updateStartDate(): String {
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            return sdf.format(cal.time)
        }
        fun updateEndDate(): String {
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val edf = SimpleDateFormat(myFormat, Locale.US)
            return edf.format(cal2.time)
        }
        var stDate: String = ""
        var edDate: String = ""
        //start Date Dialog Listener
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                stDate = updateStartDate()
                start_date_display.text = stDate
            }
        //validate whether to update or add new date
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
        //fun to validate End date doesn't exceed start date
        @SuppressLint("SimpleDateFormat")
        fun validEndDate(date: String, enddate: String):Boolean {
            val sdf:SimpleDateFormat= SimpleDateFormat("dd/MM/yyyy")
            try {
                val d1=sdf.parse(date)
                val d2=sdf.parse(enddate)
                val difference=d2.time-d1.time
                val days: Long =(difference/ (1000 * 60 * 60 * 24))% 365
                if(days<0){
                    return false
                }
                return true

            }catch (e: Exception){
                Log.ERROR;
            }
            return true
        }

        //End Date Dialog Listener
        val dateSetListener2 = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal2.set(Calendar.YEAR, year)
                cal2.set(Calendar.MONTH, monthOfYear)
                cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                edDate = updateEndDate()
                if(validEndDate(stDate,edDate)){
                    end_date_display.text = edDate
                }else{
                    end_date_display.text="Invalid End Date"
                    context?.toast("Invalid End Date")
                    return@OnDateSetListener
                }
            }
        //validate whether to update or add new date
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
        //button to validate & save all the details
        button_save.setOnClickListener {
            //gets the value from the text field and stores in the variables to store in database
            val noteTask = edit_task.text.toString().trim()
            val noteDescription = edit_description.text.toString().trim()
            //formating for storing date in dataBase
            val noteStartDate = stDate.split("/")
            val noteEndDate = edDate.split("/")
            val noteStartDay: Int;
            val noteEndDay: Int;
            val noteStartMon: Int;
            val noteEndMon: Int;
            val noteEndYear: Int;
            val noteStartYear: Int;
            //if end date is before start date user is cautioned
            if(!validEndDate(stDate,edDate)){
                context?.toast("Pick Valid End Date")
                end_date_display.error
                return@setOnClickListener
            }
            //if Task is empty user is cautioned
            if (noteTask.isEmpty()) {
                edit_task.error = "Task required"
                edit_task.requestFocus()
                return@setOnClickListener
            }
            //if Description is empty user is cautioned
            if (noteDescription.isEmpty()) {
                edit_description.error = "Description required"
                edit_description.requestFocus()
                return@setOnClickListener
            }
            //if no client is picked user is cautioned
            val noteClient:String
            try {
                noteClient= spinnerclient.selectedItem.toString()
            }catch (e:NullPointerException){
                context?.toast("Client required")
                return@setOnClickListener
            }
            val noteProject:String
            try {
                noteProject= spinnerProject.selectedItem.toString()
            }catch (e:NullPointerException){
                context?.toast("Project Required")
                return@setOnClickListener
            }
            //if Start Date (or) End Date is not picked user is cautioned
            if (stDate.isEmpty() || edDate.isEmpty()) {
                if (stDate.isEmpty()) {
                    context?.toast("Start date Required")
                    start_date_display.requestFocus()
                    return@setOnClickListener
                }
                if (edDate.isEmpty()) {
                    context?.toast( "End date Required")
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
            //saving the data in the database
            launch {
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
            //back navigation w.r.t the called fragment
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



