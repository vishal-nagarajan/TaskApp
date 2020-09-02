package com.example.mynotes.Ui

import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_save.setOnClickListener{
            val noteTask = edit_task.text.toString().trim()
            val noteDescription = edit_description.text.toString().trim()
            val noteClient = edit_client.text.toString().trim()
            val noteProject = edit_project.text.toString().trim()
            val noteStartDate = (edit_start_date.text.toString().trim())
            val noteEndDate = (edit_end_date.text.toString().trim())
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
                edit_project.error = "project required"
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
