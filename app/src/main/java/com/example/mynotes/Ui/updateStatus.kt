package com.example.mynotes.Ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TimePicker
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.*
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_update_status.*
import kotlinx.android.synthetic.main.new_chip_dialog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


class updateStatus : BaseFragment() {
    private var note: Note? = null
    private var status: Boolean = false
    private var hours:Int=0
    val cal = Calendar.getInstance()
    var fromTimeHour:Int = cal.get(Calendar.HOUR_OF_DAY)
    var fromTimeMinute  = cal.get(Calendar.MINUTE)
    val cal2 = Calendar.getInstance()
    var toTimeHour = cal2.get(Calendar.HOUR_OF_DAY)
    var toTimeMinute =  cal2.get(Calendar.MINUTE)
    var natureOfWork:String=""
    var fromtime:LocalTime= LocalTime.now()
    var totime:LocalTime= LocalTime.now()
    val updateddate:LocalDate= LocalDate.now()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_status, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        launch {
//            context?.let {
//                val TaskWTL=NoteDatabase(it).getNoteDao().getTaskWithTimeLog()
//
//                for (task in TaskWTL){
//
////                    task.task.totimeMin
//                    val timeloglist =task.timelogs
//                    for(time in timeloglist){
//                        time.fromtimeHour
//                    }
//                }
//                it.toast(TaskWTL.toString())
//            }
//        }
        launch {
            context?.let {
//                val text = "test"
//                NoteDatabase(it).getNoteDao().addNOW(NOW(text))
                val textNOW = NoteDatabase(it).getNoteDao().getNOW()
//                it.toast(textNOW)
                val length = textNOW.size
                for (text in textNOW) {
                    if (!text.equals("a")) {
//                    //chip add
                        val chip = Chip(activity)
                        chip.text = (text)
                        chip.isCheckable = true
                        chip.setRippleColorResource(R.color.colorPrimaryDark)
                        chip.setChipBackgroundColorResource(R.color.colorAccent)
                        chipGroup.addView(chip)
                    } else {
                        ;
                    }
                }
            }
        }
        arguments?.let {
            note = updateStatusArgs.fromBundle(it).note
            task_update_status.setText(note?.task)
            when {
                !(note?.place.equals("a")) -> {
                    place_text.setText(note?.place)
                    people_text.setText(note?.people)
                }
            }
            edit_hours.setText(note?.hours.toString())
        }
        completed.setOnClickListener {
            launch {
                context?.let {
                    val sid = note!!.id
                    NoteDatabase(it).getNoteDao().updateStatus(sid, true)
                    it.toast("Status Completed")
                }
            }
            status = true
            // The toggle is enabled
        }
        inprogress.setOnClickListener {
            launch {
                context?.let {
                    val sid = note!!.id
                    NoteDatabase(it).getNoteDao().updateStatus(sid, false)
                    it.toast("Ststus in Progress")
                }
                status = false
            }
        }




        val timeSetListener=TimePickerDialog.OnTimeSetListener{_,hour,minute->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
            fromTimeHour = hour
            fromTimeMinute = minute
            fromtime= LocalTime.of(hour,minute)
            }
        button3.setOnClickListener {
            context?.let {
                TimePickerDialog(it,timeSetListener,fromTimeHour,fromTimeMinute,false).show()
//                TimePickerDialog(it,timeSetListener)

            }
        }

        val timeSetListener2=TimePickerDialog.OnTimeSetListener{_,hour,minute->
            cal2.set(Calendar.HOUR_OF_DAY,hour)
            cal2.set(Calendar.MINUTE,minute)
            toTimeHour = hour
            toTimeMinute = minute
            totime= LocalTime.of(hour,minute)
        }
        button4.setOnClickListener {
            context?.let {
                TimePickerDialog(it,timeSetListener2,toTimeHour,toTimeMinute,false).show()

            }
        }





        //add chip
        add_new_nature_of_work.setOnClickListener {
            var textNOW = "test"
            launch {
                context?.let {
                    val builder  = AlertDialog.Builder(it)
                    val inflater = layoutInflater.inflate(
                        R.layout.new_chip_dialog,
                        null
                    )
                    builder.setTitle("Add Nature of Work")
//                    val edittext = nature_of_work_text
//                    textNOW = nature_of_work_text.text.toString()
                    builder.setPositiveButton(R.string.save,
                        DialogInterface.OnClickListener { dialog, id ->
                        })
                    builder.setView(inflater)
                    val dial = builder.create()
                    dial.show()
                    dial.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val edittext = inflater.findViewById<EditText>(R.id.nature_of_work_text)
                        textNOW = edittext.text.toString()
                        if (textNOW.isEmpty()){
                            edittext.error = "Enter work nature"
                            edittext.requestFocus()
//                            return@setOnClickListener
                        }else{

                            CoroutineScope(Dispatchers.Main + Job()).launch {
//                                context?.let {
                                    it.context.toast("here")
                                    val mNOW = NOW(textNOW)
                                    NoteDatabase(it.context).getNoteDao().addNOW(mNOW)

                                    it.context.toast("new work added")
                                    val chip = Chip(activity)
                                    chip.text = (textNOW)
                                    chip.isCheckable = true
                                    chip.setRippleColorResource(R.color.colorPrimaryDark)
                                    chip.setChipBackgroundColorResource(R.color.colorAccent)
                                    chipGroup.addView(chip)
                                    dial.dismiss()
//                                }
                            }

                        }
                    }
//                    val mNOW = NOW(textNOW)
//                    NoteDatabase(it).getNoteDao().addNOW(mNOW)


                }
            }
        }

        //chipselection to db
        var checkedChipId = chipGroup.checkedChipId
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val tex = view?.findViewById<Chip>(checkedId)?.text.toString()
                natureOfWork = tex
                context?.toast(" $tex")
        }

            // The toggle is disabled
        confirm_status_button.setOnClickListener{
            val nhours = edit_hours.getText().toString()
            val place = place_text.text.toString()
            val people = people_text.text.toString()
            if (nhours.isEmpty()) {
                edit_hours.error = "hours Required"
                edit_hours.requestFocus()
                return@setOnClickListener
            }
            if (place.isEmpty()) {
                place_text.error = "place Required"
                place_text.requestFocus()
                return@setOnClickListener
            }
            if (people.isEmpty()) {
                people_text.error = "People Required"
                people_text.requestFocus()
                return@setOnClickListener
            }
            if (natureOfWork.equals("null")){
                textView10.error = "Nature of work not specified"
                textView10.requestFocus()
                return@setOnClickListener
            }
            hours = Integer.parseInt(edit_hours.getText().toString())
                launch {
                    context?.let {
                        val sid = note!!.id
                        NoteDatabase(it).getNoteDao().updateHours(sid, hours,place,people,fromtime.hour,fromtime.minute,totime.hour,totime.minute,natureOfWork)
                        val timelog:Timelog = Timelog(updateddate.dayOfMonth,updateddate.monthValue,updateddate.year,fromtime.hour,fromtime.minute,totime.hour,totime.minute,sid)
                        NoteDatabase(it).getNoteDao().addTimeLog(timelog)

                        it.toast("time saved $natureOfWork")
                    }
                    val action = updateStatusDirections.actionbacktoMenu()
                    Navigation.findNavController(it).navigate(action)
                }



        }
    }
}

